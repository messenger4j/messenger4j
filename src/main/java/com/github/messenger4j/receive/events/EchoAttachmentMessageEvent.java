package com.github.messenger4j.receive.events;

import com.github.messenger4j.receive.attachments.Attachment;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.github.messenger4j.internal.JsonHelper.Constants.*;
import static com.github.messenger4j.internal.JsonHelper.getProperty;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

/**
 * This event will occur when a attachment message has been sent by your page.
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
public class EchoAttachmentMessageEvent extends EchoMessageEvent {
    private final List<Attachment> attachments;

    /**
     * <b>Internal</b> method to create an instance of {@link EchoAttachmentMessageEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link EchoAttachmentMessageEvent}
     */
    public static EchoAttachmentMessageEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date timestamp = getPropertyAsDate(jsonObject, PROP_TIMESTAMP);
        final String mid = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_MID);
        final String appId = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_APP_ID);
        final String metadata = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_METADATA);
        final JsonArray attachmentsJsonArray = getProperty(jsonObject, PROP_MESSAGE, PROP_ATTACHMENTS).getAsJsonArray();

        final List<Attachment> attachments = new ArrayList<>(attachmentsJsonArray.size());
        for (JsonElement attachmentJsonElement : attachmentsJsonArray) {
            attachments.add(Attachment.fromJson(attachmentJsonElement.getAsJsonObject()));
        }

        return new EchoAttachmentMessageEvent(senderId, recipientId, timestamp, mid, appId, metadata, attachments);
    }

    public EchoAttachmentMessageEvent(String senderId, String recipientId, Date timestamp, String mid, String appId, String metadata,
                                  List<Attachment> attachments) {

        super(senderId, recipientId, timestamp, mid, appId, metadata);
        this.attachments = attachments == null ? null : Collections.unmodifiableList(attachments);
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EchoAttachmentMessageEvent that = (EchoAttachmentMessageEvent) o;
        return Objects.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attachments);
    }

    @Override
    public String toString() {
        return "EchoAttachmentMessageEvent{" +
                "attachments=" + attachments +
                "} super=" + super.toString();
    }
}
