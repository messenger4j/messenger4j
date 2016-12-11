package com.github.messenger4j.send.buttons;

import com.github.messenger4j.internal.PreConditions;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class PostbackButton extends TitleButton {

    private final String payload;

    private PostbackButton(Builder builder) {
        super(ButtonType.POSTBACK, builder.title);
        payload = builder.payload;
    }

    @Override
    public boolean isPostbackButton() {
        return true;
    }

    @Override
    public PostbackButton asPostbackButton() {
        return this;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PostbackButton that = (PostbackButton) o;
        return Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), payload);
    }

    @Override
    public String toString() {
        return "PostbackButton{" +
                "payload='" + payload + '\'' +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private static final int TITLE_CHARACTER_LIMIT = 20;
        private static final int PAYLOAD_CHARACTER_LIMIT = 1000;

        private final String title;
        private final String payload;
        private final ListBuilder listBuilder;

        Builder(String title, String payload, ListBuilder listBuilder) {
            PreConditions.notNullOrBlank(title, "title");
            PreConditions.lengthNotGreaterThan(title, TITLE_CHARACTER_LIMIT, "title");

            PreConditions.notNullOrBlank(payload, "payload");
            PreConditions.lengthNotGreaterThan(payload, PAYLOAD_CHARACTER_LIMIT, "payload");

            this.title = title;
            this.payload = payload;
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new PostbackButton(this));
        }
    }
}