package com.github.messenger4j.exception;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_CODE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ERROR;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_FB_TRACE_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsInt;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;

import com.google.gson.JsonObject;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class MessengerApiExceptionFactory {

    private MessengerApiExceptionFactory() {
    }

    public static MessengerApiException create(JsonObject jsonObject) {
        final String message = getPropertyAsString(jsonObject, PROP_ERROR, PROP_MESSAGE)
                .orElseThrow(IllegalArgumentException::new);
        final Optional<String> type = getPropertyAsString(jsonObject, PROP_ERROR, PROP_TYPE);
        final Optional<Integer> code = getPropertyAsInt(jsonObject, PROP_ERROR, PROP_CODE);
        final Optional<String> fbTraceId = getPropertyAsString(jsonObject, PROP_ERROR, PROP_FB_TRACE_ID);
        return new MessengerApiException(message, type, code, fbTraceId);
    }
}
