package com.github.messenger4j.v3;

import com.google.gson.annotations.SerializedName;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public enum MessengerSettingProperty {
    @SerializedName("get_started")
    START_BUTTON,
    PERSISTENT_MENU,
    GREETING
}
