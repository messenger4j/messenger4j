package com.github.messenger4j.messengerprofile.persistentmenu.action;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class PostbackCallToAction extends CallToAction {

    private final String payload;

    public static PostbackCallToAction create(@NonNull String title, @NonNull String payload) {
        return new PostbackCallToAction(title, payload);
    }

    private PostbackCallToAction(String title, String payload) {
        super(Type.POSTBACK, title);
        this.payload = payload;
    }

    public String payload() {
        return payload;
    }
}
