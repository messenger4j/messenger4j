package com.github.messenger4j.webhook.event;

import com.github.messenger4j.webhook.event.common.Referral;
import java.time.Instant;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ReferralEvent extends BaseEvent {

    private final Referral referral;

    public ReferralEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                         @NonNull Referral referral) {
        super(senderId, recipientId, timestamp);
        this.referral = referral;
    }

    public Referral referral() {
        return referral;
    }
}
