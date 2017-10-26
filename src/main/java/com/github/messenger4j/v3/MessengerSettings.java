package com.github.messenger4j.v3;

import java.util.Optional;
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

    private final Optional<StartButton> startButton;
    private final Optional<Greeting> greeting;
    private final Optional<PersistentMenu> persistentMenu;

    public static MessengerSettings create(@NonNull Optional<StartButton> startButton,
                                           @NonNull Optional<Greeting> greeting,
                                           @NonNull Optional<PersistentMenu> persistentMenu) {
        return new MessengerSettings(startButton, greeting, persistentMenu);
    }

    private MessengerSettings(Optional<StartButton> startButton, Optional<Greeting> greeting,
                              Optional<PersistentMenu> persistentMenu) {
        this.startButton = startButton;
        this.greeting = greeting;
        this.persistentMenu = persistentMenu;
    }

    public Optional<StartButton> startButton() {
        return startButton;
    }

    public Optional<Greeting> greeting() {
        return greeting;
    }

    public Optional<PersistentMenu> persistentMenu() {
        return persistentMenu;
    }
}
