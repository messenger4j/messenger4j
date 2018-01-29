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

    private final MessagingType messagingType;
    private final Message message;
    private final Optional<NotificationType> notificationType;
    private final Optional<MessageTag> tag;

    public static MessagePayload create(@NonNull String recipientId, @NonNull MessagingType messagingType,
                                        @NonNull Message message) {
        return create(IdRecipient.create(recipientId), messagingType, message, empty(), empty());
    }

    public static MessagePayload create(@NonNull Recipient recipient, @NonNull MessagingType messagingType,
                                        @NonNull Message message) {
        return create(recipient, messagingType, message, empty(), empty());
    }

    public static MessagePayload create(@NonNull Recipient recipient,
                                        @NonNull MessagingType messagingType,
                                        @NonNull Message message,
                                        @NonNull Optional<NotificationType> notificationType,
                                        @NonNull Optional<MessageTag> tag) {
        return new MessagePayload(recipient, messagingType, message, notificationType, tag);
    }

    private MessagePayload(Recipient recipient, MessagingType messagingType, Message message,
                           Optional<NotificationType> notificationType, Optional<MessageTag> tag) {
        super(recipient);
        this.messagingType = messagingType;
        this.message = message;
        this.notificationType = notificationType;
        this.tag = tag;
    }

    public MessagingType messagingType() {
        return messagingType;
    }

    public Message message() {
        return message;
    }

    public Optional<NotificationType> notificationType() {
        return notificationType;
    }

    public Optional<MessageTag> tag() {
        return tag;
    }
}
