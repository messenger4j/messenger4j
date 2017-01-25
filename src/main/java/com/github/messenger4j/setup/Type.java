package com.github.messenger4j.setup;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 23.01.17.
 */
public enum Type {

    @SerializedName("web_url")
    URL,

    @SerializedName("postback")
    POSTBACK

}
