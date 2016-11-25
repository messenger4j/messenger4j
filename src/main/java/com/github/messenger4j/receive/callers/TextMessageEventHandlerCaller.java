package com.github.messenger4j.receive.callers;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_IS_ECHO;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_QUICK_REPLY;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TEXT;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;

/**
 * <b>Internal</b> {@link EventHandlerCaller} responsible for the {@link TextMessageEvent}.
 *
 * @author Max Grabenhorst
 * @see EventHandlerCaller
 * @see EventHandler
 * @see FallbackEventHandler
 * @see TextMessageEvent
 * @since 0.6.0
 */
public final class TextMessageEventHandlerCaller extends EventHandlerCaller<TextMessageEvent> {

    public TextMessageEventHandlerCaller(EventHandler<TextMessageEvent> eventHandler,
                                         FallbackEventHandler fallbackEventHandler) {

        super(eventHandler, fallbackEventHandler);
    }

    @Override
    boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_TEXT) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_QUICK_REPLY) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_IS_ECHO);
    }

    @Override
    TextMessageEvent createEventFromJson(JsonObject messagingEvent) {
        return TextMessageEvent.fromJson(messagingEvent);
    }
}
