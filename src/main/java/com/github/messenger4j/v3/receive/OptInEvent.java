package com.github.messenger4j.v3.receive;

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

    private final String refPayload;

    public OptInEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                      String refPayload) {
        super(senderId, recipientId, timestamp);
        this.refPayload = refPayload;
    }

    public Optional<String> refPayload() {
        return Optional.ofNullable(refPayload);
    }
}
