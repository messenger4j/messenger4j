package com.github.messenger4j.exceptions;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * Thrown to indicate that a Messenger Platform Send API request failed.
 *
 * <p>
 * Messenger Platform errors are grouped by code, with a different message depending on the error condition.<br>
 * For a list of common errors that you should consider handling at run-time refer to: <br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/send-api-reference#errors">
 * https://developers.facebook.com/docs/messenger-platform/send-api-reference#errors
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class MessengerApiException extends Exception {

    private final String type;
    private final Integer code;
    private final String fbTraceId;

    public MessengerApiException(@NonNull String message, String type, Integer code, String fbTraceId) {
        super(message);
        this.type = type;
        this.code = code;
        this.fbTraceId = fbTraceId;
    }

    public String message() {
        return super.getMessage();
    }

    public Optional<String> type() {
        return Optional.ofNullable(type);
    }

    public Optional<Integer> code() {
        return Optional.ofNullable(code);
    }

    public Optional<String> fbTraceId() {
        return Optional.ofNullable(fbTraceId);
    }
}