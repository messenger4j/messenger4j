package com.github.messenger4j.receive.events;

import com.github.messenger4j.receive.attachments.Attachment;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public abstract class CommonAttachmentMessageEvent extends MessageEvent {
    private final List<Attachment> attachments;

    public CommonAttachmentMessageEvent(String senderId, String recipientId, Date timestamp, String mid,
                                  List<Attachment> attachments) {

        super(senderId, recipientId, timestamp, mid);
        this.attachments = attachments == null ? null : Collections.unmodifiableList(attachments);
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommonAttachmentMessageEvent that = (CommonAttachmentMessageEvent) o;
        return Objects.equals(attachments, that.attachments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), attachments);
    }

    @Override
    public String toString() {
        return "CommonAttachmentMessageEvent{" +
                "attachments=" + attachments +
                "} super=" + super.toString();
    }
}
