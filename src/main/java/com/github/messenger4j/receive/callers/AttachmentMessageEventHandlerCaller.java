package com.github.messenger4j.receive.callers;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ATTACHMENTS;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_IS_ECHO;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MESSAGE;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.receive.events.AttachmentMessageEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;

/**
 * <b>Internal</b> {@link EventHandlerCaller} responsible for the {@link AttachmentMessageEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandlerCaller
 * @see EventHandler
 * @see FallbackEventHandler
 * @see AttachmentMessageEvent
 */
public final class AttachmentMessageEventHandlerCaller extends EventHandlerCaller<AttachmentMessageEvent> {

    public AttachmentMessageEventHandlerCaller(EventHandler<AttachmentMessageEvent> eventHandler,
                                               FallbackEventHandler fallbackEventHandler) {

        super(eventHandler, fallbackEventHandler);
    }

    @Override
    boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_MESSAGE, PROP_ATTACHMENTS) &&
                !hasProperty(messagingEvent, PROP_MESSAGE, PROP_IS_ECHO);
    }

    @Override
    AttachmentMessageEvent createEventFromJson(JsonObject messagingEvent) {
        return AttachmentMessageEvent.fromJson(messagingEvent);
    }
}
