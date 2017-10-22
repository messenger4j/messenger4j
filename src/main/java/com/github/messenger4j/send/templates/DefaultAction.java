package com.github.messenger4j.send.templates;

import static com.github.messenger4j.send.buttons.Button.ButtonType.WEB_URL;

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

    private final Button.ButtonType type;
    private final URL url;
    private final WebviewHeightRatio webviewHeightRatio;
    private final Boolean messengerExtensions;
    private final URL fallbackUrl;

    public static Builder newBuilder(@NonNull URL url) {
        return new Builder(url);
    }

    public DefaultAction(@NonNull URL url, WebviewHeightRatio webviewHeightRatio, Boolean messengerExtensions,
                         URL fallbackUrl) {
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
        return Optional.ofNullable(webviewHeightRatio);
    }

    public Optional<Boolean> messengerExtensions() {
        return Optional.ofNullable(messengerExtensions);
    }

    public Optional<URL> fallbackUrl() {
        return Optional.ofNullable(fallbackUrl);
    }

    /**
     * @author Max Grabenhorst
     * @since 0.1.0
     */
    public static final class Builder {

        private final URL url;
        private WebviewHeightRatio webviewHeightRatio;
        private Boolean messengerExtensions;
        private URL fallbackUrl;

        private Builder(URL url) {
            this.url = url;
        }

        public Builder webviewHeightRatio(@NonNull WebviewHeightRatio webviewHeightRatio) {
            this.webviewHeightRatio = webviewHeightRatio;
            return this;
        }

        public Builder messengerExtensions(boolean messengerExtensions) {
            this.messengerExtensions = messengerExtensions;
            return this;
        }

        public Builder fallbackUrl(@NonNull URL fallbackUrl) {
            this.fallbackUrl = fallbackUrl;
            return this;
        }

        public DefaultAction build() {
            return new DefaultAction(url, webviewHeightRatio, messengerExtensions, fallbackUrl);
        }
    }
}