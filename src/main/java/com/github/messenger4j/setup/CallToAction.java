package com.github.messenger4j.setup;

import static com.github.messenger4j.setup.CallToActionType.NESTED;
import static com.github.messenger4j.setup.CallToActionType.POSTBACK;
import static com.github.messenger4j.setup.CallToActionType.WEB_URL;
import static com.github.messenger4j.setup.WebviewShareButtonState.HIDE;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.internal.PreConditions;
import com.google.gson.annotations.SerializedName;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
@ToString
@EqualsAndHashCode
public final class CallToAction {

    private final CallToActionType type;
    private final String title;
    private final URL url;
    private final String payload;
    private final List<CallToAction> callToActions;
    private final WebviewHeightRatio webviewHeightRatio;
    private final Boolean messengerExtensions;
    private final URL fallbackUrl;
    @SerializedName("webviewShareButton")
    private final WebviewShareButtonState webviewShareButtonState;

    public static Builder newBuilder() {
        return new Builder();
    }

    public CallToAction(@NonNull CallToActionType type, @NonNull String title, URL url, String payload,
                        List<CallToAction> callToActions, WebviewHeightRatio webviewHeightRatio,
                        Boolean messengerExtensions, URL fallbackUrl, Boolean hideWebviewShareButton) {

        this.type = type;
        this.title = title;
        this.url = url;
        this.payload = payload;
        this.callToActions = callToActions != null ? Collections.unmodifiableList(new ArrayList<>(callToActions)) : null;
        this.webviewHeightRatio = webviewHeightRatio;
        this.messengerExtensions = messengerExtensions;
        this.fallbackUrl = fallbackUrl;
        this.webviewShareButtonState = hideWebviewShareButton != null && hideWebviewShareButton ? HIDE : null;
    }

    public CallToActionType type() {
        return type;
    }

    public String title() {
        return title;
    }

    public Optional<URL> url() {
        return Optional.ofNullable(url);
    }

    public Optional<String> payload() {
        return Optional.ofNullable(payload);
    }

    public Optional<List<CallToAction>> callToActions() {
        return Optional.ofNullable(callToActions);
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

    public boolean isWebviewShareButtonHidden() {
        return webviewShareButtonState != null && webviewShareButtonState == HIDE;
    }

    public static final class Builder {

        private CallToActionType callToActionType;
        private String title;
        private URL url;
        private String payload;
        private List<CallToAction> callToActions;
        private WebviewHeightRatio webviewHeightRatio;
        private Boolean messengerExtensions;
        private URL fallbackUrl;
        private Boolean hideWebviewShareButton;

        public Builder type(@NonNull CallToActionType callToActionType) {
            this.callToActionType = callToActionType;
            return this;
        }

        public Builder title(@NonNull String title) {
            this.title = title;
            return this;
        }

        public Builder url(@NonNull URL url) {
            this.url = url;
            return this;
        }

        public Builder payload(@NonNull String payload) {
            this.payload = payload;
            return this;
        }

        public Builder callToActions(@NonNull List<CallToAction> callToActions) {
            this.callToActions = callToActions;
            return this;
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

        public Builder hideWebviewShareButton(boolean hideWebviewShareButton) {
            this.hideWebviewShareButton = hideWebviewShareButton;
            return this;
        }

        public CallToAction build() {
            if (callToActionType == WEB_URL) {
                PreConditions.notNull(url, "url");
            }
            if (callToActionType == POSTBACK) {
                PreConditions.notNull(payload, "payload");
            }
            if (callToActionType == NESTED) {
                PreConditions.notNull(callToActions, "callToActions");
            }

            return new CallToAction(callToActionType, title, url, payload, callToActions, webviewHeightRatio,
                    messengerExtensions, fallbackUrl, hideWebviewShareButton);
        }
    }
}
