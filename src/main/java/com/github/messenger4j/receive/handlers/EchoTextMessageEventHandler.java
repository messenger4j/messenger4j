package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.EchoTextMessageEvent;

/**
 * An implementation of this interface is intended to handle the {@link EchoTextMessageEvent}.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see EventHandler
 * @see EchoTextMessageEvent
 */
public interface EchoTextMessageEventHandler extends EventHandler<EchoTextMessageEvent> {
}
