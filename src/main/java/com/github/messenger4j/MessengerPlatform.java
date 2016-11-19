package com.github.messenger4j;

import com.github.messenger4j.receive.MessengerReceiveClientBuilder;
import com.github.messenger4j.send.MessengerSendClientBuilder;

/**
 * This is the entry point for using the {@code Messenger Platform API}.
 * The {@code MessengerPlatform} returns instances of {@link MessengerReceiveClientBuilder}
 * and {@link MessengerSendClientBuilder}.
 *
 * <p>
 * For further information on the {@code Messenger Platform} refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform">
 * https://developers.facebook.com/docs/messenger-platform
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @see MessengerReceiveClientBuilder
 * @see MessengerSendClientBuilder
 * @since 0.6.0
 */
public final class MessengerPlatform {

    private MessengerPlatform() {
    }

    /**
     * Returns an instance of {@code MessengerReceiveClientBuilder} that is used
     * to build instances of {@code MessengerReceiveClient}.
     *
     * @param appSecret   the {@code Application Secret} of your {@code Facebook App} connected to your {@code Facebook
     *                    Page}
     * @param verifyToken the {@code Verification Token} that has been provided by you during the setup of the {@code
     *                    Webhook}
     * @return a {@code MessengerReceiveClientBuilder}
     */
    public static MessengerReceiveClientBuilder newReceiveClientBuilder(String appSecret, String verifyToken) {
        return new MessengerReceiveClientBuilder(appSecret, verifyToken);
    }

    /**
     * Returns an instance of {@code MessengerSendClientBuilder} that is used
     * to build instances of {@code MessengerSendClient}.
     *
     * @param pageAccessToken the generated {@code Page Access Token} of your {@code Facebook Page}
     *                        connected to your {@code Facebook App}
     * @return a {@code MessengerSendClientBuilder}
     */
    public static MessengerSendClientBuilder newSendClientBuilder(String pageAccessToken) {
        return new MessengerSendClientBuilder(pageAccessToken);
    }
}