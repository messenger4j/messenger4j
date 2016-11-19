package com.github.messenger4j.receive.callers;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ACCOUNT_LINKING;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.receive.events.AccountLinkingEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;

/**
 * <b>Internal</b> {@link EventHandlerCaller} responsible for the {@link AccountLinkingEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandlerCaller
 * @see EventHandler
 * @see FallbackEventHandler
 * @see AccountLinkingEvent
 */
public final class AccountLinkingEventHandlerCaller extends EventHandlerCaller<AccountLinkingEvent> {

    public AccountLinkingEventHandlerCaller(EventHandler<AccountLinkingEvent> eventHandler,
                                            FallbackEventHandler fallbackEventHandler) {

        super(eventHandler, fallbackEventHandler);
    }

    @Override
    boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_ACCOUNT_LINKING);
    }

    @Override
    AccountLinkingEvent createEventFromJson(JsonObject messagingEvent) {
        return AccountLinkingEvent.fromJson(messagingEvent);
    }
}
