package com.github.messenger4j.setup;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andrey on 23.01.17.
 */
public enum ThreadState {

    @SerializedName("new_thread")
    NEW_THREAD,

    @SerializedName("existing_thread")
    EXISTING_THREAD
}
