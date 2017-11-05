package com.github.messenger4j.webhook.event.attachment;

import java.net.URL;
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
    private final URL url;

    public RichMediaAttachment(@NonNull Type type, @NonNull URL url) {
        this.type = type;
        this.url = url;
    }

    @Override
    public boolean isRichMediaAttachment() {
        return true;
    }

    @Override
    public RichMediaAttachment asRichMediaAttachment() {
        return this;
    }

    public Type type() {
        return type;
    }

    public URL url() {
        return url;
    }

    /**
     * @since 1.0.0
     */
    public enum Type {
        IMAGE,
        AUDIO,
        VIDEO,
        FILE;
    }
}
