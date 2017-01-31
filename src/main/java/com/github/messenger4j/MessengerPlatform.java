package com.github.messenger4j;

import com.github.messenger4j.receive.MessengerReceiveClientBuilder;
import com.github.messenger4j.send.MessengerSendClientBuilder;
import com.github.messenger4j.setup.MessengerSetupClientBuilder;
import com.github.messenger4j.user.UserProfileClientBuilder;

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

    /**
     * Private constructor to prevent instantiation.
     */
    private MessengerPlatform() {
    }

    /**
     * Returns an instance of {@code MessengerReceiveClientBuilder} that is used
     * to build instances of {@code MessengerReceiveClient}.
     *
     * @param appSecret   the {@code Application Secret} of your {@code Facebook App}
     * @param verifyToken the {@code Verification Token} that has been provided by you
     *                    during the setup of the {@code Webhook}
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
     * @return a {@code MessengerSendClientBuilder}
     */
    public static MessengerSendClientBuilder newSendClientBuilder(String pageAccessToken) {
        return new MessengerSendClientBuilder(pageAccessToken);
    }

    /**
     * @param pageAccessToken the generated {@code Page Access Token} of your {@code Facebook Page}
     * @return a {@code MessengerSetupClientBuilder}
     * @since 0.8.0
     */
    public static MessengerSetupClientBuilder newSetupClientBuilder(String pageAccessToken) {
        return new MessengerSetupClientBuilder(pageAccessToken);
    }

    /**
     * @param pageAccessToken the generated {@code Page Access Token} of your {@code Facebook Page}
     * @return a {@code UserProfileClientBuilder}
     * @since 0.8.0
     */
    public static UserProfileClientBuilder newUserProfileClientBuilder(String pageAccessToken) {
        return new UserProfileClientBuilder(pageAccessToken);
    }

}