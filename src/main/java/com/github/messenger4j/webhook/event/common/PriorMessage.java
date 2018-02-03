package com.github.messenger4j.webhook.event.common;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class PriorMessage {

    private final String source;
    private final String identifier;

    public PriorMessage(@NonNull String source, @NonNull String identifier) {
        this.source = source;
        this.identifier = identifier;
    }

    public String source() {
        return source;
    }

    public String identifier() {
        return identifier;
    }
}
