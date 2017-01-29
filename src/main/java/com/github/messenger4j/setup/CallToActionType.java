package com.github.messenger4j.setup;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
public enum CallToActionType {

    @SerializedName("web_url")
    URL,

    @SerializedName("postback")
    POSTBACK
}
