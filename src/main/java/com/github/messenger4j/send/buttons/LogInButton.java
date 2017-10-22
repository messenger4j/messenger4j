package com.github.messenger4j.send.buttons;

import java.net.URL;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.7.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class LogInButton extends Button {

    private final URL url;

    private LogInButton(Builder builder) {
        super(ButtonType.ACCOUNT_LINK);
        this.url = builder.url;
    }

    @Override
    public boolean isLogInButton() {
        return true;
    }

    @Override
    public LogInButton asLogInButton() {
        return this;
    }

    public URL url() {
        return url;
    }

    /**
     * @author Max Grabenhorst
     * @since 0.7.0
     */
    public static final class Builder {

        private final URL url;
        private final ListBuilder listBuilder;

        Builder(URL url, ListBuilder listBuilder) {
            this.url = url;
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new LogInButton(this));
        }
    }
}