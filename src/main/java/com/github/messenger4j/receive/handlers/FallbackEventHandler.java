package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.FallbackEvent;

/**
 * An implementation of this interface is intended to handle the {@link FallbackEvent}.
 *
 * <p>
 * The {@code FallbackEventHandler} can be used to respond to the user even when the actual event
 * is not supported by this library or when the specific {@link EventHandler} for the actual event is not registered.
 * </p>
 *
 * @author Max Grabenhorst
 * @see EventHandler
 * @see FallbackEvent
 * @since 0.6.0
 */
public interface FallbackEventHandler extends EventHandler<FallbackEvent> {
}
