package com.github.messenger4j.messengerprofile.targetaudience;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class TargetAudience {

    private final Type audienceType;

    TargetAudience(Type audienceType) {
        this.audienceType = audienceType;
    }

    public Type audienceType() {
        return audienceType;
    }

    /**
     * @since 1.0.0
     */
    public enum Type {
        ALL,
        NONE,
        CUSTOM
    }
}
