package com.github.messenger4j.send.message.template.button;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PostbackButton extends Button {

    private final String title;
    private final String payload;

    public static PostbackButton create(@NonNull String title, @NonNull String payload) {
        return new PostbackButton(title, payload);
    }

    private PostbackButton(String title, String payload) {
        super(Type.POSTBACK);
        this.title = title;
        this.payload = payload;
    }

    @Override
    public boolean isPostbackButton() {
        return true;
    }

    @Override
    public PostbackButton asPostbackButton() {
        return this;
    }

    public String title() {
        return title;
    }

    public String payload() {
        return payload;
    }
}
