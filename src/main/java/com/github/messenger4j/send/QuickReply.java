package com.github.messenger4j.send;

import com.github.messenger4j.internal.Assert;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class QuickReply {

    @SerializedName("content_type")
    private final ContentType contentType;
    private final String title;
    private final String payload;
    @SerializedName("image_url")
    private final String imageUrl;

    public static ListBuilder newListBuilder() {
        return new ListBuilder();
    }

    private QuickReply(TextBuilder builder) {
        contentType = builder.contentType;
        title = builder.title;
        payload = builder.payload;
        imageUrl = builder.imageUrl;
    }

    private QuickReply(LocationBuilder builder) {
        contentType = builder.contentType;
        title = null;
        payload = null;
        imageUrl = null;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public String getTitle() {
        return this.title;
    }

    public String getPayload() {
        return this.payload;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuickReply that = (QuickReply) o;
        return contentType == that.contentType &&
                Objects.equals(title, that.title) &&
                Objects.equals(payload, that.payload) &&
                Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType, title, payload, imageUrl);
    }

    @Override
    public String toString() {
        return "QuickReply{" +
                "contentType=" + contentType +
                ", title='" + title + '\'' +
                ", payload='" + payload + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum ContentType {
        @SerializedName("text")
        TEXT,
        @SerializedName("location")
        LOCATION
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class ListBuilder {

        private final List<QuickReply> quickReplies;

        private ListBuilder() {
            this.quickReplies = new ArrayList<>(10);
        }

        public TextBuilder addTextQuickReply(String title, String payload) {
            return new TextBuilder(title, payload, this);
        }

        public LocationBuilder addLocationQuickReply() {
            return new LocationBuilder(this);
        }

        private ListBuilder addQuickReplyToList(QuickReply quickReply) {
            this.quickReplies.add(quickReply);
            return this;
        }

        public List<QuickReply> build() {
            return Collections.unmodifiableList(new ArrayList<>(this.quickReplies));
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class TextBuilder {

        private static final int TITLE_CHARACTER_LIMIT = 20;
        private static final int PAYLOAD_CHARACTER_LIMIT = 1000;

        private final ContentType contentType;
        private final String title;
        private final String payload;
        private String imageUrl;
        private final ListBuilder listBuilder;

        private TextBuilder(String title, String payload, ListBuilder listBuilder) {
            Assert.notNullOrBlank(title, "title");
            Assert.lengthNotGreaterThan(title, TITLE_CHARACTER_LIMIT, "title");

            Assert.notNullOrBlank(payload, "payload");
            Assert.lengthNotGreaterThan(payload, PAYLOAD_CHARACTER_LIMIT, "payload");

            this.contentType = ContentType.TEXT;
            this.title = title;
            this.payload = payload;
            this.listBuilder = listBuilder;
        }

        public TextBuilder imageUrl(String imageUrl) {
            Assert.notNullOrBlank(imageUrl, "imageUrl");
            this.imageUrl = imageUrl;
            return this;
        }

        public ListBuilder toList() {
            return this.listBuilder.addQuickReplyToList(new QuickReply(this));
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class LocationBuilder {

        private final ContentType contentType;
        private final ListBuilder listBuilder;

        private LocationBuilder(ListBuilder listBuilder) {
            this.contentType = ContentType.LOCATION;
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addQuickReplyToList(new QuickReply(this));
        }
    }
}