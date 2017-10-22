package com.github.messenger4j.send.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class CallButton extends Button {

    private final String payload;
    private final String title;

    private CallButton(Builder builder) {
        super(ButtonType.PHONE_NUMBER);
        payload = builder.payload;
        title = builder.title;
    }

    @Override
    public boolean isCallButton() {
        return true;
    }

    @Override
    public CallButton asCallButton() {
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
            return this.listBuilder.addButtonToList(new CallButton(this));
        }
    }
}