package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.TextMessageEvent;

/**
 * An implementation of this interface is intended to handle the {@link TextMessageEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandler
 * @see TextMessageEvent
 */
public interface TextMessageEventHandler extends EventHandler<TextMessageEvent> {
}
