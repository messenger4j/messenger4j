package com.github.messenger4j.webhook.factory;

import com.github.messenger4j.webhook.event.BaseEvent;
import com.google.gson.JsonObject;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
interface BaseEventFactory<E extends BaseEvent> {

    boolean isResponsible(JsonObject messagingEvent);

    E createEventFromJson(JsonObject messagingEvent);
}
