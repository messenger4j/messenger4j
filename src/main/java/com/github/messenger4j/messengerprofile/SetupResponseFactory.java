package com.github.messenger4j.messengerprofile;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_RESULT;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;

import com.google.gson.JsonObject;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class SetupResponseFactory {

    private SetupResponseFactory() {
    }

    public static SetupResponse create(JsonObject jsonObject) {
        final String result = getPropertyAsString(jsonObject, PROP_RESULT)
                .orElseThrow(IllegalArgumentException::new);
        return new SetupResponse(result);
    }
}
