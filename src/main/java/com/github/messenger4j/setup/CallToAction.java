package com.github.messenger4j.setup;

import static com.github.messenger4j.setup.CallToActionType.POSTBACK;
import static com.github.messenger4j.setup.CallToActionType.WEB_URL;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.internal.PreConditions;
import java.net.URL;
import java.util.Objects;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
public final class CallToAction {

    private final CallToActionType type;
    private final String title;
    private final URL url;
    private final String payload;
    private final WebviewHeightRatio webviewHeightRatio;
    private final Boolean messengerExtensions;
    private final URL fallbackUrl;

    public static CallToAction.Builder newBuilder() {
        return new CallToAction.Builder();
    }

    public CallToAction(String payload) {
        this(null, null, null, payload, null, null, null);
    }

    public CallToAction(CallToActionType type, String title, URL url, String payload,
                        WebviewHeightRatio webviewHeightRatio, Boolean messengerExtensions, URL fallbackUrl) {

        this.type = type;
        this.title = title;
        this.url = url;
        this.payload = payload;
        this.webviewHeightRatio = webviewHeightRatio;
        this.messengerExtensions = messengerExtensions;
        this.fallbackUrl = fallbackUrl;
    }

    public CallToActionType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public java.net.URL getUrl() {
        return url;
    }

    public String getPayload() {
        return payload;
    }

    public WebviewHeightRatio getWebviewHeightRatio() {
        return webviewHeightRatio;
    }

    public Boolean getMessengerExtensions() {
        return messengerExtensions;
    }

    public URL getFallbackUrl() {
        return fallbackUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallToAction that = (CallToAction) o;
        return type == that.type &&
                Objects.equals(title, that.title) &&
                Objects.equals(url, that.url) &&
                Objects.equals(payload, that.payload) &&
                webviewHeightRatio == that.webviewHeightRatio &&
                Objects.equals(messengerExtensions, that.messengerExtensions) &&
                Objects.equals(fallbackUrl, that.fallbackUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, title, url, payload, webviewHeightRatio, messengerExtensions, fallbackUrl);
    }

    @Override
    public String toString() {
        return "CallToAction{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", url=" + url +
                ", payload='" + payload + '\'' +
                ", webviewHeightRatio=" + webviewHeightRatio +
                ", messengerExtensions=" + messengerExtensions +
                ", fallbackUrl='" + fallbackUrl + '\'' +
                '}';
    }

    public static final class Builder {

        private CallToActionType callToActionType;
        private String title;
        private URL url;
        private String payload;
        private WebviewHeightRatio webviewHeightRatio;
        private Boolean messengerExtensions;
        private URL fallbackUrl;

        public Builder type(CallToActionType callToActionType) {
            this.callToActionType = callToActionType;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder url(URL url) {
            this.url = url;
            return this;
        }

        public Builder payload(String payload) {
            this.payload = payload;
            return this;
        }

        public Builder webviewHeightRatio(WebviewHeightRatio webviewHeightRatio) {
            this.webviewHeightRatio = webviewHeightRatio;
            return this;
        }

        public Builder messengerExtensions(Boolean messengerExtensions) {
            this.messengerExtensions = messengerExtensions;
            return this;
        }

        public Builder fallbackUrl(URL fallbackUrl) {
            this.fallbackUrl = fallbackUrl;
            return this;
        }

        public CallToAction build() {
            PreConditions.notNull(callToActionType, "type");
            PreConditions.notNullOrBlank(title, "title");
            if (callToActionType == WEB_URL) {
                PreConditions.notNull(url, "url");
            }

            if (callToActionType == POSTBACK) {
                PreConditions.notNullOrBlank(payload, "payload");
            }

            return new CallToAction(callToActionType, title, url, payload, webviewHeightRatio,
                    messengerExtensions, fallbackUrl);
        }
    }
}
