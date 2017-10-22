package com.github.messenger4j.exceptions;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_CODE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ERROR;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_FB_TRACE_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsInt;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonObject;
import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class MessengerApiExceptionFactory {

    private MessengerApiExceptionFactory() {
    }

    public static MessengerApiException create(@NonNull JsonObject jsonObject) {
        final String message = getPropertyAsString(jsonObject, PROP_ERROR, PROP_MESSAGE);
        final String type = getPropertyAsString(jsonObject, PROP_ERROR, PROP_TYPE);
        final Integer code = getPropertyAsInt(jsonObject, PROP_ERROR, PROP_CODE);
        final String fbTraceId = getPropertyAsString(jsonObject, PROP_ERROR, PROP_FB_TRACE_ID);
        return new MessengerApiException(message, type, code, fbTraceId);
    }
}
