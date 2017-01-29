package com.github.messenger4j.setup;

import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.DELETE;
import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.POST;
import static com.github.messenger4j.setup.SettingType.CALL_TO_ACTIONS;
import static com.github.messenger4j.setup.SettingType.GREETING;
import static com.github.messenger4j.setup.ThreadState.EXISTING_THREAD;

import com.github.messenger4j.common.DefaultMessengerHttpClient;
import com.github.messenger4j.common.MessengerSendClientAbstract;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;

import java.util.List;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
final class MessengerSetupClientImpl extends MessengerSendClientAbstract<SetupPayload, SetupResponse>
        implements MessengerSetupClient {

    private static final String FB_GRAPH_API_URL = "https://graph.facebook.com/v2.8/me/thread_settings?access_token=%s";

    private final Logger logger = LoggerFactory.getLogger(MessengerSetupClientImpl.class);

    MessengerSetupClientImpl(MessengerSetupClientBuilder builder) {
        super(String.format(FB_GRAPH_API_URL, builder.pageAccessToken),
                builder.httpClient == null ? new DefaultMessengerHttpClient() : builder.httpClient);
        logger.debug("{} initialized successfully.", MessengerSetupClientImpl.class.getSimpleName());
    }

    @Override
    public SetupResponse setupWelcomeMessage(String greeting)
            throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(GREETING)
                .greeting(greeting)
                .build();

        return sendPayload(payload, POST);
    }

    @Override
    public SetupResponse resetWelcomeMessage() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(GREETING)
                .build();

        return sendPayload(payload, DELETE);
    }

    @Override
    public SetupResponse setupStartButton(String startPayload)
            throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(ThreadState.NEW_THREAD)
                .payload(startPayload)
                .build();

        return sendPayload(payload, POST);
    }

    @Override
    public SetupResponse removeStartButton() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(ThreadState.NEW_THREAD)
                .build();

        return sendPayload(payload, DELETE);
    }

    @Override
    public SetupResponse setupPersistentMenu(List<CallToAction> menuItems) throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(EXISTING_THREAD)
                .addMenuItems(menuItems)
                .build();

        return sendPayload(payload, POST);
    }

    @Override
    public SetupResponse removePersistentMenu() throws MessengerApiException, MessengerIOException {
        final SetupPayload payload = SetupPayload.newBuilder()
                .settingType(CALL_TO_ACTIONS)
                .threadState(EXISTING_THREAD)
                .build();

        return sendPayload(payload, DELETE);
    }

    @Override
    protected SetupResponse responseFromJson(JsonObject responseJsonObject) {
        return SetupResponse.fromJson(responseJsonObject);
    }
}
