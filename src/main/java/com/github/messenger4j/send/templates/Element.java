package com.github.messenger4j.send.templates;

import com.github.messenger4j.send.buttons.Button;
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
    private final String subtitle;
    private final URL imageUrl;
    private final DefaultAction defaultAction;
    private final List<Button> buttons;

    public static Builder newBuilder(@NonNull String title) {
        return new Builder(title);
    }

    public Element(@NonNull String title, String subtitle, URL imageUrl, DefaultAction defaultAction,
                   List<Button> buttons) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageUrl = imageUrl;
        this.defaultAction = defaultAction;
        this.buttons = buttons;
    }

    public String title() {
        return title;
    }

    public Optional<String> subtitle() {
        return Optional.ofNullable(subtitle);
    }

    public Optional<URL> imageUrl() {
        return Optional.ofNullable(imageUrl);
    }

    public Optional<DefaultAction> defaultAction() {
        return Optional.ofNullable(defaultAction);
    }

    public Optional<List<Button>> buttons() {
        return Optional.ofNullable(buttons);
    }

    /**
     * @author Max Grabenhorst
     * @since 1.0.0
     */
    public static final class Builder {

        private final String title;
        private String subtitle;
        private URL imageUrl;
        private DefaultAction defaultAction;
        private List<Button> buttons;

        private Builder(String title) {
            this.title = title;
        }

        public Builder subtitle(@NonNull String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder imageUrl(@NonNull URL imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder defaultAction(@NonNull DefaultAction defaultAction) {
            this.defaultAction = defaultAction;
            return this;
        }

        public Builder buttons(@NonNull List<Button> buttons) {
            this.buttons = buttons;
            return this;
        }

        public Element build() {
            return new Element(title, subtitle, imageUrl, defaultAction, buttons);
        }
    }
}
