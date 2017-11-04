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
public final class MessageReadEvent extends BaseEvent {

    private final Instant watermark;

    public MessageReadEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                            @NonNull Instant watermark) {
        super(senderId, recipientId, timestamp);
        this.watermark = watermark;
    }

    public Instant watermark() {
        return watermark;
    }
}
