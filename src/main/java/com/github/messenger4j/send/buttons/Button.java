package com.github.messenger4j.send.buttons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public abstract class Button {

    private final ButtonType type;

    public static ListBuilder newListBuilder() {
        return new ListBuilder();
    }

    Button(ButtonType type) {
        this.type = type;
    }

    public boolean isUrlButton() {
        return false;
    }

    public boolean isPostbackButton() {
        return false;
    }

    public boolean isCallButton() {
        return false;
    }

    public boolean isShareButton() {
        return false;
    }

    public boolean isLogInButton() {
        return false;
    }

    public boolean isLogOutButton() {
        return false;
    }

    public UrlButton asUrlButton() {
        throw new UnsupportedOperationException("not a UrlButton");
    }

    public PostbackButton asPostbackButton() {
        throw new UnsupportedOperationException("not a PostbackButton");
    }

    public CallButton asCallButton() {
        throw new UnsupportedOperationException("not a CallButton");
    }

    public ShareButton asShareButton() {
        throw new UnsupportedOperationException("not a ShareButton");
    }

    public LogInButton asLogInButton() {
        throw new UnsupportedOperationException("not a LogInButton");
    }

    public LogOutButton asLogOutButton() {
        throw new UnsupportedOperationException("not a LogOutButton");
    }

    public ButtonType getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        return type == button.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "Button{" +
                "type=" + type +
                '}';
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum ButtonType {
        WEB_URL,
        POSTBACK,
        PHONE_NUMBER,
        ELEMENT_SHARE,
        ACCOUNT_LINK,
        ACCOUNT_UNLINK
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class ListBuilder {

        private final List<Button> buttons;

        private ListBuilder() {
            this.buttons = new ArrayList<>(5);
        }

        ListBuilder addButtonToList(Button button) {
            this.buttons.add(button);
            return this;
        }

        public UrlButton.Builder addUrlButton(String title, String url) {
            return new UrlButton.Builder(title, url, this);
        }

        public PostbackButton.Builder addPostbackButton(String title, String payload) {
            return new PostbackButton.Builder(title, payload, this);
        }

        public CallButton.Builder addCallButton(String title, String payload) {
            return new CallButton.Builder(title, payload, this);
        }

        public ShareButton.Builder addShareButton() {
            return new ShareButton.Builder(this);
        }

        /**
         * @since 0.7.0
         */
        public LogInButton.Builder addLogInButton(String url) {
            return new LogInButton.Builder(url, this);
        }

        /**
         * @since 0.7.0
         */
        public LogOutButton.Builder addLogOutButton() {
            return new LogOutButton.Builder(this);
        }

        public List<Button> build() {
            return Collections.unmodifiableList(new ArrayList<>(this.buttons));
        }
    }
}