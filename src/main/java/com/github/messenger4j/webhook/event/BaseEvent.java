package com.github.messenger4j.webhook.event;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class BaseEvent {

    private final String recipientId;
    private final Instant timestamp;

    BaseEvent(String recipientId, Instant timestamp) {
        this.recipientId = recipientId;
        this.timestamp = timestamp;
    }

    public String recipientId() {
        return recipientId;
    }

    public Instant timestamp() {
        return timestamp;
    }
}
