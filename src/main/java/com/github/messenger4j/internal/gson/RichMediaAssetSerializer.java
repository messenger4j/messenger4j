package com.github.messenger4j.internal.gson;

import com.github.messenger4j.send.message.richmedia.ReusableRichMediaAsset;
import com.github.messenger4j.send.message.richmedia.RichMediaAsset;
import com.github.messenger4j.send.message.richmedia.UrlRichMediaAsset;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class RichMediaAssetSerializer implements JsonSerializer<RichMediaAsset> {

    @Override
    public JsonElement serialize(RichMediaAsset richMediaAsset, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject payloadObject = new JsonObject();

        if (richMediaAsset instanceof UrlRichMediaAsset) {
            final UrlRichMediaAsset urlRichMediaAsset = (UrlRichMediaAsset) richMediaAsset;
            payloadObject.add("url", context.serialize(urlRichMediaAsset.url()));
            urlRichMediaAsset.isReusable().ifPresent(isReusable -> payloadObject.addProperty("is_reusable", isReusable));
        }
        if (richMediaAsset instanceof ReusableRichMediaAsset) {
            final ReusableRichMediaAsset reusableRichMediaAsset = (ReusableRichMediaAsset) richMediaAsset;
            payloadObject.addProperty("attachment_id", reusableRichMediaAsset.attachmentId());
        }

        final JsonObject attachmentObject = new JsonObject();
        attachmentObject.add("type", context.serialize(richMediaAsset.type()));
        attachmentObject.add("payload", payloadObject);

        return attachmentObject;
    }
}
