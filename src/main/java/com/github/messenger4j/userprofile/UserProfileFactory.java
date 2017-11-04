package com.github.messenger4j.userprofile;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_AD_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_FIRST_NAME;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_GENDER;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_IS_PAYMENT_ENABLED;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_LAST_AD_REFERRAL;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_LAST_NAME;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_LOCALE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_PROFILE_PIC;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SOURCE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TIMEZONE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsBoolean;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsFloat;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsJsonObject;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;
import static java.util.Optional.empty;
import static java.util.Optional.of;

import com.github.messenger4j.webhook.event.common.Referral;
import com.google.gson.JsonObject;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class UserProfileFactory {

    private UserProfileFactory() {
    }

    public static UserProfile create(JsonObject jsonObject) {
        final String firstName = getPropertyAsString(jsonObject, PROP_FIRST_NAME)
                .orElseThrow(IllegalArgumentException::new);
        final String lastName = getPropertyAsString(jsonObject, PROP_LAST_NAME)
                .orElseThrow(IllegalArgumentException::new);
        final String profilePic = getPropertyAsString(jsonObject, PROP_PROFILE_PIC)
                .orElseThrow(IllegalArgumentException::new);
        final String locale = getPropertyAsString(jsonObject, PROP_LOCALE)
                .orElseThrow(IllegalArgumentException::new);
        final Float timezoneOffset = getPropertyAsFloat(jsonObject, PROP_TIMEZONE)
                .orElseThrow(IllegalArgumentException::new);
        final UserProfile.Gender gender = getPropertyAsString(jsonObject, PROP_GENDER)
                .map(String::toUpperCase)
                .map(UserProfile.Gender::valueOf)
                .orElseThrow(IllegalArgumentException::new);
        final boolean isPaymentEnabled = getPropertyAsBoolean(jsonObject, PROP_IS_PAYMENT_ENABLED)
                .orElseThrow(IllegalArgumentException::new);

        final Optional<Referral> lastAdReferral = getPropertyAsJsonObject(jsonObject, PROP_LAST_AD_REFERRAL)
                .map(referralJsonObject -> {
                    final String source = getPropertyAsString(referralJsonObject, PROP_SOURCE)
                            .orElseThrow(IllegalArgumentException::new);
                    final String type = getPropertyAsString(referralJsonObject, PROP_TYPE)
                            .orElseThrow(IllegalArgumentException::new);
                    final String adId = getPropertyAsString(referralJsonObject, PROP_AD_ID)
                            .orElseThrow(IllegalArgumentException::new);
                    return of(new Referral(source, type, empty(), of(adId)));
                })
                .orElse(empty());

        return new UserProfile(firstName, lastName, profilePic, locale, timezoneOffset, gender,
                isPaymentEnabled, lastAdReferral);
    }
}
