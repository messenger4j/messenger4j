package com.github.messenger4j.v3;

import com.github.messenger4j.setup.CallToAction;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public class PersistentMenuConfiguration {

    public static PersistentMenuConfiguration create(boolean composerInputDisabled, CallToAction... callToActions) {
        return new PersistentMenuConfiguration();
    }

    public static PersistentMenuConfiguration create(CallToAction callToAction, CallToAction... callToActions) {
        return new PersistentMenuConfiguration();
    }
}
