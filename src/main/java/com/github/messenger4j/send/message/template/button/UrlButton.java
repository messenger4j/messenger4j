package com.github.messenger4j.send.message.template.button;

import static java.util.Optional.empty;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.common.WebviewShareButtonState;
import com.google.gson.annotations.SerializedName;
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
public final class UrlButton extends Button {

    private final String title;
    private final URL url;
    private final Optional<WebviewHeightRatio> webviewHeightRatio;
    private final Optional<Boolean> messengerExtensions;
    private final Optional<URL> fallbackUrl;
    @SerializedName("webview_share_button")
    private final Optional<WebviewShareButtonState> webviewShareButtonState;

    public static UrlButton create(@NonNull String title, @NonNull URL url) {
        return create(title, url, empty(), empty(), empty(), empty());
    }

    public static UrlButton create(@NonNull String title, @NonNull URL url,
                                   @NonNull Optional<WebviewHeightRatio> webviewHeightRatio) {
        return create(title, url, webviewHeightRatio, empty(), empty(), empty());
    }

    public static UrlButton create(@NonNull String title, @NonNull URL url, @NonNull Optional<WebviewHeightRatio> webviewHeightRatio,
                                   @NonNull Optional<Boolean> messengerExtensions, @NonNull Optional<URL> fallbackUrl,
                                   @NonNull Optional<WebviewShareButtonState> webviewShareButtonState) {
        return new UrlButton(title, url, webviewHeightRatio, messengerExtensions, fallbackUrl, webviewShareButtonState);
    }

    private UrlButton(String title, URL url, Optional<WebviewHeightRatio> webviewHeightRatio,
                      Optional<Boolean> messengerExtensions, Optional<URL> fallbackUrl,
                      Optional<WebviewShareButtonState> webviewShareButtonState) {
        super(Type.WEB_URL);
        this.title = title;
        this.url = url;
        this.webviewHeightRatio = webviewHeightRatio;
        this.messengerExtensions = messengerExtensions;
        this.fallbackUrl = fallbackUrl;
        this.webviewShareButtonState = webviewShareButtonState;
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

    public Optional<WebviewShareButtonState> webviewShareButtonState() {
        return webviewShareButtonState;
    }
}
