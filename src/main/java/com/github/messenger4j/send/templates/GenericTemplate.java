package com.github.messenger4j.send.templates;

import com.github.messenger4j.internal.PreConditions;
import com.github.messenger4j.send.buttons.Button;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class GenericTemplate extends Template {

    private final List<Element> elements;

    public static Builder newBuilder() {
        return new Builder();
    }

    private GenericTemplate(Builder builder) {
        super(TemplateType.GENERIC);
        elements = builder.elements;
    }

    public List<Element> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GenericTemplate that = (GenericTemplate) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elements);
    }

    @Override
    public String toString() {
        return "GenericTemplate{" +
                "elements=" + elements +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private static final int ELEMENTS_LIMIT = 10;

        private List<Element> elements;

        private Builder() {
        }

        public Element.ListBuilder addElements() {
            return new Element.ListBuilder(this);
        }

        private Builder elements(List<Element> elements) {
            this.elements = elements;
            return this;
        }

        public GenericTemplate build() {
            PreConditions.notNullOrEmpty(this.elements, "elements", IllegalStateException.class);
            PreConditions.sizeNotGreaterThan(this.elements, ELEMENTS_LIMIT, "elements", IllegalStateException.class);
            return new GenericTemplate(this);
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Element {

        private final String title;
        private final String itemUrl;
        private final String imageUrl;
        private final String subtitle;
        private final List<Button> buttons;

        private Element(Builder builder) {
            title = builder.title;
            itemUrl = builder.itemUrl;
            imageUrl = builder.imageUrl;
            subtitle = builder.subtitle;
            buttons = builder.buttons;
        }

        public String getTitle() {
            return title;
        }

        public String getItemUrl() {
            return itemUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Element element = (Element) o;
            return Objects.equals(title, element.title) &&
                    Objects.equals(itemUrl, element.itemUrl) &&
                    Objects.equals(imageUrl, element.imageUrl) &&
                    Objects.equals(subtitle, element.subtitle) &&
                    Objects.equals(buttons, element.buttons);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, itemUrl, imageUrl, subtitle, buttons);
        }

        @Override
        public String toString() {
            return "Element{" +
                    "title='" + title + '\'' +
                    ", itemUrl='" + itemUrl + '\'' +
                    ", imageUrl='" + imageUrl + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", buttons=" + buttons +
                    '}';
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class ListBuilder {

            private final List<Element> elements;
            private final GenericTemplate.Builder genericTemplateBuilder;

            private ListBuilder(GenericTemplate.Builder genericTemplateBuilder) {
                this.elements = new ArrayList<>(10);
                this.genericTemplateBuilder = genericTemplateBuilder;
            }

            public Builder addElement(String title) {
                return new Builder(title, this);
            }

            private ListBuilder addElementToList(Element element) {
                this.elements.add(element);
                return this;
            }

            public GenericTemplate.Builder done() {
                return this.genericTemplateBuilder.elements(Collections.unmodifiableList(new ArrayList<>(this.elements)));
            }
        }

        /**
         * @author Max Grabenhorst
         * @since 0.6.0
         */
        public static final class Builder {

            private static final int TITLE_CHARACTER_LIMIT = 80;
            private static final int SUBTITLE_CHARACTER_LIMIT = 80;
            private static final int BUTTONS_LIMIT = 3;

            private final String title;
            private String itemUrl;
            private String imageUrl;
            private String subtitle;
            private List<Button> buttons;
            private final ListBuilder listBuilder;

            private Builder(String title, ListBuilder listBuilder) {
                PreConditions.notNullOrBlank(title, "title");
                PreConditions.lengthNotGreaterThan(title, TITLE_CHARACTER_LIMIT, "title");

                this.title = title;
                this.listBuilder = listBuilder;
            }

            public Builder itemUrl(String itemUrl) {
                PreConditions.notNullOrBlank(itemUrl, "itemUrl");
                this.itemUrl = itemUrl;
                return this;
            }

            public Builder imageUrl(String imageUrl) {
                PreConditions.notNullOrBlank(imageUrl, "imageUrl");
                this.imageUrl = imageUrl;
                return this;
            }

            public Builder subtitle(String subtitle) {
                PreConditions.notNullOrBlank(subtitle, "subtitle");
                PreConditions.lengthNotGreaterThan(subtitle, SUBTITLE_CHARACTER_LIMIT, "subtitle");
                this.subtitle = subtitle;
                return this;
            }

            public Builder buttons(List<Button> buttons) {
                PreConditions.notNullOrEmpty(buttons, "buttons");
                PreConditions.sizeNotGreaterThan(buttons, BUTTONS_LIMIT, "buttons");
                this.buttons = buttons;
                return this;
            }

            public ListBuilder toList() {
                return this.listBuilder.addElementToList(new Element(this));
            }
        }
    }
}