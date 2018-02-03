package com.github.messenger4j.webhook.event;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.webhook.event.attachment.Attachment;
import com.github.messenger4j.webhook.event.common.PriorMessage;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class AttachmentMessageEvent extends BaseEvent {

    private final String messageId;
    private final List<Attachment> attachments;
    private final Optional<PriorMessage> priorMessage;

    public AttachmentMessageEvent(@NonNull String senderId, @NonNull String recipientId, @NonNull Instant timestamp,
                                  @NonNull String messageId, @NonNull List<Attachment> attachments,
                                  @NonNull Optional<PriorMessage> priorMessage) {
        super(senderId, recipientId, timestamp);
        this.messageId = messageId;
        this.attachments = Lists.immutableList(attachments);
        this.priorMessage = priorMessage;
    }

    public String messageId() {
        return messageId;
    }

    public List<Attachment> attachments() {
        return attachments;
    }

    public Optional<PriorMessage> priorMessage() {
        return priorMessage;
    }
}
