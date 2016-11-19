package com.github.messenger4j.send;

import com.google.gson.annotations.SerializedName;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public enum SenderAction {

    /**
     * Mark last message as read.
     */
    @SerializedName("mark_seen")
    MARK_SEEN,

    /**
     * Turn typing indicators on.
     * Typing indicators are automatically turned off after 20 seconds.
     */
    @SerializedName("typing_on")
    TYPING_ON,

    /**
     * Turn typing indicators off.
     */
    @SerializedName("typing_off")
    TYPING_OFF
}