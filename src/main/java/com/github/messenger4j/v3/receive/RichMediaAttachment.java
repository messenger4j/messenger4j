package com.github.messenger4j.v3.receive;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class RichMediaAttachment extends Attachment {

    private final Type type;
    private final String url;

    public RichMediaAttachment(@NonNull Type type, @NonNull String url) {
        this.type = type;
        this.url = url;
    }

    public Type type() {
        return type;
    }

    public String url() {
        return url;
    }

    @Override
    public boolean isRichMediaAttachment() {
        return true;
    }

    @Override
    public RichMediaAttachment asRichMediaAttachment() {
        return this;
    }

    public enum Type {
        IMAGE, AUDIO, VIDEO, FILE
    }
}
