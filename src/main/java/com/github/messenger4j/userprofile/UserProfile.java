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
    private final String locale;
    private final float timezoneOffset;
    private final Gender gender;
    private final boolean isPaymentEnabled;
    private final Optional<Referral> lastAdReferral;

    public UserProfile(@NonNull String firstName, @NonNull String lastName, @NonNull String profilePicture,
                       @NonNull String locale, float timezoneOffset, @NonNull Gender gender, boolean isPaymentEnabled,
                       @NonNull Optional<Referral> lastAdReferral) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.locale = locale;
        this.timezoneOffset = timezoneOffset;
        this.gender = gender;
        this.isPaymentEnabled = isPaymentEnabled;
        this.lastAdReferral = lastAdReferral;
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

    public String locale() {
        return locale;
    }

    public float timezoneOffset() {
        return timezoneOffset;
    }

    public Gender gender() {
        return gender;
    }

    public boolean isPaymentEnabled() {
        return isPaymentEnabled;
    }

    public Optional<Referral> lastAdReferral() {
        return lastAdReferral;
    }

    /**
     * @since 1.0.0
     */
    public enum Gender {
        MALE, FEMALE
    }
}
