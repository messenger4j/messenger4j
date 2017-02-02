package com.github.messenger4j.common;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
final class GsonFactory {

    private GsonFactory() {
    }

    static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory())
                .registerTypeAdapter(Float.class, new FloatSerializer())
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.8.0
     */
    private static final class FloatSerializer implements JsonSerializer<Float> {
        @Override
        public JsonElement serialize(Float floatValue, Type type, JsonSerializationContext jsonSerializationContext) {
            if (floatValue.isNaN() || floatValue.isInfinite()) {
                return null;
            }
            return new JsonPrimitive(new BigDecimal(floatValue).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
    }

    /**
     * @author Andriy Koretskyy
     * @author Max Grabenhorst
     * @since 0.8.0
     */
    private static final class LowercaseEnumTypeAdapterFactory implements TypeAdapterFactory {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            @SuppressWarnings("unchecked")
            final Class<T> rawType = (Class<T>) type.getRawType();
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
         * Transforms the given enum constant to lower case. If a {@code SerializedName} annotation is present,
         * the default adapter result is returned.
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
}
