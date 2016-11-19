package com.github.messenger4j.exceptions;

/**
 * Thrown to indicate that a verification failed.
 *
 * <p>
 * For example verification of the payload signature.
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class MessengerVerificationException extends Exception {

    public MessengerVerificationException(String message) {
        super(message);
    }
}