package com.github.messenger4j.receive.events;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_PAYLOAD;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_QUICK_REPLY;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TEXT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsJsonObject;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonObject;
import java.util.Date;
import java.util.Objects;

/**
 * This event will occur when a {@code Quick Reply} button has been tapped.
 *
 * <p>
 * For further information refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/message">
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/message
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see Event
 */
public final class QuickReplyMessageEvent extends CommonTextMessageEvent {

    private final QuickReply quickReply;

    /**
     * <b>Internal</b> method to create an instance of {@link QuickReplyMessageEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link QuickReplyMessageEvent}
     */
    public static QuickReplyMessageEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date timestamp = getPropertyAsDate(jsonObject, PROP_TIMESTAMP);
        final String mid = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_MID);
        final String text = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_TEXT);
        final JsonObject quickReplyJsonObject = getPropertyAsJsonObject(jsonObject, PROP_MESSAGE, PROP_QUICK_REPLY);

        final QuickReply quickReply = QuickReply.fromJson(quickReplyJsonObject);
        return new QuickReplyMessageEvent(senderId, recipientId, timestamp, mid, text, quickReply);
    }

    public QuickReplyMessageEvent(String senderId, String recipientId, Date timestamp, String mid, String text,
                                   QuickReply quickReply) {

        super(senderId, recipientId, timestamp, mid, text);
        this.quickReply = quickReply;
    }

    public QuickReply getQuickReply() {
        return quickReply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QuickReplyMessageEvent that = (QuickReplyMessageEvent) o;
        return Objects.equals(quickReply, that.quickReply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quickReply);
    }

    @Override
    public String toString() {
        return "QuickReplyMessageEvent{" +
                "quickReply=" + quickReply +
                "} super=" + super.toString();
    }

    /**
     * Custom data that is provided when the user taps on a {@code Quick Reply} button.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class QuickReply {

        private final String payload;

        private static QuickReply fromJson(JsonObject jsonObject) {
            final String payload = getPropertyAsString(jsonObject, PROP_PAYLOAD);
            return new QuickReply(payload);
        }

        public QuickReply(String payload) {
            this.payload = payload;
        }

        public String getPayload() {
            return payload;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            QuickReply that = (QuickReply) o;
            return Objects.equals(payload, that.payload);
        }

        @Override
        public int hashCode() {
            return Objects.hash(payload);
        }

        @Override
        public String toString() {
            return "QuickReply{" +
                    "payload='" + payload + '\'' +
                    '}';
        }
    }
}