package com.github.messenger4j.internal.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class OptionalSerializer implements JsonSerializer<Optional> {

    @Override
    public JsonElement serialize(Optional src, Type typeOfSrc, JsonSerializationContext context) {
        if (!src.isPresent()) {
            return null;
        }
        return context.serialize(src.get());
    }
}
