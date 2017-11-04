package com.github.messenger4j.internal.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class OptionalInstantToSecondsStringSerializer implements JsonSerializer<Optional<Instant>> {

    @Override
    public JsonElement serialize(Optional<Instant> src, Type typeOfSrc, JsonSerializationContext context) {
        return src.map(instant -> new JsonPrimitive(Long.toString(instant.getEpochSecond()))).orElse(null);
    }
}
