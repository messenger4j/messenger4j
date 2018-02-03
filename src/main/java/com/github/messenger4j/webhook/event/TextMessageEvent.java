package com.github.messenger4j.webhook.event;

import com.github.messenger4j.webhook.event.common.PriorMessage;
import com.github.messenger4j.webhook.event.nlp.NLPEntity;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
    private final Optional<Map<String, Set<NLPEntity>>> nlpEntities;
    private final Optional<PriorMessage> priorMessage;

    public TextMessageEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                            @NonNull String messageId, @NonNull String text,
                            @NonNull Optional<Map<String, Set<NLPEntity>>> nlpEntities,
                            @NonNull Optional<PriorMessage> priorMessage) {
        super(senderId, recipientId, timestamp);
        this.messageId = messageId;
        this.text = text;
        this.nlpEntities = nlpEntities.map(entities -> Collections.unmodifiableMap(new HashMap<>(entities)));
        this.priorMessage = priorMessage;
    }

    public String messageId() {
        return messageId;
    }

    public String text() {
        return text;
    }

    public Optional<Map<String, Set<NLPEntity>>> nlpEntities() {
        return nlpEntities;
    }

    public Optional<PriorMessage> priorMessage() {
        return priorMessage;
    }
}
