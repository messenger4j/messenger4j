package com.github.messenger4j.send.templates;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString
@EqualsAndHashCode
public abstract class Template {

    private final TemplateType templateType;

    Template(TemplateType templateType) {
        this.templateType = templateType;
    }

    public TemplateType templateType() {
        return templateType;
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum TemplateType {

        GENERIC,
        RECEIPT,
        BUTTON,
        LIST
    }
}