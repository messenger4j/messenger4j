package com.github.messenger4j.send;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ATTACHMENT_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_MESSAGE_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_RECIPIENT_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;

import com.google.gson.JsonObject;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class MessageResponseFactory {

    private MessageResponseFactory() {
    }

    public static MessageResponse create(JsonObject jsonObject) {
        final Optional<String> recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT_ID);
        final Optional<String> messageId = getPropertyAsString(jsonObject, PROP_MESSAGE_ID);
        final Optional<String> attachmentId = getPropertyAsString(jsonObject, PROP_ATTACHMENT_ID);
        return new MessageResponse(recipientId, messageId, attachmentId);
    }
}
