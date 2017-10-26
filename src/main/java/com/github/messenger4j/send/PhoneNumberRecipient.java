package com.github.messenger4j.send;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class PhoneNumberRecipient extends Recipient {

    private final String phoneNumber;

    public static PhoneNumberRecipient create(@NonNull String phoneNumber) {
        return new PhoneNumberRecipient(phoneNumber);
    }

    private PhoneNumberRecipient(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String phoneNumber() {
        return phoneNumber;
    }
}
