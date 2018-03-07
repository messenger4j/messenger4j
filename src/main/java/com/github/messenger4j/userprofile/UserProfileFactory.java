package com.github.messenger4j.userprofile;

import com.github.messenger4j.webhook.event.common.Referral;
import com.google.gson.JsonObject;

import java.util.Optional;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.*;
import static com.github.messenger4j.internal.gson.GsonUtil.*;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class UserProfileFactory {

	private UserProfileFactory() {
	}

	public static UserProfile create(JsonObject jsonObject) {
		if (hasProperty(jsonObject, PROP_ERROR)) {
			throw new IllegalArgumentException("User Profile error");
		}
		final Optional<String> firstName = getPropertyAsString(jsonObject, PROP_FIRST_NAME);
		final Optional<String> lastName = getPropertyAsString(jsonObject, PROP_LAST_NAME);
		final Optional<String> profilePic = getPropertyAsString(jsonObject, PROP_PROFILE_PIC);
		final Optional<String> locale = getPropertyAsString(jsonObject, PROP_LOCALE);
		final Optional<Float> timezoneOffset = getPropertyAsFloat(jsonObject, PROP_TIMEZONE);
		final Optional<UserProfile.Gender> gender = getPropertyAsString(jsonObject, PROP_GENDER).map(s -> of(UserProfile.Gender.valueOf(s.toUpperCase())))
				.orElse(empty());
		final Optional<Boolean> isPaymentEnabled = getPropertyAsBoolean(jsonObject, PROP_IS_PAYMENT_ENABLED);
		final Optional<Referral> lastAdReferral = getPropertyAsJsonObject(jsonObject, PROP_LAST_AD_REFERRAL).map(referralJsonObject -> {
			final String source = getPropertyAsString(referralJsonObject, PROP_SOURCE).orElse(null);
			final String type = getPropertyAsString(referralJsonObject, PROP_TYPE).orElse(null);
			final String adId = getPropertyAsString(referralJsonObject, PROP_AD_ID).orElse(null);
			return of(new Referral(source, type, empty(), of(adId)));
		}).orElse(empty());

		return new UserProfile(firstName, lastName, profilePic, locale, timezoneOffset, gender, isPaymentEnabled, lastAdReferral);
	}
}
