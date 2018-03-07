package com.github.messenger4j;

import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerApiExceptionFactory;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.exception.MessengerVerificationException;
import com.github.messenger4j.internal.gson.GsonFactory;
import com.github.messenger4j.messengerprofile.*;
import com.github.messenger4j.send.MessageResponse;
import com.github.messenger4j.send.MessageResponseFactory;
import com.github.messenger4j.send.Payload;
import com.github.messenger4j.spi.MessengerHttpClient;
import com.github.messenger4j.spi.MessengerHttpClient.HttpMethod;
import com.github.messenger4j.spi.MessengerHttpClient.HttpResponse;
import com.github.messenger4j.userprofile.UserProfile;
import com.github.messenger4j.userprofile.UserProfileFactory;
import com.github.messenger4j.webhook.Event;
import com.github.messenger4j.webhook.SignatureUtil;
import com.github.messenger4j.webhook.factory.EventFactory;
import com.google.gson.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.*;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsJsonArray;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;
import static com.github.messenger4j.spi.MessengerHttpClient.HttpMethod.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@Slf4j
public final class Messenger {

	public enum Version {
		VERSION_2_6("v2.6"),
		VERSION_2_7("v2.7"),
		VERSION_2_8("v2.8"),
		VERSION_2_9("v2.9"),
		VERSION_2_10("v2.10"),
		VERSION_2_11("v2.11"),
		VERSION_2_12("v2.12"),
		LATEST(VERSION_2_12.versionUrl);

		private final String versionUrl;

		Version(String versionUrl) {
			this.versionUrl = versionUrl;
		}

		public String getVersionUrl() {
			return this.versionUrl;
		}

		public static Version getVersionFromString(String versionString) {
			Version version = LATEST;
			if (versionString != null) {
				version = Arrays.asList(values()).stream().filter(v -> v.versionUrl.equals(versionString)).findAny().orElse(LATEST);
			}
			return version;
		}

	}

	/**
	 * Constant for the {@code hub.mode} request parameter name.
	 */
	public static final String MODE_REQUEST_PARAM_NAME = "hub.mode";

	/**
	 * Constant for the {@code hub.challenge} request parameter name.
	 */
	public static final String CHALLENGE_REQUEST_PARAM_NAME = "hub.challenge";

	/**
	 * Constant for the {@code hub.verify_token} request parameter name.
	 */
	public static final String VERIFY_TOKEN_REQUEST_PARAM_NAME = "hub.verify_token";

	/**
	 * Constant for the {@code X-Hub-Signature} header name.
	 */
	public static final String SIGNATURE_HEADER_NAME = "X-Hub-Signature";

	private static final String OBJECT_TYPE_PAGE = "page";
	private static final String HUB_MODE_SUBSCRIBE = "subscribe";

	private static final String FB_GRAPH_API_BASE_URL = "https://graph.facebook.com/";
	private static final String FB_GRAPH_API_URL_MESSAGES = FB_GRAPH_API_BASE_URL.concat("%s/me/messages?access_token=%s");
	private static final String FB_GRAPH_API_URL_MESSENGER_PROFILE = FB_GRAPH_API_BASE_URL.concat("%s/me/messenger_profile?access_token=%s");
	private static final String FB_GRAPH_API_URL_USER = FB_GRAPH_API_BASE_URL.concat("%s/%s?fields=%s&access_token=%s");
	private static final String FB_USER_PROFILE_DEFAULT_FIELDS = "first_name,last_name,profile_pic,locale,timezone,gender,is_payment_enabled,last_ad_referral";

	private static final String FORMAT_DIAMOND_STRING = "%s";

	private final String pageAccessToken;
	private final String appSecret;
	private final String verifyToken;
	private final String userProfileFields;
	private final String messagesRequestUrl;
	private final String messengerProfileRequestUrl;
	private final String messengerUserProfileRequestUrl;
	private final MessengerHttpClient httpClient;

	private final Gson gson;
	private final JsonParser jsonParser;

	public static Messenger create(@NonNull String pageAccessToken, @NonNull String appSecret, @NonNull String verifyToken) {
		return create(pageAccessToken, appSecret, verifyToken, empty());
	}

	public static Messenger create(@NonNull String pageAccessToken, @NonNull String appSecret, @NonNull String verifyToken,
			@NonNull Optional<String> version) {
		return create(pageAccessToken, appSecret, verifyToken, version, empty());
	}

	public static Messenger create(@NonNull String pageAccessToken, @NonNull String appSecret, @NonNull String verifyToken, @NonNull Optional<String> version,
			@NonNull Optional<String> userProfileFields) {
		return create(pageAccessToken, appSecret, verifyToken, version, userProfileFields, empty());
	}

	public static Messenger create(@NonNull String pageAccessToken, @NonNull String appSecret, @NonNull String verifyToken, @NonNull Optional<String> version,
			@NonNull Optional<String> userProfileFields, @NonNull Optional<MessengerHttpClient> customHttpClient) {
		return new Messenger(pageAccessToken, appSecret, verifyToken, version, userProfileFields, customHttpClient);
	}

