package com.github.messenger4j.send.buttons;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "ShareButton{} super=" + super.toString();
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