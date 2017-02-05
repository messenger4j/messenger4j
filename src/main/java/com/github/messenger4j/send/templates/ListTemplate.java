package com.github.messenger4j.send.templates;

import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.internal.PreConditions;
import com.github.messenger4j.send.buttons.Button;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Jan Zarnikov
 * @since 0.7.0
 */
public final class ListTemplate extends Template {

    private final TopElementStyle topElementStyle;
    private final List<Button> buttons;
    private final List<Element> elements;

    public static Builder newBuilder(TopElementStyle topElementStyle) {
        return new Builder(topElementStyle);
    }

    private ListTemplate(Builder builder) {
        super(TemplateType.LIST);
        this.topElementStyle = builder.topElementStyle;
        this.buttons = builder.buttons;
        this.elements = builder.elements;
    }

    public enum TopElementStyle {
        LARGE,
        COMPACT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ListTemplate that = (ListTemplate) o;
        return topElementStyle == that.topElementStyle &&
                Objects.equals(buttons, that.buttons) &&
                Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), topElementStyle, buttons, elements);
    }

    @Override
    public String toString() {
        return "ListTemplate{" +
                "topElementStyle=" + topElementStyle +
                ", buttons=" + buttons +
                ", elements=" + elements +
                '}';
    }

    /**
     * @since 0.7.0
     */
    public static final class Builder {

        private static final int BUTTONS_LIMIT = 1;
        private final TopElementStyle topElementStyle;

        private List<Button> buttons;
        private List<Element> elements;

        public Builder(TopElementStyle topElementStyle) {
            this.topElementStyle = topElementStyle;
        }

        public Builder buttons(List<Button> buttons) {
            PreConditions.notNullOrEmpty(buttons, "buttons");
            PreConditions.sizeNotGreaterThan(buttons, BUTTONS_LIMIT, "buttons");
            this.buttons = buttons;
            return this;
        }

        public Element.ListBuilder addElements() {
            return new Element.ListBuilder(this);
        }

        private Builder elements(List<Element> elements) {
            this.elements = elements;
            return this;
        }

        public ListTemplate build() {
            PreConditions.notNullOrEmpty(this.elements, "elements");
            if (topElementStyle == TopElementStyle.LARGE) {
                PreConditions.notNullOrBlank(elements.get(0).imageUrl, "imageUrl");
            }
            return new ListTemplate(this);
        }
    }

    /**
     * @since 0.7.0
     */
    public static final class Element {

        private final String title;
        private final String subtitle;
        private final String imageUrl;
        private final List<Button> buttons;
        private final DefaultAction defaultAction;

        private Element(Builder builder) {
            this.title = builder.title;
            this.subtitle = builder.subtitle;
            this.imageUrl = builder.imageUrl;
            this.buttons = builder.buttons;
            this.defaultAction = builder.defaultAction;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return Objects.equals(title, element.title) &&
                    Objects.equals(subtitle, element.subtitle) &&
                    Objects.equals(imageUrl, element.imageUrl) &&
                    Objects.equals(buttons, element.buttons) &&
                    Objects.equals(defaultAction, element.defaultAction);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, subtitle, imageUrl, buttons, defaultAction);
        }

        @Override
        public String toString() {
            return "Element{" +
                    "title='" + title + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", buttons=" + buttons +
                    ", defaultAction=" + defaultAction +
                    '}';
        }

        /**
         * @since 0.7.0
         */
        public static final class ListBuilder {

            private final List<Element> elements;
            private final ListTemplate.Builder listTemplateBuilder;

            private static final int MIN_ELEMENTS = 2;
            private static final int MAX_ELEMENTS = 4;

            private ListBuilder(ListTemplate.Builder listTemplateBuilder) {
                this.listTemplateBuilder = listTemplateBuilder;
                this.elements = new ArrayList<>();
            }

            public Builder addElement(String title) {
                return new Builder(title, this);
            }

            public ListTemplate.Builder done() {
                PreConditions.sizeNotGreaterThan(this.elements, MAX_ELEMENTS, "elements", IllegalStateException.class);
                PreConditions.sizeNotLessThan(this.elements, MIN_ELEMENTS, "elements", IllegalStateException.class);
                return this.listTemplateBuilder.elements(Collections.unmodifiableList(new ArrayList<>(this.elements)));
            }

            private void addElementToList(Element element) {
                this.elements.add(element);
            }
        }

        /**
         * @since 0.7.0
         */
        public static final class Builder {

            private static final int BUTTONS_LIMIT = 1;
            private static final int TITLE_CHARACTER_LIMIT = 80;
            private static final int SUBTITLE_CHARACTER_LIMIT = 80;

            private final String title;
            private final ListBuilder listBuilder;
            private String subtitle;
            private String imageUrl;
            private List<Button> buttons;
            private DefaultAction defaultAction;

            public Builder(String title, ListBuilder listBuilder) {
                PreConditions.notNullOrBlank(title, "title");
                PreConditions.lengthNotGreaterThan(title, TITLE_CHARACTER_LIMIT, "title");
                this.title = title;
                this.listBuilder = listBuilder;
            }

            public Builder subtitle(String subtitle) {
                PreConditions.notNullOrBlank(subtitle, "subtitle");
                PreConditions.lengthNotGreaterThan(subtitle, SUBTITLE_CHARACTER_LIMIT, "subtitle");
                this.subtitle = subtitle;
                return this;
            }

            public Builder imageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
                return this;
            }

            public Builder buttons(List<Button> buttons) {
                PreConditions.notNullOrEmpty(buttons, "buttons");
                PreConditions.sizeNotGreaterThan(buttons, BUTTONS_LIMIT, "buttons", IllegalStateException.class);
                this.buttons = buttons;
                return this;
            }

            public DefaultAction.Builder addDefaultAction(String url) {
                return new DefaultAction.Builder(url, this);
            }

            private Builder defaulAction(DefaultAction defaultAction) {
                this.defaultAction = defaultAction;
                return this;
            }

            public ListBuilder toList() {
                listBuilder.addElementToList(new Element(this));
                return listBuilder;
            }

        }

        /**
         * @since 0.7.0
         */
        public static final class DefaultAction {

            private final Button.ButtonType type;
            private final String url;
            private final WebviewHeightRatio webviewHeightRatio;
            private final Boolean messengerExtensions;
            private final String fallbackUrl;

            public DefaultAction(Builder builder) {
                this.type = Button.ButtonType.WEB_URL;
                this.url = builder.url;
                this.webviewHeightRatio = builder.webviewHeightRatio;
                this.messengerExtensions = builder.messengerExtensions;
                this.fallbackUrl = builder.fallbackUrl;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                DefaultAction that = (DefaultAction) o;
                return type == that.type &&
                        Objects.equals(url, that.url) &&
                        webviewHeightRatio == that.webviewHeightRatio &&
                        Objects.equals(messengerExtensions, that.messengerExtensions) &&
                        Objects.equals(fallbackUrl, that.fallbackUrl);
            }

            @Override
            public int hashCode() {
                return Objects.hash(type, url, webviewHeightRatio, messengerExtensions, fallbackUrl);
            }

            @Override
            public String toString() {
                return "DefaultAction{" +
                        "type=" + type +
                        ", url='" + url + '\'' +
                        ", webviewHeightRatio=" + webviewHeightRatio +
                        ", messengerExtensions=" + messengerExtensions +
                        ", fallbackUrl='" + fallbackUrl + '\'' +
                        '}';
            }

            /**
             * @since 0.7.0
             */
            public static final class Builder {
                private final String url;
                private WebviewHeightRatio webviewHeightRatio;
                private Boolean messengerExtensions;
                private String fallbackUrl;
                private final Element.Builder elementBuilder;


                public Builder(String url, Element.Builder builder) {
                    this.url = url;
                    elementBuilder = builder;
                }

                public Builder webviewHeightRatio(WebviewHeightRatio webviewHeightRatio) {
                    this.webviewHeightRatio = webviewHeightRatio;
                    return this;
                }

                public Builder messengerExtensions(Boolean messengerExtensions) {
                    this.messengerExtensions = messengerExtensions;
                    return this;
                }

                public Builder fallbackUrl(String fallbackUrl) {
                    PreConditions.notNullOrBlank(fallbackUrl, "fallbackUrl");
                    this.fallbackUrl = fallbackUrl;
                    return this;
                }

                public Element.Builder done() {
                    return elementBuilder.defaulAction(new DefaultAction(this));
                }
            }

        }
    }
}
