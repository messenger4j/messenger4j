package com.github.messenger4j.internal.gson;

import com.github.messenger4j.common.WebviewShareButtonState;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class WebviewShareButtonStateSerializer implements JsonSerializer<WebviewShareButtonState> {

    @Override
    public JsonElement serialize(WebviewShareButtonState webviewShareButtonState,
                                 Type typeOfSrc, JsonSerializationContext context) {
        if (webviewShareButtonState != WebviewShareButtonState.HIDE) {
            return null;
        }
        return context.serialize(WebviewShareButtonState.HIDE.name().toLowerCase());
    }
}
