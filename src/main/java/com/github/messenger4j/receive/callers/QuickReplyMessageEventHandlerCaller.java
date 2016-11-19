package com.github.messenger4j.receive.callers;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_QUICK_REPLY;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TEXT;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.receive.events.QuickReplyMessageEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;

/**
 * <b>Internal</b> {@link EventHandlerCaller} responsible for the {@link QuickReplyMessageEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandlerCaller
 * @see EventHandler
 * @see FallbackEventHandler
 * @see QuickReplyMessageEvent
 */
public final class QuickReplyMessageEventHandlerCaller extends EventHandlerCaller<QuickReplyMessageEvent> {

    public QuickReplyMessageEventHandlerCaller(EventHandler<QuickReplyMessageEvent> eventHandler,
                                               FallbackEventHandler fallbackEventHandler) {

        super(eventHandler, fallbackEventHandler);
    }

    @Override
    boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_TEXT)
                && hasProperty(messagingEvent, PROP_MESSAGE, PROP_QUICK_REPLY);
    }

    @Override
    QuickReplyMessageEvent createEventFromJson(JsonObject messagingEvent) {
        return QuickReplyMessageEvent.fromJson(messagingEvent);
    }
}
