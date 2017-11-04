package com.github.messenger4j.send.message.richmedia;

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
public final class UrlRichMediaAsset extends RichMediaAsset {

    private final URL url;
    private final Optional<Boolean> isReusable;

    public static UrlRichMediaAsset create(@NonNull Type type, @NonNull URL url) {
        return create(type, url, empty());
    }

    public static UrlRichMediaAsset create(@NonNull Type type, @NonNull URL url, @NonNull Optional<Boolean> isReusable) {
        return new UrlRichMediaAsset(type, url, isReusable);
    }

    private UrlRichMediaAsset(Type type, URL url, Optional<Boolean> isReusable) {
        super(type);
        this.url = url;
        this.isReusable = isReusable;
    }

    public URL url() {
        return url;
    }

    public Optional<Boolean> isReusable() {
        return isReusable;
    }
}
