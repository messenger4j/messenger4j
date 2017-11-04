package com.github.messenger4j.send.message.template.button;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ShareButton extends Button {

    public static ShareButton create() {
        return new ShareButton();
    }

    private ShareButton() {
        super(Type.ELEMENT_SHARE);
    }

    @Override
    public boolean isShareButton() {
        return true;
    }

    @Override
    public ShareButton asShareButton() {
        return this;
    }
}
