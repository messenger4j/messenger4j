package com.github.messenger4j.setup;

import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.DELETE;
import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.POST;
import static com.github.messenger4j.setup.SettingType.CALL_TO_ACTIONS;
import static com.github.messenger4j.setup.SettingType.GREETING;
import static com.github.messenger4j.setup.ThreadState.EXISTING_THREAD;

import com.github.messenger4j.common.MessengerHttpClient.HttpMethod;
import com.github.messenger4j.common.MessengerRestClientAbstract;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.google.gson.JsonObject;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
final class MessengerSetupClientImpl extends MessengerRestClientAbstract<SetupPayload, SetupResponse>
        implements MessengerSetupClient {

    private static final String FB_GRAPH_API_URL = "https://graph.facebook.com/v2.8/me/thread_settings?access_token=%s";

    private final Logger logger = LoggerFactory.getLogger(MessengerSetupClientImpl.class);

    private final String requestUrl;

    MessengerSetupClientImpl(MessengerSetupClientBuilder builder) {
        super(builder.httpClient);
        this.requestUrl = String.format(FB_GRAPH_API_URL, builder.pageAccessToken);
        logger.debug("{} initialized successfully.", MessengerSetupClientImpl.class.getSimpleName());
    }

    @Override
    public SetupResponse setupWelcomeMessage(String greeting)
            throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(GREETING)
                .greeting(greeting)
                .build();

        return sendPayload(POST, payload);
    }

    @Override
    public SetupResponse resetWelcomeMessage() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(GREETING)
                .build();

        return sendPayload(DELETE, payload);
    }

    @Override
    public SetupResponse setupStartButton(String startPayload)
            throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(ThreadState.NEW_THREAD)
                .payload(startPayload)
                .build();

        return sendPayload(POST, payload);
    }

    @Override
    public SetupResponse removeStartButton() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(ThreadState.NEW_THREAD)
                .build();

        return sendPayload(DELETE, payload);
    }

    @Override
    public SetupResponse setupPersistentMenu(List<CallToAction> menuItems) throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(EXISTING_THREAD)
                .addMenuItems(menuItems)
                .build();

        return sendPayload(POST, payload);
    }

    @Override
    public SetupResponse removePersistentMenu() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(EXISTING_THREAD)
                .build();

        return sendPayload(DELETE, payload);
    }

    private SetupResponse sendPayload(HttpMethod httpMethod, SetupPayload payload)
            throws MessengerApiException, MessengerIOException {

        return doRequest(httpMethod, this.requestUrl, payload);
    }

    @Override
    protected SetupResponse responseFromJson(JsonObject responseJsonObject) {
        return SetupResponse.fromJson(responseJsonObject);
    }
}
