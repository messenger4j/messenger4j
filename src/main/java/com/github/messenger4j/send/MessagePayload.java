package com.github.messenger4j.send;

import static java.util.Optional.empty;

import com.github.messenger4j.send.message.Message;
import com.github.messenger4j.send.recipient.IdRecipient;
import com.github.messenger4j.send.recipient.Recipient;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class MessagePayload extends Payload {

    private final Message message;

    public static MessagePayload create(@NonNull String recipientId, @NonNull Message message) {
        return create(IdRecipient.create(recipientId), message, empty());
    }

    public static MessagePayload create(@NonNull Recipient recipient, @NonNull Message message) {
        return create(recipient, message, empty());
    }

    public static MessagePayload create(@NonNull Recipient recipient, @NonNull Message message,
                                        @NonNull Optional<NotificationType> notificationType) {
        return new MessagePayload(recipient, message, notificationType);
    }

    private MessagePayload(Recipient recipient, Message message, Optional<NotificationType> notificationType) {
        super(recipient, notificationType);
        this.message = message;
    }

    public Message message() {
        return message;
    }
}
