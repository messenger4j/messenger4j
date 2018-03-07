package com.github.messenger4j.test.integration;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.spi.MessengerHttpClient;
import com.github.messenger4j.spi.MessengerHttpClient.HttpResponse;
import com.github.messenger4j.userprofile.UserProfile;
import org.junit.Test;

import java.util.Optional;

import static com.github.messenger4j.spi.MessengerHttpClient.HttpMethod.GET;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public class UserProfileTest {

	private static final Messenger.Version VERSION = Messenger.Version.LATEST;
	private static final String FB_GRAPH_API_BASE_URL = "https://graph.facebook.com/";
	private static final String FB_USER_PROFILE_DEFAULT_FIELDS = "first_name,last_name,profile_pic,locale,timezone,gender,is_payment_enabled,last_ad_referral";
	private static final String FB_GRAPH_API_URL_USER = FB_GRAPH_API_BASE_URL.concat(VERSION.getVersionUrl()).concat("/%s?fields=")
			.concat(FB_USER_PROFILE_DEFAULT_FIELDS).concat("&access_token=%s");
	private static final String PAGE_ACCESS_TOKEN = "PAGE_ACCESS_TOKEN";

	private final MessengerHttpClient mockHttpClient = mock(MessengerHttpClient.class);
	private final Messenger messenger = Messenger
			.create(PAGE_ACCESS_TOKEN, "test", "test", of(VERSION.getVersionUrl()), of(FB_USER_PROFILE_DEFAULT_FIELDS), of(mockHttpClient));

	@Test
	public void shouldQueryUserProfile() throws Exception {
		final String userId = "USER_ID";
		final HttpResponse successfulResponse = new HttpResponse(200, "{\n" + "  \"first_name\": \"Peter\",\n" + "  \"last_name\": \"Chang\",\n"
				+ "  \"profile_pic\": \"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/p200x200/"
				+ "13055603_10105219398495383_8237637584159975445_n.jpg?oh=1d241d4b6d4dac50eaf9bb73288ea192&oe=57AF5C03&"
				+ "__gda__=1470213755_ab17c8c8e3a0a447fed3f272fa2179ce\",\n" + "  \"locale\": \"en_US\",\n" + "  \"timezone\": -7,\n"
				+ "  \"gender\": \"male\",\n" + "  \"is_payment_enabled\": true,\n" + "  \"last_ad_referral\": {\n" + "    \"source\": \"ADS\",\n"
				+ "    \"type\": \"OPEN_THREAD\",\n" + "    \"ad_id\": \"6045246247433\"\n" + "  }\n" + "}");
		when(mockHttpClient.execute(eq(GET), anyString(), isNull())).thenReturn(successfulResponse);

		// tag::user-QueryProfile[]
		final UserProfile userProfile = messenger.queryUserProfile(userId);
		// end::user-QueryProfile[]

		final String expectedRequestUrl = String.format(FB_GRAPH_API_URL_USER, userId, PAGE_ACCESS_TOKEN);
		verify(mockHttpClient).execute(eq(GET), eq(expectedRequestUrl), isNull());

		assertThat(userProfile, is(notNullValue()));
		assertThat(userProfile.firstName().isPresent(), is(true));
		assertThat(userProfile.firstName().get(), is(equalTo("Peter")));
		assertThat(userProfile.lastName().isPresent(), is(true));
		assertThat(userProfile.lastName().get(), is(equalTo("Chang")));
		assertThat(userProfile.profilePicture().isPresent(), is(true));
		assertThat(userProfile.profilePicture().get(), is(equalTo(
				"https://fbcdn-profile-a.akamaihd.net/" + "hprofile-ak-xpf1/v/t1.0-1/p200x200/13055603_10105219398495383_8237637584159975445_n.jpg"
						+ "?oh=1d241d4b6d4dac50eaf9bb73288ea192&oe=57AF5C03&__gda__=1470213755_ab17c8c8e3a0a447fed3f272fa2179ce")));
		assertThat(userProfile.locale().isPresent(), is(true));
		assertThat(userProfile.locale().get(), is(equalTo("en_US")));
		assertThat(userProfile.timezoneOffset().isPresent(), is(true));
		assertThat(userProfile.timezoneOffset().get(), is(equalTo(-7f)));
		assertThat(userProfile.gender().isPresent(), is(true));
		assertThat(userProfile.gender().get(), is(equalTo(UserProfile.Gender.MALE)));
		assertThat(userProfile.isPaymentEnabled().isPresent(), is(true));
		assertThat(userProfile.isPaymentEnabled().get(), is(true));
		assertThat(userProfile.lastAdReferral().isPresent(), is(true));
		assertThat(userProfile.lastAdReferral().get().source(), is(equalTo("ADS")));
		assertThat(userProfile.lastAdReferral().get().type(), is(equalTo("OPEN_THREAD")));
		assertThat(userProfile.lastAdReferral().get().adId(), is(equalTo(of("6045246247433"))));
	}

	@Test
	public void shouldHandleEmptyResponse() throws Exception {
		final HttpResponse emptyResponse = new HttpResponse(200, "{}");
		when(mockHttpClient.execute(eq(GET), anyString(), isNull())).thenReturn(emptyResponse);

		MessengerApiException messengerApiException = null;
		try {
			messenger.queryUserProfile("USER_ID");
		} catch (MessengerApiException e) {
			messengerApiException = e;
		}

		assertThat(messengerApiException, is(notNullValue()));
		assertThat(messengerApiException.message(), is(equalTo("The response JSON does not contain any key/value pair")));
		assertThat(messengerApiException.type(), is(Optional.empty()));
		assertThat(messengerApiException.code(), is(Optional.empty()));
		assertThat(messengerApiException.fbTraceId(), is(Optional.empty()));
	}

	@Test
	public void shouldHandleErrorResponse() throws Exception {
		final HttpResponse errorResponse = new HttpResponse(401,
				"{\n" + "  \"error\": {\n" + "    \"message\": \"Invalid OAuth access token.\",\n" + "    \"type\": \"OAuthException\",\n"
						+ "    \"code\": 190,\n" + "    \"fbtrace_id\": \"BLBz/WZt8dN\"\n" + "  }\n" + "}");
		when(mockHttpClient.execute(eq(GET), anyString(), isNull())).thenReturn(errorResponse);

		MessengerApiException messengerApiException = null;
		try {
			messenger.queryUserProfile("USER_ID");
		} catch (MessengerApiException e) {
			messengerApiException = e;
		}

		assertThat(messengerApiException, is(notNullValue()));
		assertThat(messengerApiException.message(), is(equalTo("Invalid OAuth access token.")));
		assertThat(messengerApiException.type(), is(equalTo(of("OAuthException"))));
		assertThat(messengerApiException.code(), is(equalTo(of(190))));
		assertThat(messengerApiException.fbTraceId(), is(equalTo(of("BLBz/WZt8dN"))));
	}
}