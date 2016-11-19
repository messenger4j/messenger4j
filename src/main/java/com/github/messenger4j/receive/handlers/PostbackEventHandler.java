package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.PostbackEvent;

/**
 * An implementation of this interface is intended to handle the {@link PostbackEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandler
 * @see PostbackEvent
 */
public interface PostbackEventHandler extends EventHandler<PostbackEvent> {
}
