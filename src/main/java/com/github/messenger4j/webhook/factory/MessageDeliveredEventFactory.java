package com.github.messenger4j.webhook.factory;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_DELIVERY;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_MIDS;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_WATERMARK;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsInstant;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsJsonArray;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;
import static com.github.messenger4j.internal.gson.GsonUtil.hasProperty;

import com.github.messenger4j.webhook.event.MessageDeliveredEvent;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class MessageDeliveredEventFactory implements BaseEventFactory<MessageDeliveredEvent> {

    @Override
    public boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_DELIVERY);
    }

    @Override
    public MessageDeliveredEvent createEventFromJson(JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP).orElse(Instant.now());
        final Instant watermark = getPropertyAsInstant(messagingEvent, PROP_DELIVERY, PROP_WATERMARK)
                .orElseThrow(IllegalArgumentException::new);
        final Optional<List<String>> messageIds = getPropertyAsJsonArray(messagingEvent, PROP_DELIVERY, PROP_MIDS)
                .map(this::getMessageIdsFromJsonArray);

        return new MessageDeliveredEvent(senderId, recipientId, timestamp, watermark, messageIds);
    }

    private List<String> getMessageIdsFromJsonArray(JsonArray jsonArray) {
        final List<String> messageIdList = new ArrayList<>(jsonArray.size());
        for (JsonElement messageIdJsonElement : jsonArray) {
            messageIdList.add(messageIdJsonElement.getAsString());
        }
        return messageIdList;
    }
}
