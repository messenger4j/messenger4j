package com.github.messenger4j.send.message.richmedia;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class RichMediaAsset {

    private final Type type;

    RichMediaAsset(Type type) {
        this.type = type;
    }

    public Type type() {
        return type;
    }

    /**
     * @since 1.0.0
     */
    public enum Type {
        IMAGE,
        AUDIO,
        VIDEO,
        FILE
    }
}
