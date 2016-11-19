package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.AccountLinkingEvent;

/**
 * An implementation of this interface is intended to handle the {@link AccountLinkingEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandler
 * @see AccountLinkingEvent
 */
public interface AccountLinkingEventHandler extends EventHandler<AccountLinkingEvent> {
}
