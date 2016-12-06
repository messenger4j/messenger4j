package com.github.messenger4j.receive.events;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ATTACHMENTS;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_COORDINATES;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
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
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDouble;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This event will occur when a message with {@link Attachment}s (image, audio, video, file or location)
 * has been sent to your page.
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
 * @see Attachment
 */
public final class AttachmentMessageEvent extends MessageEvent {

    private final List<Attachment> attachments;

    /**
     * <b>Internal</b> method to create an instance of {@link AttachmentMessageEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link AttachmentMessageEvent}
     */
    public static AttachmentMessageEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date timestamp = getPropertyAsDate(jsonObject, PROP_TIMESTAMP);
        final String mid = getPropertyAsString(jsonObject, PROP_MESSAGE, PROP_MID);
        final JsonArray attachmentsJsonArray = getProperty(jsonObject, PROP_MESSAGE, PROP_ATTACHMENTS).getAsJsonArray();

        final List<Attachment> attachments = new ArrayList<>(attachmentsJsonArray.size());
        for (JsonElement attachmentJsonElement : attachmentsJsonArray) {
            attachments.add(Attachment.fromJson(attachmentJsonElement.getAsJsonObject()));
        }

        return new AttachmentMessageEvent(senderId, recipientId, timestamp, mid, attachments);
    }

    public AttachmentMessageEvent(String senderId, String recipientId, Date timestamp, String mid,
                                   List<Attachment> attachments) {

        super(senderId, recipientId, timestamp, mid);
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
        AttachmentMessageEvent that = (AttachmentMessageEvent) o;
        return Objects.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attachments);
    }

    @Override
    public String toString() {
        return "AttachmentMessageEvent{" +
                "attachments=" + attachments +
                "} super=" + super.toString();
    }

    /**
     * An attachment contained within a message.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     * @see AttachmentType
     * @see Payload
     */
    public static final class Attachment {

        private final AttachmentType type;
        private final Payload payload;

        private static Attachment fromJson(JsonObject jsonObject) {
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

    /**
     * The possible attachment types.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     * @see Attachment
     */
    public enum AttachmentType {
        IMAGE, AUDIO, VIDEO, FILE, LOCATION;
    }

    /**
     * This is the base implementation of the different {@link Payload} types providing the functionality to check
     * the type of the concrete implementation and to return the concrete type.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     * @see Attachment
     */
    public abstract static class Payload {

        public boolean isUnsupportedPayload() {
            return false;
        }

        public boolean isBinaryPayload() {
            return false;
        }

        public boolean isLocationPayload() {
            return false;
        }

        public BinaryPayload asBinaryPayload() {
            throw new UnsupportedOperationException("not a BinaryPayload");
        }

        public LocationPayload asLocationPayload() {
            throw new UnsupportedOperationException("not a LocationPayload");
        }

        @Override
        public String toString() {
            return "Payload{}";
        }
    }

    /**
     * {@link Payload} implementation that is used when the {@link Payload} of the {@link Attachment} is not supported.
     *
     * @author Max Grabenhorst
     * @see Attachment
     * @since 0.6.0
     */
    public static final class UnsupportedPayload extends Payload {

        @Override
        public boolean isUnsupportedPayload() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hash(getClass().getSimpleName());
        }

        @Override
        public String toString() {
            return "UnsupportedPayload{} super=" + super.toString();
        }
    }

    /**
     * {@link Payload} implementation that is used when the {@link Payload} of the {@link Attachment} is a binary attachment.
     * E.g. an image, file, audio or video attachment.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     * @see Attachment
     */
    public static final class BinaryPayload extends Payload {

        private final String url;

        private static BinaryPayload fromJson(JsonObject jsonObject) {
            final String url = getPropertyAsString(jsonObject, PROP_PAYLOAD, PROP_URL);
            return new BinaryPayload(url);
        }

        public BinaryPayload(String url) {
            this.url = url;
        }

        @Override
        public boolean isBinaryPayload() {
            return true;
        }

        @Override
        public BinaryPayload asBinaryPayload() {
            return this;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BinaryPayload that = (BinaryPayload) o;
            return Objects.equals(url, that.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(url);
        }

        @Override
        public String toString() {
            return "BinaryPayload{" +
                    "url='" + url + '\'' +
                    "} super=" + super.toString();
        }
    }

    /**
     * {@link Payload} implementation that is used when the {@link Payload} of the {@link Attachment} is a location.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     * @see Attachment
     * @see Coordinates
     */
    public static final class LocationPayload extends Payload {

        private final Coordinates coordinates;

        private static LocationPayload fromJson(JsonObject jsonObject) {
            final Double latitude = getPropertyAsDouble(jsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LAT);
            final Double longitude = getPropertyAsDouble(jsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LONG);
            return new LocationPayload(latitude, longitude);
        }

        public LocationPayload(Double latitude, Double longitude) {
            this.coordinates = new Coordinates(latitude, longitude);
        }

        @Override
        public boolean isLocationPayload() {
            return true;
        }

        @Override
        public LocationPayload asLocationPayload() {
            return this;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LocationPayload that = (LocationPayload) o;
            return Objects.equals(coordinates, that.coordinates);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coordinates);
        }

        @Override
        public String toString() {
            return "LocationPayload{" +
                    "coordinates=" + coordinates +
                    "} super=" + super.toString();
        }
    }

    /**
     * Representing the location of a user.
     *
     * @author Max Grabenhorst
     * @see LocationPayload
     * @since 0.6.0
     */
    public static final class Coordinates {

        private final Double latitude;
        private final Double longitude;

        private Coordinates(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public Double getLatitude() {
            return latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinates that = (Coordinates) o;
            return Objects.equals(latitude, that.latitude) &&
                    Objects.equals(longitude, that.longitude);
        }

        @Override
        public int hashCode() {
            return Objects.hash(latitude, longitude);
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
}