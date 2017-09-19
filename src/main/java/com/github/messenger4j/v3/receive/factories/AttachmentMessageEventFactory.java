package com.github.messenger4j.v3.receive.factories;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ATTACHMENTS;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_COORDINATES;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_IS_ECHO;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_LAT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_LONG;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_PAYLOAD;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_URL;
import static com.github.messenger4j.internal.JsonHelper.getProperty;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDouble;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsInstant;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.v3.receive.Attachment;
import com.github.messenger4j.v3.receive.AttachmentMessageEvent;
import com.github.messenger4j.v3.receive.FallbackAttachment;
import com.github.messenger4j.v3.receive.LocationAttachment;
import com.github.messenger4j.v3.receive.RichMediaAttachment;
import com.github.messenger4j.v3.receive.RichMediaAttachment.Type;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class AttachmentMessageEventFactory implements BaseEventFactory<AttachmentMessageEvent> {

    @Override
    public boolean isResponsible(@NonNull JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_ATTACHMENTS) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_IS_ECHO);
    }

    @Override
    public AttachmentMessageEvent createEventFromJson(@NonNull JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP).get();
        final String messageId = getPropertyAsString(messagingEvent, PROP_MESSAGE, PROP_MID);
        final JsonArray attachmentsJsonArray = getProperty(messagingEvent, PROP_MESSAGE, PROP_ATTACHMENTS).getAsJsonArray();

        final List<Attachment> attachments = new ArrayList<>(attachmentsJsonArray.size());
        for (JsonElement attachmentJsonElement : attachmentsJsonArray) {
            final JsonObject attachmentJsonObject = attachmentJsonElement.getAsJsonObject();
            final String type = getPropertyAsString(attachmentJsonObject, PROP_TYPE).toUpperCase();
            switch (type) {
                case "IMAGE":
                case "AUDIO":
                case "VIDEO":
                case "FILE":
                    final String url = getPropertyAsString(attachmentJsonObject, PROP_PAYLOAD, PROP_URL);
                    attachments.add(new RichMediaAttachment(Type.valueOf(type), url));
                    break;
                case "LOCATION":
                    final Double latitude = getPropertyAsDouble(attachmentJsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LAT);
                    final Double longitude = getPropertyAsDouble(attachmentJsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LONG);
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
