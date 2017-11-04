package com.github.messenger4j.send.message.quickreply;

import static java.util.Optional.empty;

import java.net.URL;
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
    private final Optional<URL> imageUrl;

    public static TextQuickReply create(@NonNull String title, @NonNull String payload) {
        return create(title, payload, empty());
    }

    public static TextQuickReply create(@NonNull String title, @NonNull String payload,
                                        @NonNull Optional<URL> imageUrl) {
        return new TextQuickReply(title, payload, imageUrl);
    }

    private TextQuickReply(String title, String payload, Optional<URL> imageUrl) {
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

    public Optional<URL> imageUrl() {
        return imageUrl;
    }
}
