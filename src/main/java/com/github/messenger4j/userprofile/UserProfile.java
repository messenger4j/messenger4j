package com.github.messenger4j.userprofile;

import com.github.messenger4j.webhook.event.common.Referral;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class UserProfile {

	/**
	 * @since 1.0.0
	 */
	public enum Gender {
		MALE,
		FEMALE
	}

	private final Optional<String> firstName;
	private final Optional<String> lastName;
	private final Optional<String> profilePicture;
	private final Optional<String> locale;
	private final Optional<Float> timezoneOffset;
	private final Optional<Gender> gender;
	private final Optional<Boolean> isPaymentEnabled;
	private final Optional<Referral> lastAdReferral;

	public UserProfile(@NonNull Optional<String> firstName, @NonNull Optional<String> lastName, @NonNull Optional<String> profilePicture,
			@NonNull Optional<String> locale, @NonNull Optional<Float> timezoneOffset, @NonNull Optional<Gender> gender,
			@NonNull Optional<Boolean> isPaymentEnabled, @NonNull Optional<Referral> lastAdReferral) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicture = profilePicture;
		this.locale = locale;
		this.timezoneOffset = timezoneOffset;
		this.gender = gender;
		this.isPaymentEnabled = isPaymentEnabled;
		this.lastAdReferral = lastAdReferral;
	}

	public Optional<String> firstName() {
		return firstName;
	}

	public Optional<String> lastName() {
		return lastName;
	}

	public Optional<String> profilePicture() {
		return profilePicture;
	}

	public Optional<String> locale() {
		return locale;
	}

	public Optional<Float> timezoneOffset() {
		return timezoneOffset;
	}

	public Optional<Gender> gender() {
		return gender;
	}

	public Optional<Boolean> isPaymentEnabled() {
		return isPaymentEnabled;
	}

	public Optional<Referral> lastAdReferral() {
		return lastAdReferral;
	}

}
