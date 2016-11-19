package com.github.messenger4j.receive.events;

import java.util.Date;
import java.util.Objects;

/**
 * This <b>internal</b> abstract class is a base implementation for {@code message} events.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see Event
 */
abstract class MessageEvent extends TimestampedEvent {

    private final String mid;

    MessageEvent(String senderId, String recipientId, Date timestamp, String mid) {
        super(senderId, recipientId, timestamp);
        this.mid = mid;
    }

    public String getMid() {
        return mid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MessageEvent that = (MessageEvent) o;
        return Objects.equals(mid, that.mid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mid);
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "mid='" + mid + '\'' +
                "} super=" + super.toString();
    }
}