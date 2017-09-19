package com.github.messenger4j.v3.receive;

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
public abstract class MessageEvent extends BaseEvent {

    private final String messageId;

    MessageEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                 @NonNull String messageId) {
        super(senderId, recipientId, timestamp);
        this.messageId = messageId;
    }

    public String messageId() {
        return messageId;
    }
}
