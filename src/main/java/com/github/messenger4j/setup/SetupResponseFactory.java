package com.github.messenger4j.setup;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RESULT;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonObject;
import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class SetupResponseFactory {

    private SetupResponseFactory() {
    }

    public static SetupResponse create(@NonNull JsonObject jsonObject) {
        final String result = getPropertyAsString(jsonObject, PROP_RESULT);
        return new SetupResponse(result);
    }
}
