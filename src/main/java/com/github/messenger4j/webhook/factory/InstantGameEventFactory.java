package com.github.messenger4j.webhook.factory;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_CONTEXT_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_CONTEXT_TYPE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_GAME_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_GAME_PLAY;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_PAYLOAD;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_PLAYER_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SCORE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsInstant;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsInt;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;
import static com.github.messenger4j.internal.gson.GsonUtil.hasProperty;

import com.github.messenger4j.webhook.event.InstantGameEvent;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.Optional;

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
    final String senderId =
        getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID)
            .orElseThrow(IllegalArgumentException::new);
    final String recipientId =
        getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID)
            .orElseThrow(IllegalArgumentException::new);
    final Instant timestamp =
        getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP)
            .orElseThrow(IllegalArgumentException::new);
    final String gameId =
        getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_GAME_ID)
            .orElseThrow(IllegalArgumentException::new);
    final String playerId =
        getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_PLAYER_ID)
            .orElseThrow(IllegalArgumentException::new);
    final String contextType =
        getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_CONTEXT_TYPE)
            .orElseThrow(IllegalArgumentException::new);
    final Optional<String> contextId =
        getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_CONTEXT_ID);
    final Optional<Integer> score = getPropertyAsInt(messagingEvent, PROP_GAME_PLAY, PROP_SCORE);
    final Optional<String> payload =
        getPropertyAsString(messagingEvent, PROP_GAME_PLAY, PROP_PAYLOAD);

    return new InstantGameEvent(
        senderId, recipientId, timestamp, gameId, playerId, contextType, contextId, score, payload);
  }
}
