package com.github.messenger4j.send.message.template;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.send.message.template.button.Button;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ButtonTemplate extends Template {

    private final String text;
    private final List<Button> buttons;

    public static ButtonTemplate create(@NonNull String text, @NonNull List<Button> buttons) {
        return new ButtonTemplate(text, buttons);
    }

    private ButtonTemplate(String text, List<Button> buttons) {
        super(Type.BUTTON);
        this.text = text;
        this.buttons = Lists.immutableList(buttons);
    }

    public String text() {
        return text;
    }

    public List<Button> buttons() {
        return buttons;
    }
}
