package com.github.messenger4j.exception;

import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class MessengerIOException extends Exception {

    public MessengerIOException(@NonNull Throwable cause) {
        super(cause);
    }
}