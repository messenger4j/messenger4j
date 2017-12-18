package com.github.messenger4j.webhook.event;

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
public final class AccountLinkingEvent extends BaseEvent {

    private final Status status;
    private final Optional<String> authorizationCode;

    public AccountLinkingEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                               @NonNull Status status, @NonNull Optional<String> authorizationCode) {
        super(senderId, recipientId, timestamp);
        this.status = status;
        this.authorizationCode = authorizationCode;
    }

    public Status status() {
        return status;
    }

    public Optional<String> authorizationCode() {
        return authorizationCode;
    }

    /**
     * @since 1.0.0
     */
    public enum Status {
        LINKED, UNLINKED
    }
}
