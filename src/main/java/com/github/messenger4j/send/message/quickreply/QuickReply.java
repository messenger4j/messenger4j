package com.github.messenger4j.send.message.quickreply;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class QuickReply {

    private final ContentType contentType;

    QuickReply(ContentType contentType) {
        this.contentType = contentType;
    }

    public ContentType contentType() {
        return contentType;
    }

    /**
     * @since 1.0.0
     */
    public enum ContentType {
        TEXT,
        LOCATION,
        USER_EMAIL,
        USER_PHONE_NUMBER
    }
}