package com.github.messenger4j.internal.gson;

import com.github.messenger4j.send.message.template.button.ShareButton;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class ShareButtonSerializer implements JsonSerializer<ShareButton> {

    @Override
    public JsonElement serialize(ShareButton shareButton, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject shareButtonObject = new JsonObject();
        shareButtonObject.add("type", context.serialize(shareButton.type()));
        shareButton.shareContents().ifPresent(genericTemplate -> {
            final JsonObject shareContentsObject = new JsonObject();
            final JsonObject attachmentObject = new JsonObject();
            attachmentObject.addProperty("type", "template");
            attachmentObject.add("payload", context.serialize(genericTemplate));
            shareContentsObject.add("attachment", attachmentObject);
            shareButtonObject.add("share_contents", shareContentsObject);
        });

        return shareButtonObject;
    }
}
