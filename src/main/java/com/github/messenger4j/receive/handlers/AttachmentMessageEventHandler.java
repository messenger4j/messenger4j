package com.github.messenger4j.receive.handlers;

import com.github.messenger4j.receive.events.AttachmentMessageEvent;

/**
 * An implementation of this interface is intended to handle the {@link AttachmentMessageEvent}.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see EventHandler
 * @see AttachmentMessageEvent
 */
public interface AttachmentMessageEventHandler extends EventHandler<AttachmentMessageEvent> {
}
