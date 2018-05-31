package com.github.messenger4j.webhook.factory;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsInstant;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.webhook.Event;
import com.github.messenger4j.webhook.event.FallbackEvent;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.List;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class EventFactory {

    private static final List<BaseEventFactory> FACTORIES = Lists.immutableList(
            new TextMessageEventFactory(),
            new AttachmentMessageEventFactory(),
            new QuickReplyMessageEventFactory(),
            new PostbackEventFactory(),
            new ReferralEventFactory(),
            new OptInEventFactory(),
            new MessageEchoEventFactory(),
            new MessageDeliveredEventFactory(),
            new MessageReadEventFactory(),
            new AccountLinkingEventFactory(),
            new InstantGameEventFactory()
    );

    private EventFactory() {
    }

    public static Event createEvent(JsonObject messagingEvent) {
        for (BaseEventFactory factory : FACTORIES) {
            if (factory.isResponsible(messagingEvent)) {
                return new Event(factory.createEventFromJson(messagingEvent));
            }
        }
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP).orElse(Instant.now());
        return new Event(new FallbackEvent(senderId, recipientId, timestamp));
    }
}
