package com.github.messenger4j.webhook.event.attachment;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class FallbackAttachment extends Attachment {

    private final String json;

    public FallbackAttachment(@NonNull String json) {
        this.json = json;
    }

    @Override
    public boolean isFallbackAttachment() {
        return true;
    }

    @Override
    public FallbackAttachment asFallbackAttachment() {
        return this;
    }

    public String json() {
        return json;
    }
}
