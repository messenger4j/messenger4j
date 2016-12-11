package com.github.messenger4j.send.templates;

import com.github.messenger4j.internal.PreConditions;
import com.github.messenger4j.send.buttons.Button;
import java.util.List;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class ButtonTemplate extends Template {

    private final String text;
    private final List<Button> buttons;

    public static Builder newBuilder(String text, List<Button> buttons) {
        return new Builder(text, buttons);
    }

    private ButtonTemplate(Builder builder) {
        super(TemplateType.BUTTON);
        text = builder.text;
        buttons = builder.buttons;
    }

    public String getText() {
        return text;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ButtonTemplate that = (ButtonTemplate) o;
        return Objects.equals(text, that.text) &&
                Objects.equals(buttons, that.buttons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, buttons);
    }

    @Override
    public String toString() {
        return "ButtonTemplate{" +
                "text='" + text + '\'' +
                ", buttons=" + buttons +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private static final int TEXT_CHARACTER_LIMIT = 320;
        private static final int BUTTONS_LIMIT = 3;

        private final String text;
        private final List<Button> buttons;

        private Builder(String text, List<Button> buttons) {
            PreConditions.notNullOrBlank(text, "text");
            PreConditions.lengthNotGreaterThan(text, TEXT_CHARACTER_LIMIT, "text");
            PreConditions.notNullOrEmpty(buttons, "buttons");
            PreConditions.sizeNotGreaterThan(buttons, BUTTONS_LIMIT, "buttons");

            this.text = text;
            this.buttons = buttons;
        }

        public ButtonTemplate build() {
            return new ButtonTemplate(this);
        }
    }
}