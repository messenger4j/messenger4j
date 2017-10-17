package com.github.messenger4j.v3;

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

    private final StartButton startButton;
    private final Greeting greeting;
    private final PersistentMenu persistentMenu;

    public static Builder newBuilder() {
        return new Builder();
    }

    private MessengerSettings(StartButton startButton, Greeting greeting, PersistentMenu persistentMenu) {
        this.startButton = startButton;
        this.greeting = greeting;
        this.persistentMenu = persistentMenu;
    }

    public StartButton startButton() {
        return startButton;
    }

    public Greeting greeting() {
        return greeting;
    }

    public PersistentMenu persistentMenu() {
        return persistentMenu;
    }

    public static final class Builder {

        private StartButton startButton;
        private Greeting greeting;
        private PersistentMenu persistentMenu;

        public Builder startButton(@NonNull String payload) {
            this.startButton = StartButton.create(payload);
            return this;
        }

        public Builder greeting(@NonNull Greeting greeting) {
            this.greeting = greeting;
            return this;
        }

        public Builder persistentMenu(@NonNull PersistentMenu persistentMenu) {
            this.persistentMenu = persistentMenu;
            return this;
        }

        public MessengerSettings build() {
            return new MessengerSettings(startButton, greeting, persistentMenu);
        }
    }
}
