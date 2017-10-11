package com.github.messenger4j.test.integration.user;

import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.GET;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.common.MessengerHttpClient.HttpResponse;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.user.UserProfile;
import com.github.messenger4j.v3.Messenger;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
 */
public class UserProfileClientTest {

    private static final String FB_GRAPH_API_URL = "https://graph.facebook.com/v2.8/%s?fields=first_name," +
            "last_name,profile_pic,locale,timezone,gender,is_payment_enabled,last_ad_referral&access_token=%s";
    private static final String PAGE_ACCESS_TOKEN = "PAGE_ACCESS_TOKEN";

    private final MessengerHttpClient mockHttpClient = mock(MessengerHttpClient.class);
    private final Messenger messenger = Messenger.create(PAGE_ACCESS_TOKEN, "test", "test", mockHttpClient);

    @Test
    public void shouldHandleSuccessfulResponse() throws Exception {
        //given
        final String userId = "USER_ID";
        final HttpResponse successfulResponse = new HttpResponse(200, "{\n" +
                "  \"first_name\": \"Peter\",\n" +
                "  \"last_name\": \"Chang\",\n" +
                "  \"profile_pic\": \"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/p200x200/" +
                "13055603_10105219398495383_8237637584159975445_n.jpg?oh=1d241d4b6d4dac50eaf9bb73288ea192&oe=57AF5C03&" +
                "__gda__=1470213755_ab17c8c8e3a0a447fed3f272fa2179ce\",\n" +
                "  \"locale\": \"en_US\",\n" +
                "  \"timezone\": -7,\n" +
                "  \"gender\": \"male\",\n" +
                "  \"is_payment_enabled\": true,\n" +
                "  \"last_ad_referral\": {\n" +
                "    \"source\": \"ADS\",\n" +
                "    \"type\": \"OPEN_THREAD\",\n" +
                "    \"ad_id\": \"6045246247433\"\n" +
                "  }\n" +
                "}");
        when(mockHttpClient.execute(eq(GET), anyString(), isNull())).thenReturn(successfulResponse);

        //when
        final UserProfile userProfile = messenger.queryUserProfileById(userId);

        //then
        final String expectedRequestUrl = String.format(FB_GRAPH_API_URL, userId, PAGE_ACCESS_TOKEN);
        verify(mockHttpClient).execute(eq(GET), eq(expectedRequestUrl), isNull());

        assertThat(userProfile, is(notNullValue()));
        assertThat(userProfile.firstName(), is(equalTo("Peter")));
        assertThat(userProfile.lastName(), is(equalTo("Chang")));
        assertThat(userProfile.profilePicture(), is(equalTo("https://fbcdn-profile-a.akamaihd.net/" +
                "hprofile-ak-xpf1/v/t1.0-1/p200x200/13055603_10105219398495383_8237637584159975445_n.jpg" +
                "?oh=1d241d4b6d4dac50eaf9bb73288ea192&oe=57AF5C03&__gda__=1470213755_ab17c8c8e3a0a447fed3f272fa2179ce")));
        assertThat(userProfile.locale(), is(equalTo("en_US")));
        assertThat(userProfile.timezoneOffset(), is(equalTo(-7f)));
        assertThat(userProfile.gender(), is(equalTo(UserProfile.Gender.MALE)));
        assertThat(userProfile.isPaymentEnabled(), is(true));
        assertThat(userProfile.lastAdReferral().isPresent(), is(true));
        assertThat(userProfile.lastAdReferral().get().source(), is(equalTo("ADS")));
        assertThat(userProfile.lastAdReferral().get().type(), is(equalTo("OPEN_THREAD")));
        assertThat(userProfile.lastAdReferral().get().adId().isPresent(), is(true));
        assertThat(userProfile.lastAdReferral().get().adId().get(), is(equalTo("6045246247433")));
    }

    @Test
    public void shouldHandleEmptyResponse() throws Exception {
        //given
        final HttpResponse emptyResponse = new HttpResponse(200, "{}");
        when(mockHttpClient.execute(eq(GET), anyString(), isNull())).thenReturn(emptyResponse);

        //when
        MessengerApiException messengerApiException = null;
        try {
            messenger.queryUserProfileById("USER_ID");
        } catch (MessengerApiException e) {
            messengerApiException = e;
        }

        //then
        assertThat(messengerApiException, is(notNullValue()));
        assertThat(messengerApiException.getMessage(), is(equalTo("The response JSON does not contain any key/value pair")));
        assertThat(messengerApiException.getType(), is(nullValue()));
        assertThat(messengerApiException.getCode(), is(nullValue()));
        assertThat(messengerApiException.getFbTraceId(), is(nullValue()));
    }

    @Test
    public void shouldHandleErrorResponse() throws Exception {
        //given
        final HttpResponse errorResponse = new HttpResponse(401, "{\n" +
                "  \"error\": {\n" +
                "    \"message\": \"Invalid OAuth access token.\",\n" +
                "    \"type\": \"OAuthException\",\n" +
                "    \"code\": 190,\n" +
                "    \"fbtrace_id\": \"BLBz/WZt8dN\"\n" +
                "  }\n" +
                "}");
        when(mockHttpClient.execute(eq(GET), anyString(), isNull())).thenReturn(errorResponse);

        //when
        MessengerApiException messengerApiException = null;
        try {
            messenger.queryUserProfileById("USER_ID");
        } catch (MessengerApiException e) {
            messengerApiException = e;
        }

        //then
        assertThat(messengerApiException, is(notNullValue()));
        assertThat(messengerApiException.getMessage(), is(equalTo("Invalid OAuth access token.")));
        assertThat(messengerApiException.getType(), is(equalTo("OAuthException")));
        assertThat(messengerApiException.getCode(), is(equalTo(190)));
        assertThat(messengerApiException.getFbTraceId(), is(equalTo("BLBz/WZt8dN")));
    }
}