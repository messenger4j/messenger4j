package com.github.messenger4j.send;

import static java.util.Optional.empty;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class TextQuickReply extends QuickReply {

    private final String title;
    private final String payload;
    private final Optional<String> imageUrl;

    public static TextQuickReply create(@NonNull String title, @NonNull String payload) {
        return create(title, payload, empty());
    }

    public static TextQuickReply create(@NonNull String title, @NonNull String payload,
                                        @NonNull Optional<String> imageUrl) {
        return new TextQuickReply(title, payload, imageUrl);
    }

    private TextQuickReply(String title, String payload, Optional<String> imageUrl) {
        super(ContentType.TEXT);
        this.title = title;
        this.payload = payload;
        this.imageUrl = imageUrl;
    }

    public String title() {
        return title;
    }

    public String payload() {
        return payload;
    }

    public Optional<String> imageUrl() {
        return imageUrl;
    }
}
