package com.github.messenger4j.receive.callers;

import com.github.messenger4j.receive.events.Event;
import com.github.messenger4j.receive.events.FallbackEvent;
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This <b>internal</b> abstract class is a base implementation for specific {@code EventHandlerCaller}s providing the
 * functionality to call the appropriate {@link EventHandler} for a specific event.
 *
 * <p>
 * At first an {@code EventHandlerCaller} tries to detect whether it is responsible for the given event which is
 * provided as JSON structure.<br> If the {@code EventHandlerCaller} is responsible for the given event it calls the
 * appropriate {@link EventHandler}.<br> In case no {@link EventHandler} was provided the
 * {@link FallbackEventHandler} is called.
 * </p>
 *
 * @param <E> the {@link Event} for which the {@code EventHandlerCaller} is responsible
 * @author Max Grabenhorst
 * @see Event
 * @see EventHandler
 * @see FallbackEventHandler
 * @since 0.6.0
 */
public abstract class EventHandlerCaller<E extends Event> {

    private final Logger logger = LoggerFactory.getLogger(EventHandlerCaller.class);

    private final EventHandler<E> eventHandler;
    private final FallbackEventHandler fallbackEventHandler;

    /**
     * Constructs the {@code EventHandlerCaller}.
     *
     * @param eventHandler         the {@link EventHandler} that is called if the {@code EventHandlerCaller} is
     *                             responsible for the given event
     * @param fallbackEventHandler the {@link FallbackEventHandler} that is called if no {@link EventHandler} is
     *                             provided (and the {@code EventHandlerCaller} is responsible for the given event)
     */
    EventHandlerCaller(EventHandler<E> eventHandler, FallbackEventHandler fallbackEventHandler) {
        this.eventHandler = eventHandler;
        this.fallbackEventHandler = fallbackEventHandler;
    }

    /**
     * Calls the appropriate {@link EventHandler} if the {@code EventHandlerCaller} is responsible for the given event.
     *
     * @param messagingEvent the event as JSON structure
     * @return {@code true} if the {@link EventHandler} has been called, otherwise {@code false}
     */
    public final boolean callHandlerIfResponsibleForEvent(JsonObject messagingEvent) {
        if (!isResponsible(messagingEvent)) {
            return false;
        }

        final E event = createEventFromJson(messagingEvent);

        if (this.eventHandler == null) {
            if (this.fallbackEventHandler != null) {
                logger.debug("{} cannot be processed, because the corresponding handler is not registered. " +
                                "Calling {} instead.",
                        event.getClass().getSimpleName(),
                        FallbackEventHandler.class.getSimpleName());
                this.fallbackEventHandler.handle(FallbackEvent.fromEvent(event));
            } else {
                logger.warn("{} cannot be processed, because the corresponding handler is not registered. " +
                                "Also the {} is not registered, hence the event will be discarded.",
                        event.getClass().getSimpleName(),
                        FallbackEventHandler.class.getSimpleName());
            }
        } else {
            this.eventHandler.handle(event);
        }

        return true;
    }

    /**
     * Checks if the {@code EventHandlerCaller} is responsible for the given event.
     *
     * @param messagingEvent the event as JSON structure
     * @return {@code true} if the {@code EventHandlerCaller} is responsible, otherwise {@code false}
     */
    abstract boolean isResponsible(JsonObject messagingEvent);

    /**
     * Creates an {@link Event} from the given event as JSON structure.
     *
     * @param messagingEvent the event as JSON structure
     * @return the created {@link Event}
     */
    abstract E createEventFromJson(JsonObject messagingEvent);
}