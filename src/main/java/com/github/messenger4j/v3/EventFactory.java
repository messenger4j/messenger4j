package com.github.messenger4j.v3;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsInstant;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.github.messenger4j.v3.receive.BaseEvent;
import com.github.messenger4j.v3.receive.Event;
import com.github.messenger4j.v3.receive.factories.AccountLinkingEventFactory;
import com.github.messenger4j.v3.receive.factories.AttachmentMessageEventFactory;
import com.github.messenger4j.v3.receive.factories.BaseEventFactory;
import com.github.messenger4j.v3.receive.factories.MessageDeliveredEventFactory;
import com.github.messenger4j.v3.receive.factories.MessageEchoEventFactory;
import com.github.messenger4j.v3.receive.factories.MessageReadEventFactory;
import com.github.messenger4j.v3.receive.factories.OptInEventFactory;
import com.github.messenger4j.v3.receive.factories.PostbackEventFactory;
import com.github.messenger4j.v3.receive.factories.QuickReplyMessageEventFactory;
import com.github.messenger4j.v3.receive.factories.TextMessageEventFactory;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class EventFactory {

    private static final List<BaseEventFactory> FACTORIES = Collections.unmodifiableList(Arrays.asList(
            new TextMessageEventFactory(),
            new AttachmentMessageEventFactory(),
            new QuickReplyMessageEventFactory(),
            new PostbackEventFactory(),
            new MessageEchoEventFactory(),
            new MessageDeliveredEventFactory(),
            new MessageReadEventFactory(),
            new AccountLinkingEventFactory(),
            new OptInEventFactory()
    ));

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
        return new Event(new BaseEvent(senderId, recipientId, timestamp) {
        });
    }
}
