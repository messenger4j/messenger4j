package com.github.messenger4j.send.message;

import static java.util.Optional.empty;

import com.github.messenger4j.send.message.quickreply.QuickReply;
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
public final class TextMessage extends Message {

    private final String text;

    public static TextMessage create(@NonNull String text) {
        return create(text, empty(), empty());
    }

    public static TextMessage create(@NonNull String text, @NonNull Optional<List<QuickReply>> quickReplies,
                                     @NonNull Optional<String> metadata) {
        return new TextMessage(text, quickReplies, metadata);
    }

    private TextMessage(String text, Optional<List<QuickReply>> quickReplies, Optional<String> metadata) {
        super(quickReplies, metadata);
        this.text = text;
    }

    public String text() {
        return text;
    }
}
