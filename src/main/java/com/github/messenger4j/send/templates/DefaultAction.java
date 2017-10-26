package com.github.messenger4j.send.templates;

import static com.github.messenger4j.send.buttons.Button.Type.WEB_URL;
import static java.util.Optional.empty;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.send.buttons.Button;
import java.net.URL;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class DefaultAction {

    private final Button.Type type;
    private final URL url;
    private final Optional<WebviewHeightRatio> webviewHeightRatio;
    private final Optional<Boolean> messengerExtensions;
    private final Optional<URL> fallbackUrl;

    public static DefaultAction create(@NonNull URL url) {
        return create(url, empty(), empty(), empty());
    }

    public static DefaultAction create(@NonNull URL url, @NonNull Optional<WebviewHeightRatio> webviewHeightRatio,
                                       @NonNull Optional<Boolean> messengerExtensions, @NonNull Optional<URL> fallbackUrl) {
        return new DefaultAction(url, webviewHeightRatio, messengerExtensions, fallbackUrl);
    }

    private DefaultAction(URL url, Optional<WebviewHeightRatio> webviewHeightRatio, Optional<Boolean> messengerExtensions,
                          Optional<URL> fallbackUrl) {
        this.type = WEB_URL;
        this.url = url;
        this.webviewHeightRatio = webviewHeightRatio;
        this.messengerExtensions = messengerExtensions;
        this.fallbackUrl = fallbackUrl;
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