package com.github.messenger4j.webhook.event;

import com.github.messenger4j.webhook.event.common.PriorMessage;
import java.time.Instant;
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
public final class QuickReplyMessageEvent extends BaseEvent {

    private final String messageId;
    private final String text;
    private final String payload;
    private final Optional<PriorMessage> priorMessage;

    public QuickReplyMessageEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                                  @NonNull String messageId, @NonNull String text, @NonNull String payload,
                                  @NonNull Optional<PriorMessage> priorMessage) {
        super(senderId, recipientId, timestamp);
        this.messageId = messageId;
        this.text = text;
        this.payload = payload;
        this.priorMessage = priorMessage;
    }

    public String messageId() {
        return messageId;
    }

    public String text() {
        return text;
    }

    public String payload() {
        return payload;
    }

    public Optional<PriorMessage> priorMessage() {
        return priorMessage;
    }
}
