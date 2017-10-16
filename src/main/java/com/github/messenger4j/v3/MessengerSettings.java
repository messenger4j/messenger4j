package com.github.messenger4j.v3;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class MessengerSettings {

    @SerializedName("get_started")
    private final StartButton startButton;
    private final Greeting greeting;

    public static Builder newBuilder() {
        return new Builder();
    }

    private MessengerSettings(StartButton startButton, Greeting greeting) {
        this.startButton = startButton;
        this.greeting = greeting;
    }

    public StartButton startButton() {
        return startButton;
    }

    public Greeting greeting() {
        return greeting;
    }

    public static final class Builder {

        private StartButton startButton;
        private Greeting greeting;

        public Builder startButton(@NonNull String payload) {
            this.startButton = StartButton.create(payload);
            return this;
        }

        public Builder greeting(@NonNull Greeting greeting) {
            this.greeting = greeting;
            return this;
        }

        public MessengerSettings build() {
            return new MessengerSettings(startButton, greeting);
        }
    }
}
