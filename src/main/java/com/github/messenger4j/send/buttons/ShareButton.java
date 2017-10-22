package com.github.messenger4j.send.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class ShareButton extends Button {

    private ShareButton() {
        super(ButtonType.ELEMENT_SHARE);
    }

    @Override
    public boolean isShareButton() {
        return true;
    }

    @Override
    public ShareButton asShareButton() {
        return this;
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private final ListBuilder listBuilder;

        Builder(ListBuilder listBuilder) {
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new ShareButton());
        }
    }
}