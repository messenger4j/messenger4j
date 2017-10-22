package com.github.messenger4j.v3;

import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
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
public final class MessagePayload {

    private final Recipient recipient;
    private final SenderAction senderAction;
    private final Message message;
    private final NotificationType notificationType;

    public static Builder newBuilder() {
        return new Builder();
    }

    private MessagePayload(@NonNull Recipient recipient, SenderAction senderAction, Message message, NotificationType notificationType) {
        this.recipient = recipient;
        this.senderAction = senderAction;
        this.message = message;
        this.notificationType = notificationType;
    }

    public Recipient recipient() {
        return recipient;
    }

    public Optional<SenderAction> senderAction() {
        return Optional.ofNullable(senderAction);
    }

    public Optional<Message> message() {
        return Optional.ofNullable(message);
    }

    public Optional<NotificationType> notificationType() {
        return Optional.ofNullable(notificationType);
    }

    public static final class Builder {

        private Recipient recipient;
        private SenderAction senderAction;
        private Message message;
        private NotificationType notificationType;

        public Builder recipient(@NonNull Recipient recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder recipientId(@NonNull String recipientId) {
            this.recipient = Recipient.createById(recipientId);
            return this;
        }

        public Builder senderAction(@NonNull SenderAction senderAction) {
            this.senderAction = senderAction;
            return this;
        }

        public Builder message(@NonNull Message message) {
            this.message = message;
            return this;
        }

        public Builder notificationType(@NonNull NotificationType notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public MessagePayload build() {
            if (message != null && senderAction != null) {
                throw new IllegalStateException("Either message or senderAction can be set - not both");
            }
            return new MessagePayload(recipient, senderAction, message, notificationType);
        }
    }
}
