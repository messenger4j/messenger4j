package com.github.messenger4j.send.message.template.common;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.send.message.template.button.Button;
import java.net.URL;
import java.util.List;
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
public final class Element {

    private final String title;
    private final Optional<String> subtitle;
    private final Optional<URL> imageUrl;
    private final Optional<DefaultAction> defaultAction;
    private final Optional<List<Button>> buttons;

    public static Element create(@NonNull String title, @NonNull Optional<String> subtitle, @NonNull Optional<URL> imageUrl,
                                 @NonNull Optional<DefaultAction> defaultAction, @NonNull Optional<List<Button>> buttons) {
        return new Element(title, subtitle, imageUrl, defaultAction, buttons);
    }

    private Element(String title, Optional<String> subtitle, Optional<URL> imageUrl, Optional<DefaultAction> defaultAction,
                    Optional<List<Button>> buttons) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.defaultAction = defaultAction;
        this.buttons = buttons.map(Lists::immutableList);
    }

    public String title() {
        return title;
    }

    public Optional<String> subtitle() {
        return subtitle;
    }

    public Optional<URL> imageUrl() {
        return imageUrl;
    }

    public Optional<DefaultAction> defaultAction() {
        return defaultAction;
    }

    public Optional<List<Button>> buttons() {
        return buttons;
    }
}
