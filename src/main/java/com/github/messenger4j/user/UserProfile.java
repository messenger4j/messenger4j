package com.github.messenger4j.user;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_FIRST_NAME;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_GENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_LAST_NAME;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_LOCALE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_PROFILE_PIC;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMEZONE;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsFloat;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonObject;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
 */
public final class UserProfile {

    private final String firstName;
    private final String lastName;
    private final String profilePicture;
    private final String locale;
    private final Float timezoneOffset;
    private final Gender gender;

    static UserProfile fromJson(JsonObject jsonObject) {
        final String firstName = getPropertyAsString(jsonObject, PROP_FIRST_NAME);
        final String lastName = getPropertyAsString(jsonObject, PROP_LAST_NAME);
        final String profilePic = getPropertyAsString(jsonObject, PROP_PROFILE_PIC);
        final String locale = getPropertyAsString(jsonObject, PROP_LOCALE);
        final Float timezoneOffset = getPropertyAsFloat(jsonObject, PROP_TIMEZONE);
        final String genderString = getPropertyAsString(jsonObject, PROP_GENDER);
        final Gender gender = genderString == null ? null : Gender.valueOf(genderString.toUpperCase());

        return new UserProfile(firstName, lastName, profilePic, locale, timezoneOffset, gender);
    }

    public UserProfile(String firstName, String lastName, String profilePicture, String locale,
                       Float timezoneOffset, Gender gender) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.locale = locale;
        this.timezoneOffset = timezoneOffset;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getLocale() {
        return locale;
    }

    public Float getTimezoneOffset() {
        return timezoneOffset;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(profilePicture, that.profilePicture) &&
                Objects.equals(locale, that.locale) &&
                Objects.equals(timezoneOffset, that.timezoneOffset) &&
                gender == that.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, profilePicture, locale, timezoneOffset, gender);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProfile{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", profilePicture='").append(profilePicture).append('\'');
        sb.append(", locale='").append(locale).append('\'');
        sb.append(", timezoneOffset=").append(timezoneOffset);
        sb.append(", gender=").append(gender);
        sb.append('}');
        return sb.toString();
    }

    /**
     * @author Max Grabenhorst
     * @since 0.8.0
     */
    public enum Gender {
        MALE, FEMALE
    }
}
