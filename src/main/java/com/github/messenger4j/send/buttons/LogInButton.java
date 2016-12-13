package com.github.messenger4j.send.buttons;

import com.github.messenger4j.internal.PreConditions;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.7.0
 */
public final class LogInButton extends Button {

    private final String url;

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

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LogInButton that = (LogInButton) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url);
    }

    @Override
    public String toString() {
        return "LogInButton{" +
                "url='" + url + '\'' +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.7.0
     */
    public static final class Builder {

        private final String url;
        private final ListBuilder listBuilder;

        Builder(String url, ListBuilder listBuilder) {
            PreConditions.notNullOrBlank(url, "url");
            PreConditions.startsWith(url, "https", "url");

            this.url = url;
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new LogInButton(this));
        }
    }
}