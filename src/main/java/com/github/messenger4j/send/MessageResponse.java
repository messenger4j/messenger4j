package com.github.messenger4j.send;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ATTACHMENT_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT_ID;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonObject;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class MessageResponse {

    private final String recipientId;
    private final String messageId;
    private final String attachmentId;

    public static MessageResponse fromJson(JsonObject jsonObject) {
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT_ID);
        final String messageId = getPropertyAsString(jsonObject, PROP_MESSAGE_ID);
        final String attachmentId = getPropertyAsString(jsonObject, PROP_ATTACHMENT_ID);
        return new MessageResponse(recipientId, messageId, attachmentId);
    }

    public MessageResponse(String recipientId, String messageId, String attachmentId) {
        this.recipientId = recipientId;
        this.messageId = messageId;
        this.attachmentId = attachmentId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageResponse that = (MessageResponse) o;
        return Objects.equals(recipientId, that.recipientId) &&
                Objects.equals(messageId, that.messageId) &&
                Objects.equals(attachmentId, that.attachmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientId, messageId, attachmentId);
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "recipientId='" + recipientId + '\'' +
                ", messageId='" + messageId + '\'' +
                ", attachmentId='" + attachmentId + '\'' +
                '}';
    }
}