package com.github.messenger4j.receive.callers;

import com.github.messenger4j.receive.events.EchoAttachmentMessageEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ATTACHMENTS;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_IS_ECHO;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

/**
 * <b>Internal</b> {@link EventHandlerCaller} responsible for the {@link EchoAttachmentMessageEvent}.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see EventHandlerCaller
 * @see EventHandler
 * @see FallbackEventHandler
 * @see EchoAttachmentMessageEvent
 */
public class EchoAttachmentMessageEventHandlerCaller extends EventHandlerCaller<EchoAttachmentMessageEvent> {
    public EchoAttachmentMessageEventHandlerCaller(EventHandler<EchoAttachmentMessageEvent> eventHandler,
                                         FallbackEventHandler fallbackEventHandler) {

        super(eventHandler, fallbackEventHandler);
    }

    @Override
    boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_IS_ECHO) && hasProperty(messagingEvent, PROP_MESSAGE, PROP_ATTACHMENTS);
    }

    @Override
    EchoAttachmentMessageEvent createEventFromJson(JsonObject messagingEvent) {
        return EchoAttachmentMessageEvent.fromJson(messagingEvent);
    }
}
