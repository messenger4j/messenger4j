package com.github.messenger4j.send.message;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.send.message.quickreply.QuickReply;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class Message {

    private final Optional<List<QuickReply>> quickReplies;
    private final Optional<String> metadata;

    Message(Optional<List<QuickReply>> quickReplies, Optional<String> metadata) {
        this.quickReplies = quickReplies.map(Lists::immutableList);
        this.metadata = metadata;
    }

    public Optional<List<QuickReply>> quickReplies() {
        return quickReplies;
    }

    public Optional<String> metadata() {
        return metadata;
    }
}
