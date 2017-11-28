package com.github.messenger4j.webhook.event;

import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Joe Tindale
 * @since 1.0.0-SNAPSHOT
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEventWithSenderId extends BaseEvent {

    private final String senderId;

    BaseEventWithSenderId(String senderId, String recipientId, Instant timestamp) {
        super(recipientId, timestamp);
        this.senderId = senderId;
    }

    public String senderId() {
        return senderId;
    }
}
