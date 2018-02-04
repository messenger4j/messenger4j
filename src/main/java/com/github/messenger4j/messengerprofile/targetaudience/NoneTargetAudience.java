package com.github.messenger4j.messengerprofile.targetaudience;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class NoneTargetAudience extends TargetAudience {

    public static NoneTargetAudience create() {
        return new NoneTargetAudience();
    }

    private NoneTargetAudience() {
        super(Type.NONE);
    }
}
