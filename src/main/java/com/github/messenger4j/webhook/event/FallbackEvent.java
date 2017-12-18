package com.github.messenger4j.webhook.event;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class FallbackEvent extends BaseEvent {

    public FallbackEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp) {
        super(senderId, recipientId, timestamp);
    }
}
