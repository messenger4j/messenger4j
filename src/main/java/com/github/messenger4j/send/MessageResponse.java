package com.github.messenger4j.send;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class MessageResponse {

    private final String recipientId;
    private final String messageId;
    private final Optional<String> attachmentId;

    public MessageResponse(@NonNull String recipientId, @NonNull String messageId,
                           @NonNull Optional<String> attachmentId) {
        this.recipientId = recipientId;
        this.messageId = messageId;
        this.attachmentId = attachmentId;
    }

    public String recipientId() {
        return recipientId;
    }

    public String messageId() {
        return messageId;
    }

    public Optional<String> attachmentId() {
        return this.attachmentId;
    }
}
