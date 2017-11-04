package com.github.messenger4j.send.message;

import static java.util.Optional.empty;

import com.github.messenger4j.send.message.quickreply.QuickReply;
import com.github.messenger4j.send.message.richmedia.RichMediaAsset;
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
public final class RichMediaMessage extends Message {

    private final RichMediaAsset richMediaAsset;

    public static RichMediaMessage create(@NonNull RichMediaAsset richMediaAsset) {
        return create(richMediaAsset, empty(), empty());
    }

    public static RichMediaMessage create(@NonNull RichMediaAsset richMediaAsset, @NonNull Optional<List<QuickReply>> quickReplies,
                                          @NonNull Optional<String> metadata) {
        return new RichMediaMessage(richMediaAsset, quickReplies, metadata);
    }

    private RichMediaMessage(RichMediaAsset richMediaAsset, Optional<List<QuickReply>> quickReplies, Optional<String> metadata) {
        super(quickReplies, metadata);
        this.richMediaAsset = richMediaAsset;
    }

    public RichMediaAsset richMediaAsset() {
        return richMediaAsset;
    }
}
