package com.github.messenger4j.webhook.event;

import java.time.Instant;
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
public final class OptInEvent extends BaseEvent {

    private final Optional<String> senderId;
    private final Optional<String> refPayload;
    private final Optional<String> userRefPayload;

    public OptInEvent(@NonNull Optional<String> senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                      @NonNull Optional<String> refPayload, @NonNull Optional<String> userRefPayload) {
        super(recipientId, timestamp);
        this.senderId = senderId;
        this.refPayload = refPayload;
        this.userRefPayload = userRefPayload;
    }

    public Optional<String> senderId() {
        return senderId;
    }

    public Optional<String> refPayload() {
        return refPayload;
    }

    public Optional<String> userRefPayload() {
        return userRefPayload;
    }
}
