package com.github.messenger4j.send;

import com.github.messenger4j.internal.PreConditions;
import com.github.messenger4j.send.templates.Template;
import java.util.List;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
final class Message {

    private final String text;
    private final Attachment attachment;
    private final List<QuickReply> quickReplies;
    private final String metadata;

    private Message(Builder builder) {
        text = builder.text;
        attachment = builder.attachment;
        quickReplies = builder.quickReplies;
        metadata = builder.metadata;
    }

    public String getText() {
        return text;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public List<QuickReply> getQuickReplies() {
        return quickReplies;
    }

    public String getMetadata() {
        return metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(text, message.text) &&
                Objects.equals(attachment, message.attachment) &&
                Objects.equals(quickReplies, message.quickReplies) &&
                Objects.equals(metadata, message.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, attachment, quickReplies, metadata);
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", attachment=" + attachment +
                ", quickReplies=" + quickReplies +
                ", metadata='" + metadata + '\'' +
                '}';
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    abstract static class Attachment {

        @Override
        public String toString() {
            return "Attachment{}";
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    static final class Builder {

        private static final int TEXT_CHARACTER_LIMIT = 320;
        private static final int QUICK_REPLIES_LIMIT = 10;
        private static final int METADATA_CHARACTER_LIMIT = 1000;

        private String text;
        private Attachment attachment;
        private List<QuickReply> quickReplies;
        private String metadata;
        private final MessagingPayload.Builder messagingPayloadBuilder;

        Builder(MessagingPayload.Builder messagingPayloadBuilder) {
            this.messagingPayloadBuilder = messagingPayloadBuilder;
        }

        public Builder text(String text) {
            PreConditions.notNullOrBlank(text, "text");
            PreConditions.lengthNotGreaterThan(text, TEXT_CHARACTER_LIMIT, "text");
            this.text = text;
            return this;
        }

        public Builder binaryAttachment(BinaryAttachment binaryAttachment) {
            PreConditions.notNull(binaryAttachment, "binaryAttachment");
            this.attachment = binaryAttachment;
            return this;
        }

        public Builder template(Template template) {
            PreConditions.notNull(template, "template");
            this.attachment = new TemplateAttachment(template);
            return this;
        }

        public Builder quickReplies(List<QuickReply> quickReplies) {
            PreConditions.notNullOrEmpty(quickReplies, "quickReplies");
            PreConditions.sizeNotGreaterThan(quickReplies, QUICK_REPLIES_LIMIT, "quickReplies");
            this.quickReplies = quickReplies;
            return this;
        }

        public Builder metadata(String metadata) {
            PreConditions.notNullOrBlank(metadata, "metadata");
            PreConditions.lengthNotGreaterThan(metadata, METADATA_CHARACTER_LIMIT, "metadata");
            this.metadata = metadata;
            return this;
        }

        public MessagingPayload.Builder done() {
            return this.messagingPayloadBuilder.message(new Message(this));
        }
    }
}