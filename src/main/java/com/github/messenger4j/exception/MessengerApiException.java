package com.github.messenger4j.exception;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class MessengerApiException extends Exception {

    private final Optional<String> type;
    private final Optional<Integer> code;
    private final Optional<String> fbTraceId;

    public MessengerApiException(@NonNull String message, @NonNull Optional<String> type,
                                 @NonNull Optional<Integer> code, @NonNull Optional<String> fbTraceId) {
        super(message);
        this.type = type;
        this.code = code;
        this.fbTraceId = fbTraceId;
    }

    public String message() {
        return super.getMessage();
    }

    public Optional<String> type() {
        return type;
    }

    public Optional<Integer> code() {
        return code;
    }

    public Optional<String> fbTraceId() {
        return fbTraceId;
    }
}
