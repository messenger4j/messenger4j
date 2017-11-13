package com.github.messenger4j.send.message.template;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class Template {

    private final Type templateType;

    Template(Type templateType) {
        this.templateType = templateType;
    }

    public Type templateType() {
        return templateType;
    }

    /**
     * @since 1.0.0
     */
    public enum Type {
        GENERIC,
        RECEIPT,
        BUTTON,
        LIST,
        OPEN_GRAPH
    }
}
