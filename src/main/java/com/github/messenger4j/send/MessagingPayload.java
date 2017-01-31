package com.github.messenger4j.send;

import com.github.messenger4j.internal.PreConditions;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
final class MessagingPayload {

    private final Recipient recipient;
    private final NotificationType notificationType;
    private final Message message;
    private final SenderAction senderAction;

    static Builder newBuilder(Recipient recipient) {
        return new Builder(recipient);
    }

    private MessagingPayload(Builder builder) {
        recipient = builder.recipient;
        notificationType = builder.notificationType;
        message = builder.message;
        senderAction = builder.senderAction;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public Message getMessage() {
        return message;
    }

    public SenderAction getSenderAction() {
        return senderAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessagingPayload that = (MessagingPayload) o;
        return Objects.equals(recipient, that.recipient) &&
                notificationType == that.notificationType &&
                Objects.equals(message, that.message) &&
                senderAction == that.senderAction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, notificationType, message, senderAction);
    }

    @Override
    public String toString() {
        return "MessagingPayload{" +
                "recipient=" + recipient +
                ", notificationType=" + notificationType +
                ", message=" + message +
                ", senderAction=" + senderAction +
                '}';
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    static final class Builder {
        private final Recipient recipient;
        private NotificationType notificationType;
        private Message message;
        private SenderAction senderAction;

        private Builder(Recipient recipient) {
            PreConditions.notNull(recipient, "recipient");
            this.recipient = recipient;
        }

        public Builder notificationType(NotificationType notificationType) {
            PreConditions.notNull(notificationType, "notificationType");
            this.notificationType = notificationType;
            return this;
        }

        public Message.Builder addMessage() {
            return new Message.Builder(this);
        }

        Builder message(Message message) {
            this.message = message;
            return this;
        }

        public Builder senderAction(SenderAction senderAction) {
            PreConditions.notNull(senderAction, "senderAction");
            this.senderAction = senderAction;
            return this;
        }

        public MessagingPayload build() {
            return new MessagingPayload(this);
        }
    }
}