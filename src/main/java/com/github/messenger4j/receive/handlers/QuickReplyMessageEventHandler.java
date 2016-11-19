package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.QuickReplyMessageEvent;

/**
 * An implementation of this interface is intended to handle the {@link QuickReplyMessageEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandler
 * @see QuickReplyMessageEvent
 */
public interface QuickReplyMessageEventHandler extends EventHandler<QuickReplyMessageEvent> {
}
