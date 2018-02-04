package com.github.messenger4j.send.message.template.button;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class Button {

    private final Type type;

    Button(Type type) {
        this.type = type;
    }

    public Type type() {
        return type;
    }

    /**
     * @since 1.0.0
     */
    public enum Type {
        WEB_URL,
        POSTBACK,
        PHONE_NUMBER,
        ELEMENT_SHARE,
        ACCOUNT_LINK,
        ACCOUNT_UNLINK
    }
}