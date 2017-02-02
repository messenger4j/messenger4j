package com.github.messenger4j.send.buttons;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.internal.PreConditions;

import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class UrlButton extends TitleButton {

    private final String url;
    private final WebviewHeightRatio webviewHeightRatio;

    private UrlButton(Builder builder) {
        super(ButtonType.WEB_URL, builder.title);
        url = builder.url;
        webviewHeightRatio = builder.webviewHeightRatio;
    }

    @Override
    public boolean isUrlButton() {
        return true;
    }

    @Override
    public UrlButton asUrlButton() {
        return this;
    }

    public String getUrl() {
        return url;
    }

    public WebviewHeightRatio getWebviewHeightRatio() {
        return webviewHeightRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UrlButton urlButton = (UrlButton) o;
        return Objects.equals(url, urlButton.url) &&
                webviewHeightRatio == urlButton.webviewHeightRatio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url, webviewHeightRatio);
    }

    @Override
    public String toString() {
        return "UrlButton{" +
                "url='" + url + '\'' +
                ", webviewHeightRatio=" + webviewHeightRatio +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private static final int TITLE_CHARACTER_LIMIT = 20;

        private final String title;
        private final String url;
        private WebviewHeightRatio webviewHeightRatio;
        private final ListBuilder listBuilder;

        Builder(String title, String url, ListBuilder listBuilder) {
            PreConditions.notNullOrBlank(title, "title");
            PreConditions.lengthNotGreaterThan(title, TITLE_CHARACTER_LIMIT, "title");
            PreConditions.notNullOrBlank(url, "url");

            this.title = title;
            this.url = url;
            this.listBuilder = listBuilder;
        }

        public Builder webviewHeightRatio(WebviewHeightRatio webviewHeightRatio) {
            PreConditions.notNull(webviewHeightRatio, "webviewHeightRatio");
            this.webviewHeightRatio = webviewHeightRatio;
            return this;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new UrlButton(this));
        }
    }
}