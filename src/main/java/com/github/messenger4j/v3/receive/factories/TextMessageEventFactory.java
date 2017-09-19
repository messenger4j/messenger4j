package com.github.messenger4j.v3.receive.factories;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_IS_ECHO;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_QUICK_REPLY;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TEXT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsInstant;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.v3.receive.TextMessageEvent;
import com.google.gson.JsonObject;
import java.time.Instant;
import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class TextMessageEventFactory implements BaseEventFactory<TextMessageEvent> {

    @Override
    public boolean isResponsible(@NonNull JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_TEXT) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_QUICK_REPLY) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_IS_ECHO);
    }

    @Override
    public TextMessageEvent createEventFromJson(@NonNull JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP)
                .orElseThrow(() -> new IllegalArgumentException("JSON property '" + PROP_TIMESTAMP + "' expected"));
        final String messageId = getPropertyAsString(messagingEvent, PROP_MESSAGE, PROP_MID);
        final String text = getPropertyAsString(messagingEvent, PROP_MESSAGE, PROP_TEXT);
        return new TextMessageEvent(senderId, recipientId, timestamp, messageId, text);
    }
}
