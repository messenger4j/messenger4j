package com.github.messenger4j.v3.receive;

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
    private final String payload;
    private final Referral referral;

    public PostbackEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                         @NonNull String title, String payload, Referral referral) {
        super(senderId, recipientId, timestamp);
        this.title = title;
        this.payload = payload;
        this.referral = referral;
    }

    public String title() {
        return title;
    }

    public Optional<String> payload() {
        return Optional.ofNullable(payload);
    }

    public Optional<Referral> referral() {
        return Optional.ofNullable(referral);
    }
}
