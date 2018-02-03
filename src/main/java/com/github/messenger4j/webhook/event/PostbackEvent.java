package com.github.messenger4j.webhook.event;

import com.github.messenger4j.webhook.event.common.PriorMessage;
import com.github.messenger4j.webhook.event.common.Referral;
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
public final class PostbackEvent extends BaseEvent {

    private final String title;
    private final Optional<String> payload;
    private final Optional<Referral> referral;
    private final Optional<PriorMessage> priorMessage;

    public PostbackEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                         @NonNull String title, @NonNull Optional<String> payload, @NonNull Optional<Referral> referral,
                         @NonNull Optional<PriorMessage> priorMessage) {
        super(senderId, recipientId, timestamp);
        this.title = title;
        this.payload = payload;
        this.referral = referral;
        this.priorMessage = priorMessage;
    }

    public String title() {
        return title;
    }

    public Optional<String> payload() {
        return payload;
    }

    public Optional<Referral> referral() {
        return referral;
    }

    public Optional<PriorMessage> priorMessage() {
        return priorMessage;
    }
}
