package com.github.messenger4j.common;

import com.google.gson.annotations.SerializedName;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public enum WebviewHeightRatio {

    @SerializedName("compact")
    COMPACT,

    @SerializedName("tall")
    TALL,

    @SerializedName("full")
    FULL
}
