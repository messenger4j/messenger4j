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
import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class UserProfileFactory {

    private UserProfileFactory() {
    }

    public static UserProfile create(@NonNull JsonObject jsonObject) {
        final String firstName = getPropertyAsString(jsonObject, PROP_FIRST_NAME);
        final String lastName = getPropertyAsString(jsonObject, PROP_LAST_NAME);
        final String profilePic = getPropertyAsString(jsonObject, PROP_PROFILE_PIC);
        final String locale = getPropertyAsString(jsonObject, PROP_LOCALE);
        final Float timezoneOffset = getPropertyAsFloat(jsonObject, PROP_TIMEZONE);
        final String genderString = getPropertyAsString(jsonObject, PROP_GENDER);
        final UserProfile.Gender gender = genderString == null ? null : UserProfile.Gender.valueOf(genderString.toUpperCase());
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
}
