package com.github.messenger4j.send;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public enum SenderAction {

    /**
     * Mark last message as read.
     */
    MARK_SEEN,

    /**
     * Turn typing indicators on.
     * Typing indicators are automatically turned off after 20 seconds.
     */
    TYPING_ON,

    /**
     * Turn typing indicators off.
     */
    TYPING_OFF
}