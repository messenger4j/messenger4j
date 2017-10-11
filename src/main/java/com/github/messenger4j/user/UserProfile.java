package com.github.messenger4j.user;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_AD_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_FIRST_NAME;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_GENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_IS_PAYMENT_ENABLED;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_LAST_AD_REFERRAL;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_LAST_NAME;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_LOCALE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_PROFILE_PIC;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SOURCE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMEZONE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsBoolean;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsFloat;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.v3.receive.Referral;
import com.google.gson.JsonObject;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
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
    private final Referral lastAdReferral;

    public static UserProfile fromJson(JsonObject jsonObject) {
        final String firstName = getPropertyAsString(jsonObject, PROP_FIRST_NAME);
        final String lastName = getPropertyAsString(jsonObject, PROP_LAST_NAME);
        final String profilePic = getPropertyAsString(jsonObject, PROP_PROFILE_PIC);
        final String locale = getPropertyAsString(jsonObject, PROP_LOCALE);
        final Float timezoneOffset = getPropertyAsFloat(jsonObject, PROP_TIMEZONE);
        final String genderString = getPropertyAsString(jsonObject, PROP_GENDER);
        final Gender gender = genderString == null ? null : Gender.valueOf(genderString.toUpperCase());
        final boolean isPaymentEnabled = getPropertyAsBoolean(jsonObject, PROP_IS_PAYMENT_ENABLED)
                .orElseThrow(IllegalArgumentException::new);

        Referral lastAdReferral = null;
        if (hasProperty(jsonObject, PROP_LAST_AD_REFERRAL)) {
            final String source = getPropertyAsString(jsonObject, PROP_LAST_AD_REFERRAL, PROP_SOURCE);
            final String type = getPropertyAsString(jsonObject, PROP_LAST_AD_REFERRAL, PROP_TYPE);
            final String adId = getPropertyAsString(jsonObject, PROP_LAST_AD_REFERRAL, PROP_AD_ID);

            lastAdReferral = new Referral(source, type, null, adId);
        }

        return new UserProfile(firstName, lastName, profilePic, locale, timezoneOffset, gender, isPaymentEnabled, lastAdReferral);
    }

    public UserProfile(@NonNull String firstName, @NonNull String lastName, @NonNull String profilePicture,
                       @NonNull String locale, float timezoneOffset, @NonNull Gender gender, boolean isPaymentEnabled,
                       Referral lastAdReferral) {

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
        return Optional.ofNullable(lastAdReferral);
    }

    /**
     * @author Max Grabenhorst
     * @since 0.8.0
     */
    public enum Gender {
        MALE, FEMALE
    }
}
