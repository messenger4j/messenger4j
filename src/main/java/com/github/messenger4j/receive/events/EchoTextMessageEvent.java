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
public class EchoTextMessageEvent extends CommonTextMessageEvent {
    private final String appId;
    private final String metadata;

    /**
     * <b>Internal</b> method to create an instance of {@link EchoAttachmentMessageEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link EchoAttachmentMessageEvent}
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

    public EchoTextMessageEvent(String senderId, String recipientId, Date timestamp, String mid, String appId, String metadata,
                                      String text) {

        super(senderId, recipientId, timestamp, mid, text);
        this.appId = appId;
        this.metadata = metadata;
    }

    public String getAppId() {
        return appId;
    }

    public String getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EchoTextMessageEvent that = (EchoTextMessageEvent) o;
        return Objects.equals(appId, that.appId) &&
                Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), appId, metadata);
    }

    @Override
    public String toString() {
        return "EchoTextMessageEvent{" +
                "appId='" + appId + '\'' +
                ", metadata='" + metadata + '\'' +
                "} super=" + super.toString();
    }
}
