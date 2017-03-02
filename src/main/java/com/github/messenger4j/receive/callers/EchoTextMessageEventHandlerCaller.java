package com.github.messenger4j.receive.callers;

import com.github.messenger4j.receive.events.EchoTextMessageEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;

import static com.github.messenger4j.internal.JsonHelper.Constants.*;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_QUICK_REPLY;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

/**
 * <b>Internal</b> {@link EventHandlerCaller} responsible for the {@link EchoTextMessageEvent}.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see EventHandlerCaller
 * @see EventHandler
 * @see FallbackEventHandler
 * @see EchoTextMessageEvent
 */
public class EchoTextMessageEventHandlerCaller extends EventHandlerCaller<EchoTextMessageEvent> {
    public EchoTextMessageEventHandlerCaller(EventHandler<EchoTextMessageEvent> eventHandler,
                                                   FallbackEventHandler fallbackEventHandler) {

        super(eventHandler, fallbackEventHandler);
    }

    @Override
    boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_IS_ECHO) &&
                hasProperty(messagingEvent, PROP_MESSAGE, PROP_TEXT) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_QUICK_REPLY);
    }

    @Override
    EchoTextMessageEvent createEventFromJson(JsonObject messagingEvent) {
        return EchoTextMessageEvent.fromJson(messagingEvent);
    }
}
