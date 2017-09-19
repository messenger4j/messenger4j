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
public final class MessageSerializer implements JsonSerializer<Message> {

    @Override
    public JsonElement serialize(Message src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject messageObject = new JsonObject();
        messageObject.addProperty("text", src.text());
        if (src.richMedia() != null) {
            messageObject.add("attachment", context.serialize(src.richMedia()));
        }
        if (src.template() != null) {
            final JsonObject attachmentObject = new JsonObject();
            attachmentObject.addProperty("type", "template");
            attachmentObject.add("payload", context.serialize(src.template()));
            messageObject.add("attachment", attachmentObject);
        }
        messageObject.add("quick_replies", context.serialize(src.quickReplies()));
        messageObject.addProperty("metadata", src.metadata());
        return messageObject;
    }
}
