package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.EchoMessageEvent;

/**
 * An implementation of this interface is intended to handle the {@link EchoMessageEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandler
 * @see EchoMessageEvent
 */
public interface EchoMessageEventHandler extends EventHandler<EchoMessageEvent> {
}
