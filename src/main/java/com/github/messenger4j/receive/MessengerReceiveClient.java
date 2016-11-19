package com.github.messenger4j.receive;

import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.handlers.EventHandler;

/**
 * A {@code MessengerReceiveClient} is responsible for handling all callbacks from the Facebook
 * {@code Messenger Platform}.
 *
 * <p>
 * It handles all inbound messages and events, and validates the integrity and origin of the payload.<br>
 * Furthermore it provides the functionality to verify the {@code Webhook}.
 * </p>
 *
 * <p>
 * For further information and instructions on how to setup the {@code Webhook} refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference">
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see MessengerReceiveClientBuilder
 */
public interface MessengerReceiveClient {

    /**
     * Supports the {@code Webhook} verification process by comparing the {@code verification token} and {@code mode}.
     *
     * <p>
     * The return value of this method has to be returned as response to the verification request.
     * </p>
     *
     * @param mode        the {@code hub.mode} query parameter
     * @param verifyToken the {@code hub.verify_token} query parameter
     * @param challenge   the {@code hub.challenge} query parameter
     * @return the {@code challenge} if the verification was successful
     * @throws MessengerVerificationException thrown if the verification was <b>not</b> successful
     */
    String verifyWebhook(String mode, String verifyToken, String challenge)
            throws MessengerVerificationException;

    /**
     * Processes the callback payload and for each contained event the specific {@link EventHandler} is called.
     *
     * <p>
     * When using this method you have to disable the {@code signature} verification
     * (see {@link MessengerReceiveClientBuilder}), otherwise a {@link IllegalArgumentException} is thrown.
     * </p>
     *
     * <p>
     * <b>It is NOT recommended to disable the signature verification!</b>
     * </p>
     *
     * @param payload the request body {@code JSON payload}
     */
    void processCallbackPayload(String payload);

    /**
     * Processes the callback payload and for each contained event the specific {@link EventHandler} is called.
     * Also the integrity and origin of the payload is ensured by validating the given {@code signature}.
     *
     * @param payload the request body {@code JSON payload}
     * @param signature the {@code X-Hub-Signature} header value. SHA1 signature of the request payload.
     * @throws MessengerVerificationException thrown if the verification of the {@code signature} fails
     */
    void processCallbackPayload(String payload, String signature) throws MessengerVerificationException;
}