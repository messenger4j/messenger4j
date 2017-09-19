package com.github.messenger4j.v3;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class RichMedia {

    private final Type type;
    private final String url;
    private final String attachmentId;
    private final Boolean isReusable;

    public static RichMedia createByUrl(@NonNull Type type, @NonNull String url) {
        return new RichMedia(type, url, null, null);
    }

    public static RichMedia createByUrl(@NonNull Type type, @NonNull String url, boolean isReusable) {
        return new RichMedia(type, url, null, isReusable);
    }

    public static RichMedia createByAttachmentId(@NonNull Type type, @NonNull String attachmentId) {
        return new RichMedia(type, null, attachmentId, null);
    }

    private RichMedia(Type type, String url, String attachmentId, Boolean isReusable) {
        this.type = type;
        this.url = url;
        this.attachmentId = attachmentId;
        this.isReusable = isReusable;
    }

    public Type type() {
        return type;
    }

    public String url() {
        return url;
    }

    public String attachmentId() {
        return attachmentId;
    }

    public Boolean isReusable() {
        return isReusable;
    }

    public enum Type {
        IMAGE,
        AUDIO,
        VIDEO,
        FILE;
    }
}
