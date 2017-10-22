package com.github.messenger4j.send.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.7.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class LogOutButton extends Button {

    private LogOutButton() {
        super(ButtonType.ACCOUNT_UNLINK);
    }

    @Override
    public boolean isLogOutButton() {
        return true;
    }

    @Override
    public LogOutButton asLogOutButton() {
        return this;
    }

    /**
     * @author Max Grabenhorst
     * @since 0.7.0
     */
    public static final class Builder {

        private final ListBuilder listBuilder;

        Builder(ListBuilder listBuilder) {
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new LogOutButton());
        }
    }
}