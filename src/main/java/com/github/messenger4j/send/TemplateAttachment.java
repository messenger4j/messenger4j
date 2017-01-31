package com.github.messenger4j.send;

import com.github.messenger4j.send.templates.Template;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
final class TemplateAttachment extends Message.Attachment {

    private final Type type;
    private final Template payload;

    TemplateAttachment(Template payload) {
        this.type = Type.TEMPLATE;
        this.payload = payload;
    }

    public Template getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateAttachment that = (TemplateAttachment) o;
        return type == that.type &&
                Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, payload);
    }

    @Override
    public String toString() {
        return "TemplateAttachment{" +
                "type=" + type +
                ", payload=" + payload +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    private enum Type {
        TEMPLATE
    }
}