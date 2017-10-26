package com.github.messenger4j.v3.receive;

import com.github.messenger4j.internal.Lists;
import java.time.Instant;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AttachmentMessageEvent extends MessageEvent {

    private final List<Attachment> attachments;

    public AttachmentMessageEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                                  @NonNull String messageId, @NonNull List<Attachment> attachments) {
        super(senderId, recipientId, timestamp, messageId);
        this.attachments = Lists.immutableList(attachments);
    }

    public List<Attachment> attachments() {
        return attachments;
    }
}
