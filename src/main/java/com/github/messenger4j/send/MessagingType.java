package com.github.messenger4j.send;

import com.google.gson.annotations.SerializedName;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public enum MessagingType {

    @SerializedName("RESPONSE")
    RESPONSE,

    @SerializedName("UPDATE")
    UPDATE,

    @SerializedName("MESSAGE_TAG")
    MESSAGE_TAG
}
