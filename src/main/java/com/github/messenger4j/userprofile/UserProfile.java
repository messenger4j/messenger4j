package com.github.messenger4j.userprofile;

import com.github.messenger4j.webhook.event.common.Referral;
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
public final class UserProfile {

    private final String firstName;
    private final String lastName;
    private final String profilePicture;

    public UserProfile(@NonNull String firstName, @NonNull String lastName, @NonNull String profilePicture) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String profilePicture() {
        return profilePicture;
    }

}
