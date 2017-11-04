package com.github.messenger4j.send.message.template.button;

import static java.util.Optional.empty;

import com.github.messenger4j.common.WebviewHeightRatio;
import java.net.URL;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class UrlButton extends Button {

    private final String title;
    private final URL url;
    private final Optional<WebviewHeightRatio> webviewHeightRatio;
    private final Optional<Boolean> messengerExtensions;
    private final Optional<URL> fallbackUrl;

    public static UrlButton create(@NonNull String title, @NonNull URL url) {
        return create(title, url, empty(), empty(), empty());
    }

    public static UrlButton create(@NonNull String title, @NonNull URL url, @NonNull Optional<WebviewHeightRatio> webviewHeightRatio,
                                   @NonNull Optional<Boolean> messengerExtensions, @NonNull Optional<URL> fallbackUrl) {
        return new UrlButton(title, url, webviewHeightRatio, messengerExtensions, fallbackUrl);
    }

    private UrlButton(String title, URL url, Optional<WebviewHeightRatio> webviewHeightRatio,
                      Optional<Boolean> messengerExtensions, Optional<URL> fallbackUrl) {
        super(Type.WEB_URL);
        this.title = title;
        this.url = url;
        this.webviewHeightRatio = webviewHeightRatio;
        this.messengerExtensions = messengerExtensions;
        this.fallbackUrl = fallbackUrl;
    }

    @Override
    public boolean isUrlButton() {
        return true;
    }

    @Override
    public UrlButton asUrlButton() {
        return this;
    }

    public String title() {
        return title;
    }

    public URL url() {
        return url;
    }

    public Optional<WebviewHeightRatio> webviewHeightRatio() {
        return webviewHeightRatio;
    }

    public Optional<Boolean> messengerExtensions() {
        return messengerExtensions;
    }

    public Optional<URL> fallbackUrl() {
        return fallbackUrl;
    }
}
