package com.github.messenger4j.receive.attachments;

import com.github.messenger4j.receive.events.AttachmentMessageEvent;
import com.google.gson.JsonObject;

import java.util.Objects;

import static com.github.messenger4j.internal.JsonHelper.Constants.*;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

/**
 * An attachment contained within a message.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see AttachmentType
 * @see Payload
 */
public final class Attachment {

    private final AttachmentType type;
    private final Payload payload;

    public static Attachment fromJson(JsonObject jsonObject) {
        final String type = getPropertyAsString(jsonObject, PROP_TYPE);
        final AttachmentType attachmentType = (type == null ? null : AttachmentType.valueOf(type.toUpperCase()));

        Payload payload;
        if (hasProperty(jsonObject, PROP_PAYLOAD, PROP_URL)) {
            payload = BinaryPayload.fromJson(jsonObject);
        } else if (hasProperty(jsonObject, PROP_PAYLOAD, PROP_COORDINATES)) {
            payload = LocationPayload.fromJson(jsonObject);
        } else {
            payload = new UnsupportedPayload();
        }

        return new Attachment(attachmentType, payload);
    }

    public Attachment(AttachmentType type, Payload payload) {
        this.type = type;
        this.payload = payload;
    }

    public AttachmentType getType() {
        return type;
    }

    public Payload getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attachment that = (Attachment) o;
        return type == that.type &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, payload);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "type=" + type +
                ", payload=" + payload +
                '}';
    }
}
