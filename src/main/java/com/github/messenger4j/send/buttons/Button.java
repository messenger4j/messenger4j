package com.github.messenger4j.send.buttons;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString
@EqualsAndHashCode
public abstract class Button {

    private final Type type;

    Button(Type type) {
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

    public Type type() {
        return type;
    }

    /**
     * @author Max Grabenhorst
     * @since 1.0.0
     */
    public enum Type {
        WEB_URL,
        POSTBACK,
        PHONE_NUMBER,
        ELEMENT_SHARE,
        ACCOUNT_LINK,
        ACCOUNT_UNLINK
    }
}