package com.github.messenger4j.setup;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
enum ThreadState {

    @SerializedName("new_thread")
    NEW_THREAD,

    @SerializedName("existing_thread")
    EXISTING_THREAD
}
