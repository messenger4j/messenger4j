package com.github.messenger4j.send;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public enum NotificationType {

    /**
     * Will emit a sound/vibration and a phone notification.
     */
    REGULAR,

    /**
     * Will just emit a phone notification.
     */
    SILENT_PUSH,

    /**
     * Will not emit either.
     */
    NO_PUSH
}