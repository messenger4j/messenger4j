package com.github.messenger4j.exception;

import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class MessengerVerificationException extends Exception {

    public MessengerVerificationException(@NonNull String message) {
        super(message);
    }
}