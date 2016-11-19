package com.github.messenger4j.receive.events;

import java.util.Date;
import java.util.Objects;

/**
 * This <b>internal</b> abstract class is a base implementation for message events containing a {@code text}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see Event
 */
abstract class CommonTextMessageEvent extends MessageEvent {

    private final String text;

    CommonTextMessageEvent(String senderId, String recipientId, Date timestamp, String mid, String text) {
        super(senderId, recipientId, timestamp, mid);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommonTextMessageEvent that = (CommonTextMessageEvent) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "CommonTextMessageEvent{" +
                "text='" + text + '\'' +
                "} super=" + super.toString();
    }
}