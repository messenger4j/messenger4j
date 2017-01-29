package com.github.messenger4j.setup;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
enum SettingType {

    @SerializedName("greeting")
    GREETING,

    @SerializedName("call_to_actions")
    CALL_TO_ACTIONS
}
