package com.github.messenger4j.v3.receive;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
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
    private final List<String> messageIds;

    public MessageDeliveredEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                                 @NonNull Instant watermark, List<String> messageIds) {
        super(senderId, recipientId, timestamp);
        this.watermark = watermark;
        this.messageIds = messageIds != null ? Collections.unmodifiableList(new ArrayList<>(messageIds)) : null;
    }

    public Instant watermark() {
        return watermark;
    }

    public Optional<List<String>> messageIds() {
        return Optional.ofNullable(messageIds);
    }
}
