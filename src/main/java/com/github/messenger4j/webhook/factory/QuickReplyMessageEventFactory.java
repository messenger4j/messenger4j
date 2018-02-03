package com.github.messenger4j.webhook.factory;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_MID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_PAYLOAD;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_PRIOR_MESSAGE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_QUICK_REPLY;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TEXT;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsInstant;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsJsonObject;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;
import static com.github.messenger4j.internal.gson.GsonUtil.hasProperty;

import com.github.messenger4j.webhook.event.QuickReplyMessageEvent;
import com.github.messenger4j.webhook.event.common.PriorMessage;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class QuickReplyMessageEventFactory implements BaseEventFactory<QuickReplyMessageEvent> {

    @Override
    public boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_TEXT)
                && hasProperty(messagingEvent, PROP_MESSAGE, PROP_QUICK_REPLY);
    }

    @Override
    public QuickReplyMessageEvent createEventFromJson(JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP)
                .orElseThrow(IllegalArgumentException::new);
        final String messageId = getPropertyAsString(messagingEvent, PROP_MESSAGE, PROP_MID)
                .orElseThrow(IllegalArgumentException::new);
        final String text = getPropertyAsString(messagingEvent, PROP_MESSAGE, PROP_TEXT)
                .orElseThrow(IllegalArgumentException::new);
        final String payload = getPropertyAsString(messagingEvent, PROP_MESSAGE, PROP_QUICK_REPLY, PROP_PAYLOAD)
                .orElseThrow(IllegalArgumentException::new);
        final Optional<PriorMessage> priorMessage = getPropertyAsJsonObject(messagingEvent, PROP_PRIOR_MESSAGE)
                .map(this::getPriorMessageFromJsonObject);

        return new QuickReplyMessageEvent(senderId, recipientId, timestamp, messageId, text, payload, priorMessage);
    }
}
