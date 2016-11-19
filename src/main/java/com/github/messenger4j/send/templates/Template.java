package com.github.messenger4j.send.templates;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public abstract class Template {

    @SerializedName("template_type")
    private final TemplateType type;

    Template(TemplateType type) {
        this.type = type;
    }

    public TemplateType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Template template = (Template) o;
        return type == template.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "Template{" +
                "type=" + type +
                '}';
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum TemplateType {

        @SerializedName("generic")
        GENERIC,

        @SerializedName("receipt")
        RECEIPT,

        @SerializedName("button")
        BUTTON
    }
}