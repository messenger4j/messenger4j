package com.github.messenger4j.webhook.event.attachment;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public abstract class Attachment {

    Attachment() {
    }

    public boolean isFallbackAttachment() {
        return false;
    }

    public FallbackAttachment asFallbackAttachment() {
        throw new UnsupportedOperationException("not a FallbackAttachment");
    }

    public boolean isLocationAttachment() {
        return false;
    }

    public LocationAttachment asLocationAttachment() {
        throw new UnsupportedOperationException("not a LocationAttachment");
    }

    public boolean isRichMediaAttachment() {
        return false;
    }

    public RichMediaAttachment asRichMediaAttachment() {
        throw new UnsupportedOperationException("not a RichMediaAttachment");
    }
}
