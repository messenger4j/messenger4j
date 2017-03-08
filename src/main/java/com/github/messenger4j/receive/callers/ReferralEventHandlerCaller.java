package com.github.messenger4j.receive.callers;

import static com.github.messenger4j.internal.JsonHelper.hasProperty;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_REFERRAL;

import com.github.messenger4j.receive.events.ReferralEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;

/**
 * <b>Internal</b> {@link EventHandlerCaller} responsible for the {@link ReferralEvent}.
 *
 * @author uzrbin
 * @see EventHandlerCaller
 * @see EventHandler
 * @see FallbackEventHandler
 * @see ReferralEvent
 */
public final class ReferralEventHandlerCaller extends EventHandlerCaller<ReferralEvent> {

    public ReferralEventHandlerCaller(EventHandler<ReferralEvent> eventHandler,
                                      FallbackEventHandler fallbackEventHandler) {

        super(eventHandler, fallbackEventHandler);
    }

    @Override
    boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_REFERRAL);
    }

    @Override
    ReferralEvent createEventFromJson(JsonObject messagingEvent) {
        return ReferralEvent.fromJson(messagingEvent);
    }
}
