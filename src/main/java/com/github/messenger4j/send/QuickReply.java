package com.github.messenger4j.send;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString
@EqualsAndHashCode
public final class QuickReply {

    private final ContentType contentType;
    private final String title;
    private final String payload;
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

    public ContentType contentType() {
        return contentType;
    }

    public Optional<String> title() {
        return Optional.ofNullable(this.title);
    }

    public Optional<String> payload() {
        return Optional.ofNullable(this.payload);
    }

    public Optional<String> imageUrl() {
        return Optional.ofNullable(this.imageUrl);
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum ContentType {
        TEXT,
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

        public TextBuilder addTextQuickReply(@NonNull String title, @NonNull String payload) {
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

        private final ContentType contentType;
        private final String title;
        private final String payload;
        private String imageUrl;
        private final ListBuilder listBuilder;

        private TextBuilder(String title, String payload, ListBuilder listBuilder) {
            this.contentType = ContentType.TEXT;
            this.title = title;
            this.payload = payload;
            this.listBuilder = listBuilder;
        }

        public TextBuilder imageUrl(@NonNull String imageUrl) {
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