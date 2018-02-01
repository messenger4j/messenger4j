package com.github.messenger4j.webhook.event;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.github.messenger4j.webhook.event.nlp.NlpEntity;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class TextMessageEvent extends BaseEvent {

    private final String messageId;
    private final String text;
    private final Optional<Map<String, Set<NlpEntity>>> nlpEntities;

    public TextMessageEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                            @NonNull String messageId, @NonNull String text, Optional<Map<String, Set<NlpEntity>>> nlpEntities) {
        super(senderId, recipientId, timestamp);
        this.messageId = messageId;
        this.text = text;
        this.nlpEntities = nlpEntities;
    }

    public String messageId() {
        return messageId;
    }

    public String text() {
        return text;
    }

    public Optional<Map<String, Set<NlpEntity>>> nlpEntities() {
        return nlpEntities;
    }
}
