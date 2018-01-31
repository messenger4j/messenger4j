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

    private final Optional<String> recipientId;
    private final Optional<String> messageId;
    private final Optional<String> attachmentId;

    public MessageResponse(@NonNull Optional<String> recipientId, @NonNull Optional<String> messageId,
                           @NonNull Optional<String> attachmentId) {
        this.recipientId = recipientId;
        this.messageId = messageId;
        this.attachmentId = attachmentId;
    }

    public Optional<String> recipientId() {
        return recipientId;
    }

    public Optional<String> messageId() {
        return messageId;
    }

    public Optional<String> attachmentId() {
        return this.attachmentId;
    }
}
