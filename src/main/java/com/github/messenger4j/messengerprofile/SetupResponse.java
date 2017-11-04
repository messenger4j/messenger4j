package com.github.messenger4j.messengerprofile;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Andriy Koretskyy
 * @author Max Grabenhorst
 * @since 0.8.0
 */
@ToString
@EqualsAndHashCode
public final class SetupResponse {

    private final String result;

    public SetupResponse(@NonNull String result) {
        this.result = result;
    }

    public String result() {
        return result;
    }
}
