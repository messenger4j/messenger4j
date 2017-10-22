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
        src.text().ifPresent(text -> messageObject.addProperty("text", text));
        src.richMedia().ifPresent(richMedia -> messageObject.add("attachment", context.serialize(richMedia)));
        src.template().ifPresent(template -> {
            final JsonObject attachmentObject = new JsonObject();
            attachmentObject.addProperty("type", "template");
            attachmentObject.add("payload", context.serialize(template));
            messageObject.add("attachment", attachmentObject);
        });
        src.quickReplies().ifPresent(quickReplies -> messageObject.add("quick_replies", context.serialize(quickReplies)));
        src.metadata().ifPresent(metadata -> messageObject.addProperty("metadata", metadata));
        return messageObject;
    }
}
