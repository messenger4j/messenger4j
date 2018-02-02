package com.github.messenger4j.send.recipient;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.util.Optional;
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
    private final Optional<String> firstName;
    private final Optional<String> lastName;

    public static PhoneNumberRecipient create(@NonNull String phoneNumber) {
        return new PhoneNumberRecipient(phoneNumber, empty(), empty());
    }

    public static PhoneNumberRecipient create(@NonNull String phoneNumber, @NonNull String firstName,
                                              @NonNull String lastName) {
        return new PhoneNumberRecipient(phoneNumber, of(firstName), of(lastName));
    }

    private PhoneNumberRecipient(String phoneNumber, Optional<String> firstName, Optional<String> lastName) {
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String phoneNumber() {
        return phoneNumber;
    }

    public Optional<String> firstName() {
        return firstName;
    }

    public Optional<String> lastName() {
        return lastName;
    }
}
