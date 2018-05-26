package com.github.messenger4j.webhook.factory;

import com.github.messenger4j.webhook.event.InstantGameEvent;
import com.google.gson.JsonObject;

import java.time.Instant;
import java.util.Optional;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.*;
import static com.github.messenger4j.internal.gson.GsonUtil.*;

/**
 * @author Marco Song
 * @since 1.0.0
 */
public class InstantGameEventFactory implements BaseEventFactory<InstantGameEvent> {
    @Override
    public boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_GAME_PLAY);

    }

    @Override
    public InstantGameEvent createEventFromJson(JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP)
                .orElseThrow(IllegalArgumentException::new);
        final String gameId = getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_GAME_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String playerId = getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_PLAYER_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String contextType = getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_CONTEXT_TYPE)
                .orElseThrow(IllegalArgumentException::new);
        final Optional<String> contextId = getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_CONTEXT_ID);
        final Optional<Integer> score = getPropertyAsInt(messagingEvent, PROP_GAME_PLAY, PROP_SCORE);
        final Optional<String> payload = getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_PAYLOAD);

        return new InstantGameEvent(senderId,recipientId,timestamp,gameId,playerId,contextType,contextId,score,payload);
    }
}
