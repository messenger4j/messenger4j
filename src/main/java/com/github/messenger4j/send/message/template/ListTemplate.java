package com.github.messenger4j.send.message.template;

import static java.util.Optional.empty;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.common.Element;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Jan Zarnikov
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ListTemplate extends Template {

    private final List<Element> elements;
    private final Optional<TopElementStyle> topElementStyle;
    private final Optional<List<Button>> buttons;

    public static ListTemplate create(@NonNull List<Element> elements) {
        return create(elements, empty(), empty());
    }

    public static ListTemplate create(@NonNull List<Element> elements, @NonNull Optional<TopElementStyle> topElementStyle,
                                      @NonNull Optional<List<Button>> buttons) {
        return new ListTemplate(elements, topElementStyle, buttons);
    }

    private ListTemplate(List<Element> elements, Optional<TopElementStyle> topElementStyle, Optional<List<Button>> buttons) {
        super(Type.LIST);
        this.elements = Lists.immutableList(elements);
        this.topElementStyle = topElementStyle;
        this.buttons = buttons.map(Lists::immutableList);
    }

    public List<Element> elements() {
        return elements;
    }

    public Optional<TopElementStyle> topElementStyle() {
        return topElementStyle;
    }

    public Optional<List<Button>> buttons() {
        return buttons;
    }

    /**
     * @since 1.0.0
     */
    public enum TopElementStyle {
        LARGE,
        COMPACT
    }
}
