package com.github.messenger4j.send.message.template.button;

import static java.util.Optional.empty;

import com.github.messenger4j.send.message.template.GenericTemplate;
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
public final class ShareButton extends Button {

    private final Optional<GenericTemplate> shareContents;

    public static ShareButton create() {
        return create(empty());
    }

    public static ShareButton create(@NonNull Optional<GenericTemplate> shareContents) {
        return new ShareButton(shareContents);
    }

    private ShareButton(Optional<GenericTemplate> shareContents) {
        super(Type.ELEMENT_SHARE);
        this.shareContents = shareContents;
    }

    public Optional<GenericTemplate> shareContents() {
        return shareContents;
    }
}
