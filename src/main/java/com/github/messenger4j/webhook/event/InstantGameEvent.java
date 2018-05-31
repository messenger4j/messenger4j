package com.github.messenger4j.webhook.event;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.time.Instant;
import java.util.Optional;

/**
 * @author Marco Song
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class InstantGameEvent extends BaseEvent{

    private final String gameId;
    private final String playerId;
    private final String contextType;
    private final Optional<String> contextId;
    private final Optional<Integer> score;
    private final Optional<String> payload;


    public InstantGameEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                            @NonNull String gameId, @NonNull String playerId, @NonNull String contextType,
                            @NonNull Optional<String> contextId, @NonNull Optional<Integer> score, @NonNull Optional<String> payload) {
        super(senderId, recipientId, timestamp);

        this.gameId = gameId;
        this.playerId = playerId;
        this.contextType = contextType;
        this.contextId = contextId;
        this.score = score;
        this.payload = payload;
    }

    public String gameId() {
        return gameId;
    }

    public String playerId() {
        return playerId;
    }

    public String contextType() {
        return contextType;
    }

    public Optional<String> contextId() {
        return contextId;
    }

    public Optional<Integer> score() {
        return score;
    }

    public Optional<String> payload() {
        return payload;
    }
}
