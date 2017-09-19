package com.github.messenger4j.v3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class RichMediaSerializer implements JsonSerializer<RichMedia> {

    @Override
    public JsonElement serialize(RichMedia src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject payloadObject = new JsonObject();
        payloadObject.addProperty("url", src.url());
        payloadObject.addProperty("attachment_id", src.attachmentId());
        payloadObject.addProperty("is_reusable", src.isReusable());

        final JsonObject attachmentObject = new JsonObject();
        attachmentObject.add("type", context.serialize(src.type()));
        attachmentObject.add("payload", payloadObject);

        return attachmentObject;
    }
}
