package com.github.messenger4j.send;

import com.github.messenger4j.internal.PreConditions;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class BinaryAttachment extends Message.Attachment {

    private final Type type;
    private final Payload payload;

    public static Builder newBuilder(Type type) {
        return new Builder(type);
    }

    private BinaryAttachment(UrlBuilder builder) {
        type = builder.type;
        payload = new Payload(builder.attachmentUrl, builder.isReusable);
    }

    private BinaryAttachment(AttachmentIdBuilder builder) {
        type = builder.type;
        payload = new Payload(builder.attachmentId);
    }

    public Type getType() {
        return this.type;
    }

    public Payload getPayload() {
        return this.payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryAttachment that = (BinaryAttachment) o;
        return type == that.type &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, payload);
    }

    @Override
    public String toString() {
        return "BinaryAttachment{" +
                "type=" + type +
                ", payload=" + payload +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum Type {
        IMAGE,
        AUDIO,
        VIDEO,
        FILE;
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Payload {

        private final String url;
        private final Boolean isReusable;
        private final String attachmentId;

        private Payload(String url, Boolean isReusable) {
            this.url = url;
            this.isReusable = isReusable;
            this.attachmentId = null;
        }

        private Payload(String attachmentId) {
            this.attachmentId = attachmentId;
            this.url = null;
            this.isReusable = null;
        }

        public String getUrl() {
            return this.url;
        }

        public Boolean getReusable() {
            return this.isReusable;
        }

        public String getAttachmentId() {
            return this.attachmentId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Payload payload = (Payload) o;
            return Objects.equals(url, payload.url) &&
                    Objects.equals(isReusable, payload.isReusable) &&
                    Objects.equals(attachmentId, payload.attachmentId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(url, isReusable, attachmentId);
        }

        @Override
        public String toString() {
            return "Payload{" +
                    "url='" + url + '\'' +
                    ", isReusable=" + isReusable +
                    ", attachmentId='" + attachmentId + '\'' +
                    '}';
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private final Type type;

        private Builder(Type type) {
            PreConditions.notNull(type, "type");
            this.type = type;
        }

        public UrlBuilder url(String attachmentUrl) {
            return new UrlBuilder(this.type, attachmentUrl);
        }

        public AttachmentIdBuilder attachmentId(String attachmentId) {
            return new AttachmentIdBuilder(this.type, attachmentId);
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class UrlBuilder {

        private final Type type;
        private final String attachmentUrl;
        private Boolean isReusable;

        private UrlBuilder(Type type, String attachmentUrl) {
            PreConditions.notNullOrBlank(attachmentUrl, "attachmentUrl");
            this.type = type;
            this.attachmentUrl = attachmentUrl;
        }

        public UrlBuilder isReusable(boolean isReusable) {
            this.isReusable = isReusable;
            return this;
        }

        public BinaryAttachment build() {
            return new BinaryAttachment(this);
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class AttachmentIdBuilder {

        private final Type type;
        private final String attachmentId;

        private AttachmentIdBuilder(Type type, String attachmentId) {
            PreConditions.notNullOrBlank(attachmentId, "attachmentId");
            this.type = type;
            this.attachmentId = attachmentId;
        }

        public BinaryAttachment build() {
            return new BinaryAttachment(this);
        }
    }
}