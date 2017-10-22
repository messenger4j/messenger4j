package com.github.messenger4j.send.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PostbackButton extends Button {

    private final String payload;
    private final String title;

    private PostbackButton(Builder builder) {
        super(ButtonType.POSTBACK);
        payload = builder.payload;
        title = builder.title;
    }

    @Override
    public boolean isPostbackButton() {
        return true;
    }

    @Override
    public PostbackButton asPostbackButton() {
        return this;
    }

    public String payload() {
        return payload;
    }

    public String title() {
        return title;
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private final String title;
        private final String payload;
        private final ListBuilder listBuilder;

        Builder(String title, String payload, ListBuilder listBuilder) {
            this.title = title;
            this.payload = payload;
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new PostbackButton(this));
        }
    }
}