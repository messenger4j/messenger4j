package com.github.messenger4j.exceptions;

/**
 * Thrown to indicate that a Messenger Platform I/O operation failed or was interrupted.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class MessengerIOException extends Exception {

    public MessengerIOException(Throwable cause) {
        super(cause);
    }
}