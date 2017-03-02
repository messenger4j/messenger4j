package com.github.messenger4j.receive.events;

import com.google.gson.JsonObject;

import java.util.Date;
import java.util.Objects;

import static com.github.messenger4j.internal.JsonHelper.Constants.*;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
/**
 * This event will occur when a text message has been sent by your page.
 *
 * <p>
 * For further information refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/message-echo">
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/message-echo
 * </a>
 * </p>
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see Event
 */
public class EchoTextMessageEvent extends EchoMessageEvent {

    private final String text;

    /**
     * <b>Internal</b> method to create an instance of {@link EchoTextMessageEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link EchoTextMessageEvent}
     */
    public static EchoTextMessageEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date timestamp = getPropertyAsDate(jsonObject, PROP_TIMESTAMP);
        final String mid = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_MID);
        final String appId = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_APP_ID);
        final String metadata = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_METADATA);
        final String text = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_TEXT);

        return new EchoTextMessageEvent(senderId, recipientId, timestamp, mid, appId, metadata, text);
    }

    public EchoTextMessageEvent(String senderId, String recipientId, Date timestamp, String mid, String appId, String metadata, String text) {
        super(senderId, recipientId, timestamp, mid, appId, metadata);
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
        EchoTextMessageEvent that = (EchoTextMessageEvent) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text);
    }

    @Override
    public String toString() {
        return "EchoTextMessageEvent{" +
                "text='" + text + '\'' +
                "} super=" + super.toString();
    }
}
