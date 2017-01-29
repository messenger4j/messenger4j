package com.github.messenger4j.send.buttons;

import com.github.messenger4j.internal.PreConditions;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class CallButton extends TitleButton {

    private final String payload;

    private CallButton(Builder builder) {
        super(ButtonType.PHONE_NUMBER, builder.title);
        payload = builder.payload;
    }

    @Override
    public boolean isCallButton() {
        return true;
    }

    @Override
    public CallButton asCallButton() {
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
        CallButton that = (CallButton) o;
        return Objects.equals(payload, that.payload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), payload);
    }

    @Override
    public String toString() {
        return "CallButton{" +
                "payload='" + payload + '\'' +
                "} super=" + super.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private static final int TITLE_CHARACTER_LIMIT = 20;

        private final String title;
        private final String payload;
        private final ListBuilder listBuilder;

        Builder(String title, String payload, ListBuilder listBuilder) {
            PreConditions.notNullOrBlank(title, "title");
            PreConditions.lengthNotGreaterThan(title, TITLE_CHARACTER_LIMIT, "title");

            PreConditions.notNullOrBlank(payload, "payload");
            PreConditions.startsWith(payload, "+", "payload");

            this.title = title;
            this.payload = payload;
            this.listBuilder = listBuilder;
        }

        public ListBuilder toList() {
            return this.listBuilder.addButtonToList(new CallButton(this));
        }
    }
}