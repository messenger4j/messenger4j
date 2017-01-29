package com.github.messenger4j.setup;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.internal.PreConditions;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

import static com.github.messenger4j.setup.Type.POSTBACK;
import static com.github.messenger4j.setup.Type.URL;

/**
 * Created by andrey on 23.01.17.
 */

public class CallToAction {

    private Type type;

    private String title;

    private URL url;

    private String payload;

    @SerializedName("webview_height_ratio")
    private WebviewHeightRatio webviewHeightRatio;

    public static CallToAction.Builder newBuilder() {
        return new CallToAction.Builder();
    }

    public CallToAction(String payload) {
        this.payload = payload;
    }

    public CallToAction(Type type, String title, URL url, String payload, WebviewHeightRatio webviewHeightRatio) {
        this.type = type;
        this.title = title;
        this.url = url;
        this.payload = payload;
        this.webviewHeightRatio = webviewHeightRatio;
    }

    public static class Builder {

        private Type type;

        private String title;

        private URL url;

        private String payload;

        private WebviewHeightRatio webviewHeightRatio;

        public Builder type(Type type) {
            this.type = type;
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

        public CallToAction build() {

            PreConditions.notNull(type, "Type");
            PreConditions.notNull(type, "Title");
            if (type == URL) {
                PreConditions.notNull(url, "Url");
            }

            if (type == POSTBACK) {
                PreConditions.notNull(payload, "Payload");
            }

            return new CallToAction(type, title, url, payload, webviewHeightRatio);
        }

    }
}
