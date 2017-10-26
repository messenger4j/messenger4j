package com.github.messenger4j.v3.receive.factories;

import com.github.messenger4j.v3.receive.BaseEvent;
import com.google.gson.JsonObject;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public interface BaseEventFactory<E extends BaseEvent> {

    boolean isResponsible(JsonObject messagingEvent);

    E createEventFromJson(JsonObject messagingEvent);
}
