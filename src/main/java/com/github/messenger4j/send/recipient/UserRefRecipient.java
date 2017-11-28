package com.github.messenger4j.send.recipient;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Joe Tindale
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class UserRefRecipient extends Recipient {

    private final String userRef;

    public static UserRefRecipient create(@NonNull String userRef) {
        return new UserRefRecipient(userRef);
    }

    private UserRefRecipient(String userRef) {
        this.userRef = userRef;
    }

    public String userRef() {
        return userRef;
    }
}
