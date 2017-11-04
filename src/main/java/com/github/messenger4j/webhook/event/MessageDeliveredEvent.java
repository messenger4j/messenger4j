package com.github.messenger4j.webhook.event;

import com.github.messenger4j.internal.Lists;
import java.time.Instant;
import java.util.List;
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
public final class MessageDeliveredEvent extends BaseEvent {

    private final Instant watermark;
    private final Optional<List<String>> messageIds;

    public MessageDeliveredEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                                 @NonNull Instant watermark, @NonNull Optional<List<String>> messageIds) {
        super(senderId, recipientId, timestamp);
        this.watermark = watermark;
        this.messageIds = messageIds.map(Lists::immutableList);
    }

    public Instant watermark() {
        return watermark;
    }

    public Optional<List<String>> messageIds() {
        return messageIds;
    }
}
