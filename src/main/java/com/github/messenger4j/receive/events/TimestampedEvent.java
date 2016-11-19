package com.github.messenger4j.receive.events;

import java.util.Date;
import java.util.Objects;

/**
 * This <b>internal</b> abstract class is a base implementation for events containing a {@code timestamp}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see Event
 */
abstract class TimestampedEvent extends Event {

    private final Date timestamp;

    TimestampedEvent(String senderId, String recipientId, Date timestamp) {
        super(senderId, recipientId);
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TimestampedEvent that = (TimestampedEvent) o;
        return Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), timestamp);
    }

    @Override
    public String toString() {
        return "TimestampedEvent{" +
                "timestamp=" + timestamp +
                "} super=" + super.toString();
    }
}