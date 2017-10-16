package com.github.messenger4j.v3;

import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.GET;
import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.POST;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ENTRY;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGING;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_OBJECT;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsJsonArray;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.github.messenger4j.common.DefaultMessengerHttpClient;
import com.github.messenger4j.common.GsonFactory;
import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.common.MessengerHttpClient.HttpMethod;
import com.github.messenger4j.common.MessengerHttpClient.HttpResponse;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.SignatureVerifier;
import com.github.messenger4j.send.MessageResponse;
import com.github.messenger4j.setup.SetupResponse;
import com.github.messenger4j.user.UserProfile;
import com.github.messenger4j.v3.receive.Event;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@Slf4j
public final class Messenger {

    public static final String GREETING_TEXT_USER_FIRST_NAME = "{{user_first_name}}";
    public static final String GREETING_TEXT_USER_LAST_NAME = "{{user_last_name}}";
    public static final String GREETING_TEXT_USER_FULL_NAME = "{{user_full_name}}";

    private static final String OBJECT_TYPE_PAGE = "page";
    private static final String HUB_MODE_SUBSCRIBE = "subscribe";

    private static final String FB_GRAPH_API_URL_MESSAGES = "https://graph.facebook.com/v2.8/me/messages?access_token=%s";
    private static final String FB_GRAPH_API_URL_MESSENGER_PROFILE = "https://graph.facebook.com/v2.8/me/messenger_profile?access_token=%s";
    private static final String FB_GRAPH_API_URL_USER = "https://graph.facebook.com/v2.8/%s?fields=first_name," +
            "last_name,profile_pic,locale,timezone,gender,is_payment_enabled,last_ad_referral&access_token=%s";

    private final String pageAccessToken;
    private final String appSecret;
    private final String verifyToken;
    private final String messagesRequestUrl;
    private final String messengerProfileRequestUrl;
    private final MessengerHttpClient httpClient;

    private final Gson gson;
    private final JsonParser jsonParser;

    public static Messenger create(@NonNull String pageAccessToken, @NonNull String appSecret, @NonNull String verifyToken) {
        return new Messenger(pageAccessToken, appSecret, verifyToken, null);
    }

    public static Messenger create(@NonNull String pageAccessToken, @NonNull String appSecret,
                                   @NonNull String verifyToken, @NonNull MessengerHttpClient customHttpClient) {

        return new Messenger(pageAccessToken, appSecret, verifyToken, customHttpClient);
    }

    private Messenger(String pageAccessToken, String appSecret, String verifyToken, MessengerHttpClient httpClient) {
        this.pageAccessToken = pageAccessToken;
        this.appSecret = appSecret;
        this.verifyToken = verifyToken;
        this.messagesRequestUrl = String.format(FB_GRAPH_API_URL_MESSAGES, pageAccessToken);
        this.messengerProfileRequestUrl = String.format(FB_GRAPH_API_URL_MESSENGER_PROFILE, pageAccessToken);
        this.httpClient = httpClient == null ? new DefaultMessengerHttpClient() : httpClient;

        this.gson = GsonFactory.createGson();
        this.jsonParser = new JsonParser();
    }

    public MessageResponse send(@NonNull MessagePayload messagePayload)
            throws MessengerApiException, MessengerIOException {

        return doRequest(POST, messagesRequestUrl, messagePayload, MessageResponse::fromJson);
    }

    public void onReceiveEvents(@NonNull String requestPayload, String signature,
                                @NonNull Consumer<Event> eventHandler)
            throws MessengerVerificationException {

        if (signature != null) {
            if (!SignatureVerifier.isSignatureValid(requestPayload, signature, this.appSecret)) {
                throw new MessengerVerificationException("Signature verification failed. " +
                        "Provided signature does not match calculated signature.");
            }
        } else {
            log.warn("No signature provided, hence the signature verification is skipped. THIS IS NOT RECOMMENDED");
        }

        final JsonObject payloadJsonObject = this.jsonParser.parse(requestPayload).getAsJsonObject();

        final String objectType = getPropertyAsString(payloadJsonObject, PROP_OBJECT);
        if (objectType == null || !objectType.equalsIgnoreCase(OBJECT_TYPE_PAGE)) {
            throw new IllegalArgumentException("'object' property must be 'page'. " +
                    "Make sure this is a page subscription");
        }

        final JsonArray entries = getPropertyAsJsonArray(payloadJsonObject, PROP_ENTRY);
        for (JsonElement entry : entries) {
            final JsonArray messagingEvents = getPropertyAsJsonArray(entry.getAsJsonObject(), PROP_MESSAGING);
            for (JsonElement messagingEvent : messagingEvents) {
                eventHandler.accept(EventFactory.createEvent(messagingEvent.getAsJsonObject()));
            }
        }
    }

    public void verifyWebhook(@NonNull String mode, @NonNull String verifyToken) throws MessengerVerificationException {
        if (!mode.equals(HUB_MODE_SUBSCRIBE)) {
            throw new MessengerVerificationException("Webhook verification failed. Mode '" + mode + "' is invalid.");
        }
        if (!verifyToken.equals(this.verifyToken)) {
            throw new MessengerVerificationException("Webhook verification failed. Verification token '" +
                    verifyToken + "' is invalid.");
        }
    }

    public UserProfile queryUserProfileById(String userId) throws MessengerApiException, MessengerIOException {
        final String requestUrl = String.format(FB_GRAPH_API_URL_USER, userId, pageAccessToken);
        return doRequest(GET, requestUrl, null, UserProfile::fromJson);
    }


    public SetupResponse updateSettings(@NonNull MessengerSettings messengerSettings)
            throws MessengerApiException, MessengerIOException {

        return doRequest(POST, messengerProfileRequestUrl, messengerSettings, SetupResponse::fromJson);
    }

    private <R> R doRequest(@NonNull HttpMethod httpMethod, @NonNull String requestUrl, Object payload,
                            @NonNull Function<JsonObject, R> responseTransformer)
            throws MessengerApiException, MessengerIOException {

        try {
            final String jsonBody = payload == null ? null : this.gson.toJson(payload);
            final HttpResponse httpResponse = this.httpClient.execute(httpMethod, requestUrl, jsonBody);
            final JsonObject responseJsonObject = this.jsonParser.parse(httpResponse.getBody()).getAsJsonObject();

            if (responseJsonObject.size() == 0) {
                throw new MessengerApiException("The response JSON does not contain any key/value pair", null, null, null);
            }

            if (httpResponse.getStatusCode() >= 200 && httpResponse.getStatusCode() < 300) {
                return responseTransformer.apply(responseJsonObject);
            } else {
                throw MessengerApiException.fromJson(responseJsonObject);
            }
        } catch (IOException e) {
            throw new MessengerIOException(e);
        }
    }
}
