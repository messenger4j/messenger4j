package com.github.messenger4j.messengerprofile.getstarted;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class StartButton {

    private final String payload;

    public static StartButton create(@NonNull String payload) {
        return new StartButton(payload);
    }

    private StartButton(String payload) {
        this.payload = payload;
    }

    public String payload() {
        return payload;
    }
}