	private Messenger(String pageAccessToken, String appSecret, String verifyToken, Optional<String> version, Optional<String> userProfileFields,
			Optional<MessengerHttpClient> httpClient) {
		this.pageAccessToken = pageAccessToken;
		this.appSecret = appSecret;
		this.verifyToken = verifyToken;
		this.userProfileFields = userProfileFields.orElse(FB_USER_PROFILE_DEFAULT_FIELDS);
		final String versionUrl = version.orElse(Version.LATEST.versionUrl);
		this.messagesRequestUrl = String.format(FB_GRAPH_API_URL_MESSAGES, versionUrl, pageAccessToken);

		this.messengerProfileRequestUrl = String.format(FB_GRAPH_API_URL_MESSENGER_PROFILE, versionUrl, pageAccessToken);
		this.messengerUserProfileRequestUrl = String.format(FB_GRAPH_API_URL_USER, versionUrl, FORMAT_DIAMOND_STRING, FORMAT_DIAMOND_STRING, pageAccessToken);
		this.httpClient = httpClient.orElse(new DefaultMessengerHttpClient());

		this.gson = GsonFactory.createGson();
		this.jsonParser = new JsonParser();
	}

	public MessageResponse send(@NonNull Payload payload) throws MessengerApiException, MessengerIOException {

		return doRequest(POST, messagesRequestUrl, of(payload), MessageResponseFactory::create);
	}

	public void onReceiveEvents(@NonNull String requestPayload, @NonNull Optional<String> signature, @NonNull Consumer<Event> eventHandler)
			throws MessengerVerificationException {

		if (signature.isPresent()) {
			if (!SignatureUtil.isSignatureValid(requestPayload, signature.get(), this.appSecret)) {
				throw new MessengerVerificationException("Signature verification failed. " + "Provided signature does not match calculated signature.");
			}
		} else {
			log.warn("No signature provided, hence the signature verification is skipped. THIS IS NOT RECOMMENDED");
		}

		final JsonObject payloadJsonObject = this.jsonParser.parse(requestPayload).getAsJsonObject();

		final Optional<String> objectType = getPropertyAsString(payloadJsonObject, PROP_OBJECT);
		if (!objectType.isPresent() || !objectType.get().equalsIgnoreCase(OBJECT_TYPE_PAGE)) {
			throw new IllegalArgumentException("'object' property must be 'page'. " + "Make sure this is a page subscription");
		}

		final JsonArray entries = getPropertyAsJsonArray(payloadJsonObject, PROP_ENTRY).orElseThrow(IllegalArgumentException::new);
		for (JsonElement entry : entries) {
			final JsonArray messagingEvents = getPropertyAsJsonArray(entry.getAsJsonObject(), PROP_MESSAGING).orElseThrow(IllegalArgumentException::new);
			for (JsonElement messagingEvent : messagingEvents) {
				final Event event = EventFactory.createEvent(messagingEvent.getAsJsonObject());
				eventHandler.accept(event);
			}
		}
	}

	public void verifyWebhook(@NonNull String mode, @NonNull String verifyToken) throws MessengerVerificationException {
		if (!mode.equals(HUB_MODE_SUBSCRIBE)) {
			throw new MessengerVerificationException("Webhook verification failed. Mode '" + mode + "' is invalid.");
		}
		if (!verifyToken.equals(this.verifyToken)) {
			throw new MessengerVerificationException("Webhook verification failed. Verification token '" + verifyToken + "' is invalid.");
		}
	}

	public UserProfile queryUserProfile(@NonNull String userId) throws MessengerApiException, MessengerIOException {
		return queryUserProfile(userId, empty());
	}

	public UserProfile queryUserProfile(@NonNull String userId, @NonNull Optional<String> fields) throws MessengerApiException, MessengerIOException {
		final String requestUrl = String.format(messengerUserProfileRequestUrl, userId, fields.orElse(userProfileFields), pageAccessToken);
		return doRequest(GET, requestUrl, empty(), UserProfileFactory::create);
	}

	public SetupResponse updateSettings(@NonNull MessengerSettings messengerSettings) throws MessengerApiException, MessengerIOException {

		return doRequest(POST, messengerProfileRequestUrl, of(messengerSettings), SetupResponseFactory::create);
	}

	public SetupResponse deleteSettings(@NonNull MessengerSettingProperty property, @NonNull MessengerSettingProperty... properties)
			throws MessengerApiException, MessengerIOException {

		final List<MessengerSettingProperty> messengerSettingPropertyList = new ArrayList<>(properties.length + 1);
		messengerSettingPropertyList.add(property);
		messengerSettingPropertyList.addAll(Arrays.asList(properties));
		final DeleteMessengerSettingsPayload payload = DeleteMessengerSettingsPayload.create(messengerSettingPropertyList);
		return doRequest(DELETE, messengerProfileRequestUrl, of(payload), SetupResponseFactory::create);
	}

	private <R> R doRequest(HttpMethod httpMethod, String requestUrl, Optional<Object> payload, Function<JsonObject, R> responseTransformer)
			throws MessengerApiException, MessengerIOException {

		try {
			final Optional<String> jsonBody = payload.map(this.gson::toJson);
			final HttpResponse httpResponse = this.httpClient.execute(httpMethod, requestUrl, jsonBody.orElse(null));
			final JsonObject responseJsonObject = this.jsonParser.parse(httpResponse.body()).getAsJsonObject();

			if (responseJsonObject.size() == 0) {
				throw new MessengerApiException("The response JSON does not contain any key/value pair", empty(), empty(), empty());
			}

			if (httpResponse.statusCode() >= 200 && httpResponse.statusCode() < 300) {
				return responseTransformer.apply(responseJsonObject);
			} else {
				throw MessengerApiExceptionFactory.create(responseJsonObject);
			}
		} catch (IOException e) {
			throw new MessengerIOException(e);
		}
	}
}
