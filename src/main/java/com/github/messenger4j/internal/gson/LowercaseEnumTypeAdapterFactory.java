package com.github.messenger4j.internal.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andriy Koretskyy
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class LowercaseEnumTypeAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

        @SuppressWarnings("unchecked") final Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }

        final TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);
        final Map<String, T> transformedToConstant = new HashMap<>();
        final Map<T, String> constantToTransformed = new HashMap<>();
        for (T constant : rawType.getEnumConstants()) {
            final String transformedConstant = transform(constant, delegateAdapter);
            transformedToConstant.put(transformedConstant, constant);
            constantToTransformed.put(constant, transformedConstant);
        }

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(constantToTransformed.get(value));
                }
            }

            @Override
            public T read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                } else {
                    return transformedToConstant.get(reader.nextString());
                }
            }
        };
    }

    /**
     * Transforms the given enum constant to lower case. If a {@code SerializedName} annotation is present, the default
     * adapter result is returned.
     *
     * @param enumConstant    the enumeration constant
     * @param delegateAdapter the default adapter of the given type
     * @return the transformed string representation of the constant
     */
    private <T> String transform(T enumConstant, TypeAdapter<T> delegateAdapter) {
        try {
            final String enumValue = ((Enum) enumConstant).name();
            final boolean hasSerializedNameAnnotation = enumConstant.getClass().getField(enumValue)
                    .isAnnotationPresent(SerializedName.class);
            return hasSerializedNameAnnotation ? delegateAdapter.toJsonTree(enumConstant).getAsString() :
                    enumValue.toLowerCase();
        } catch (NoSuchFieldException e) {
            // should never happen
            throw new RuntimeException(e);
        }
    }
}
