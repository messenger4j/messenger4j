package com.github.messenger4j.internal.gson;

import com.github.messenger4j.send.message.Message;
import com.github.messenger4j.send.message.RichMediaMessage;
import com.github.messenger4j.send.message.TemplateMessage;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.message.richmedia.RichMediaAsset;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class MessageSerializer implements JsonSerializer<Message> {

    @Override
    public JsonElement serialize(Message message, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject messageObject = new JsonObject();

        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            messageObject.addProperty("text", textMessage.text());
        }
        if (message instanceof RichMediaMessage) {
            final RichMediaMessage richMediaMessage = (RichMediaMessage) message;
            messageObject.add("attachment", context.serialize(richMediaMessage.richMediaAsset(), RichMediaAsset.class));
        }
        if (message instanceof TemplateMessage) {
            final TemplateMessage templateMessage = (TemplateMessage) message;
            final JsonObject attachmentObject = new JsonObject();
            attachmentObject.addProperty("type", "template");
            attachmentObject.add("payload", context.serialize(templateMessage.template()));
            messageObject.add("attachment", attachmentObject);
        }

        message.quickReplies().ifPresent(quickReplies -> messageObject.add("quick_replies", context.serialize(quickReplies)));
        message.metadata().ifPresent(metadata -> messageObject.addProperty("metadata", metadata));

        return messageObject;
    }
}
