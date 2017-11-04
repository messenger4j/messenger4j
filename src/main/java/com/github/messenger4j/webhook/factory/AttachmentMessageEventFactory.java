package com.github.messenger4j.webhook.factory;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ATTACHMENTS;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_COORDINATES;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_IS_ECHO;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_LAT;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_LONG;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_MID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_PAYLOAD;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_URL;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsDouble;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsInstant;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsJsonArray;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;
import static com.github.messenger4j.internal.gson.GsonUtil.hasProperty;

import com.github.messenger4j.webhook.event.AttachmentMessageEvent;
import com.github.messenger4j.webhook.event.attachment.Attachment;
import com.github.messenger4j.webhook.event.attachment.FallbackAttachment;
import com.github.messenger4j.webhook.event.attachment.LocationAttachment;
import com.github.messenger4j.webhook.event.attachment.RichMediaAttachment;
import com.github.messenger4j.webhook.event.attachment.RichMediaAttachment.Type;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class AttachmentMessageEventFactory implements BaseEventFactory<AttachmentMessageEvent> {

    @Override
    public boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_ATTACHMENTS) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_IS_ECHO);
    }

    @Override
    public AttachmentMessageEvent createEventFromJson(JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP)
                .orElseThrow(IllegalArgumentException::new);
        final String messageId = getPropertyAsString(messagingEvent, PROP_MESSAGE, PROP_MID)
                .orElseThrow(IllegalArgumentException::new);
        final JsonArray attachmentsJsonArray = getPropertyAsJsonArray(messagingEvent, PROP_MESSAGE, PROP_ATTACHMENTS)
                .orElseThrow(IllegalArgumentException::new);

        final List<Attachment> attachments = new ArrayList<>(attachmentsJsonArray.size());
        for (JsonElement attachmentJsonElement : attachmentsJsonArray) {
            final JsonObject attachmentJsonObject = attachmentJsonElement.getAsJsonObject();
            final String type = getPropertyAsString(attachmentJsonObject, PROP_TYPE)
                    .map(String::toUpperCase)
                    .orElseThrow(IllegalArgumentException::new);
            switch (type) {
                case "IMAGE":
                case "AUDIO":
                case "VIDEO":
                case "FILE":
                    final URL url = getPropertyAsString(attachmentJsonObject, PROP_PAYLOAD, PROP_URL)
                            .map(s -> {
                                try {
                                    return new URL(s);
                                } catch (MalformedURLException e) {
                                    throw new IllegalArgumentException(e);
                                }
                            })
                            .orElseThrow(IllegalArgumentException::new);
                    attachments.add(new RichMediaAttachment(Type.valueOf(type), url));
                    break;
                case "LOCATION":
                    final Double latitude = getPropertyAsDouble(attachmentJsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LAT)
                            .orElseThrow(IllegalArgumentException::new);
                    final Double longitude = getPropertyAsDouble(attachmentJsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LONG)
                            .orElseThrow(IllegalArgumentException::new);
                    attachments.add(new LocationAttachment(latitude, longitude));
                    break;
                case "FALLBACK":
                    final String json = attachmentJsonObject.toString();
                    attachments.add(new FallbackAttachment(json));
                    break;
                default:
                    throw new IllegalArgumentException("attachment type '" + type + "' is not supported");
            }
        }

        return new AttachmentMessageEvent(senderId, recipientId, timestamp, messageId, attachments);
    }
}
