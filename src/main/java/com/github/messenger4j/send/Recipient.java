package com.github.messenger4j.send;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString
@EqualsAndHashCode
public final class Recipient {

    private final String id;
    private final String phoneNumber;

    public static Recipient createById(@NonNull String userId) {
        return new Recipient(userId, null);
    }

    public static Recipient createByPhoneNumber(@NonNull String phoneNumber) {
        return new Recipient(null, phoneNumber);
    }

    private Recipient(String id, String phoneNumber) {
        this.id = id;
        this.phoneNumber = phoneNumber;
    }

    public Optional<String> id() {
        return Optional.ofNullable(id);
    }

    public Optional<String> phoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }
}