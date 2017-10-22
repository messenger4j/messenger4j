package com.github.messenger4j.send.templates;

import com.github.messenger4j.send.buttons.Button;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Jan Zarnikov
 * @since 0.7.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ListTemplate extends Template {

    private final List<Element> elements;
    private final TopElementStyle topElementStyle;
    private final List<Button> buttons;

    public static Builder newBuilder(@NonNull List<Element> elements) {
        return new Builder(elements);
    }

    public ListTemplate(@NonNull List<Element> elements, TopElementStyle topElementStyle, List<Button> buttons) {
        super(TemplateType.LIST);
        this.elements = elements;
        this.topElementStyle = topElementStyle;
        this.buttons = buttons;
    }

    public List<Element> elements() {
        return elements;
    }

    public Optional<TopElementStyle> topElementStyle() {
        return Optional.ofNullable(topElementStyle);
    }

    public Optional<List<Button>> buttons() {
        return Optional.ofNullable(buttons);
    }

    /**
     * @since 0.7.0
     */
    public enum TopElementStyle {
        LARGE,
        COMPACT
    }

    /**
     * @since 0.7.0
     */
    public static final class Builder {

        private final List<Element> elements;
        private TopElementStyle topElementStyle;
        private List<Button> buttons;

        private Builder(List<Element> elements) {
            this.elements = elements;
        }

        public Builder topElementStyle(@NonNull TopElementStyle topElementStyle) {
            this.topElementStyle = topElementStyle;
            return this;
        }

        public Builder buttons(@NonNull List<Button> buttons) {
            this.buttons = buttons;
            return this;
        }

        public ListTemplate build() {
            return new ListTemplate(elements, topElementStyle, buttons);
        }
    }
}
