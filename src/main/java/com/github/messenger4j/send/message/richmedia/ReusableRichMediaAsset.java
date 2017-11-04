package com.github.messenger4j.send.message.richmedia;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ReusableRichMediaAsset extends RichMediaAsset {

    private final String attachmentId;

    public static ReusableRichMediaAsset create(@NonNull Type type, @NonNull String attachmentId) {
        return new ReusableRichMediaAsset(type, attachmentId);
    }

    private ReusableRichMediaAsset(Type type, String attachmentId) {
        super(type);
        this.attachmentId = attachmentId;
    }

    public String attachmentId() {
        return attachmentId;
    }
}
