package com.github.messenger4j.exceptions;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_CODE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ERROR;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_FB_TRACE_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsInt;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonObject;

/**
 * Thrown to indicate that a Messenger Platform Send API request failed.
 *
 * <p>
 * Messenger Platform errors are grouped by code, with a different message depending on the error condition.<br>
 * For a list of common errors that you should consider handling at run-time refer to: <br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/send-api-reference#errors">
 * https://developers.facebook.com/docs/messenger-platform/send-api-reference#errors
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class MessengerApiException extends Exception {

    private final String type;
    private final Integer code;
    private final String fbTraceId;

    public static MessengerApiException fromJson(JsonObject jsonObject) {
        final String message = getPropertyAsString(jsonObject, PROP_ERROR, PROP_MESSAGE);
        final String type = getPropertyAsString(jsonObject, PROP_ERROR, PROP_TYPE);
        final Integer code = getPropertyAsInt(jsonObject, PROP_ERROR, PROP_CODE);
        final String fbTraceId = getPropertyAsString(jsonObject, PROP_ERROR, PROP_FB_TRACE_ID);
        return new MessengerApiException(message, type, code, fbTraceId);
    }

    private MessengerApiException(String message, String type, Integer code, String fbTraceId) {
        super(message);
        this.type = type;
        this.code = code;
        this.fbTraceId = fbTraceId;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public String getFbTraceId() {
        return fbTraceId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MessengerApiException{");
        sb.append("message='").append(getMessage()).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append(", code=").append(code);
        sb.append(", fbTraceId='").append(fbTraceId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}