package com.github.messenger4j.exception;

import lombok.NonNull;

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

    public MessengerVerificationException(@NonNull String message) {
        super(message);
    }
}