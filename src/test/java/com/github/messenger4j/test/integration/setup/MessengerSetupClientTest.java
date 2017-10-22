package com.github.messenger4j.test.integration.setup;

import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.DELETE;
import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.POST;
import static com.github.messenger4j.common.WebviewHeightRatio.FULL;
import static com.github.messenger4j.setup.CallToActionType.NESTED;
import static com.github.messenger4j.setup.CallToActionType.POSTBACK;
import static com.github.messenger4j.setup.CallToActionType.WEB_URL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.common.MessengerHttpClient.HttpMethod;
import com.github.messenger4j.common.MessengerHttpClient.HttpResponse;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.setup.CallToAction;
import com.github.messenger4j.setup.SetupResponse;
import com.github.messenger4j.v3.Greeting;
import com.github.messenger4j.v3.LocalizedGreeting;
import com.github.messenger4j.v3.LocalizedPersistentMenu;
import com.github.messenger4j.v3.Messenger;
import com.github.messenger4j.v3.MessengerSettingProperty;
import com.github.messenger4j.v3.MessengerSettings;
import com.github.messenger4j.v3.PersistentMenu;
import com.github.messenger4j.v3.SupportedLocale;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Andriy Koretskyy
 * @since 0.8.0
 */
public class MessengerSetupClientTest {

    private static final String PAGE_ACCESS_TOKEN = "PAGE_ACCESS_TOKEN";

    private final MessengerHttpClient mockHttpClient = mock(MessengerHttpClient.class);
    private final HttpResponse fakeResponse = new HttpResponse(200,
            "{\"result\": \"Successfully added new_thread's CTAs\"}");

    private final Messenger messenger = Messenger.create(PAGE_ACCESS_TOKEN, "test", "test", mockHttpClient);

    @Before
    public void beforeEach() throws Exception {
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(fakeResponse);
    }

    @Test
    public void shouldSetupStartButton() throws Exception {
        //given
        final MessengerSettings messengerSettings = MessengerSettings.newBuilder().startButton("Button pressed").build();

        //when
        messenger.updateSettings(messengerSettings);

        //then
        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final String expectedJsonBody = "{ \n" +
                "  \"get_started\":{\n" +
                "    \"payload\":\"Button pressed\"\n" +
                "  }\n" +
                "}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
        JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
    }

    @Test
    public void shouldDeleteStartButton() throws Exception {
        //when
        messenger.deleteSettings(MessengerSettingProperty.START_BUTTON);

        //then
        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final String expectedJsonBody = "{\n" +
                "  \"fields\": [\n" +
                "    \"get_started\"\n" +
                "  ]\n" +
                "}";
        verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
        JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
    }

    @Test
    public void shouldSetupGreetingText() throws Exception {
        //given
        final Greeting greeting = Greeting.create("Hello!", LocalizedGreeting.create(SupportedLocale.en_US,
                "Timeless apparel for the masses."));
        final MessengerSettings messengerSettings = MessengerSettings.newBuilder().greeting(greeting).build();

        //when
        messenger.updateSettings(messengerSettings);

        //then
        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final String expectedJsonBody = "{\n" +
                "  \"greeting\":[\n" +
                "    {\n" +
                "      \"locale\":\"default\",\n" +
                "      \"text\":\"Hello!\"\n" +
                "    }, {\n" +
                "      \"locale\":\"en_US\",\n" +
                "      \"text\":\"Timeless apparel for the masses.\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
        JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
    }

    @Test
    public void shouldDeleteGreetingText() throws Exception {
        //when
        messenger.deleteSettings(MessengerSettingProperty.GREETING);

        //then
        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final String expectedJsonBody = "{\n" +
                "  \"fields\": [\n" +
                "    \"greeting\"\n" +
                "  ]\n" +
                "}";
        verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
        JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
    }

    @Test
    public void shouldSetupPersistentMenu() throws Exception {
        //given
        final CallToAction callToActionAA = CallToAction.newBuilder()
                .title("Pay Bill")
                .type(POSTBACK)
                .payload("PAYBILL_PAYLOAD")
                .build();

        final CallToAction callToActionAB = CallToAction.newBuilder()
                .title("History")
                .type(POSTBACK)
                .payload("HISTORY_PAYLOAD")
                .build();

        final CallToAction callToActionAC = CallToAction.newBuilder()
                .title("Contact Info")
                .type(POSTBACK)
                .payload("CONTACT_INFO_PAYLOAD")
                .build();

        final CallToAction callToActionA = CallToAction.newBuilder()
                .title("My Account")
                .type(NESTED)
                .callToActions(Arrays.asList(callToActionAA, callToActionAB, callToActionAC))
                .build();

        final CallToAction callToActionB = CallToAction.newBuilder()
                .title("Latest News")
                .type(WEB_URL)
                .url(new URL("http://petershats.parseapp.com/hat-news"))
                .webviewHeightRatio(FULL)
                .build();

        final PersistentMenu persistentMenu = PersistentMenu.create(true, Arrays.asList(callToActionA, callToActionB),
                LocalizedPersistentMenu.create(SupportedLocale.zh_CN, false, null));

        final MessengerSettings messengerSettings = MessengerSettings.newBuilder().persistentMenu(persistentMenu).build();

        //when
        messenger.updateSettings(messengerSettings);

        //then
        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final String expectedJsonBody = "{\n" +
                "  \"persistent_menu\":[\n" +
                "    {\n" +
                "      \"locale\":\"default\",\n" +
                "      \"composer_input_disabled\": true,\n" +
                "      \"call_to_actions\":[\n" +
                "        {\n" +
                "          \"title\":\"My Account\",\n" +
                "          \"type\":\"nested\",\n" +
                "          \"call_to_actions\":[\n" +
                "            {\n" +
                "              \"title\":\"Pay Bill\",\n" +
                "              \"type\":\"postback\",\n" +
                "              \"payload\":\"PAYBILL_PAYLOAD\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\":\"History\",\n" +
                "              \"type\":\"postback\",\n" +
                "              \"payload\":\"HISTORY_PAYLOAD\"\n" +
                "            },\n" +
                "            {\n" +
                "              \"title\":\"Contact Info\",\n" +
                "              \"type\":\"postback\",\n" +
                "              \"payload\":\"CONTACT_INFO_PAYLOAD\"\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\":\"web_url\",\n" +
                "          \"title\":\"Latest News\",\n" +
                "          \"url\":\"http://petershats.parseapp.com/hat-news\",\n" +
                "          \"webview_height_ratio\":\"full\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"locale\":\"zh_CN\",\n" +
                "      \"composer_input_disabled\":false\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
        JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
    }

    @Test
    public void shouldDeletePersistentMenu() throws Exception {
        //when
        messenger.deleteSettings(MessengerSettingProperty.PERSISTENT_MENU);

        //then
        final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
        final String expectedJsonBody = "{\n" +
                "  \"fields\": [\n" +
                "    \"persistent_menu\"\n" +
                "  ]\n" +
                "}";
        verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
        JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
    }

    @Test
    public void shouldHandleUpdateSuccessResponse() throws Exception {
        //given
        final MessengerSettings messengerSettings = MessengerSettings.newBuilder().startButton("test").build();
        final HttpResponse successfulResponse = new HttpResponse(200, "{\"result\": \"success\"}");
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(successfulResponse);

        //when
        final SetupResponse setupResponse = messenger.updateSettings(messengerSettings);

        //then
        assertThat(setupResponse, is(notNullValue()));
        assertThat(setupResponse.result(), is(equalTo("success")));
    }

    @Test
    public void shouldHandleUpdateErrorResponse() throws Exception {
        //given
        final MessengerSettings messengerSettings = MessengerSettings.newBuilder().startButton("test").build();
        final HttpResponse errorResponse = new HttpResponse(401, "{\n" +
                "  \"error\": {\n" +
                "    \"message\": \"Invalid OAuth access token.\",\n" +
                "    \"type\": \"OAuthException\",\n" +
                "    \"code\": 190,\n" +
                "    \"fbtrace_id\": \"BLBz/WZt8dN\"\n" +
                "  }\n" +
                "}");
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(errorResponse);

        //when
        MessengerApiException messengerApiException = null;
        try {
            messenger.updateSettings(messengerSettings);
        } catch (MessengerApiException e) {
            messengerApiException = e;
        }

        //then
        assertThat(messengerApiException, is(notNullValue()));
        assertThat(messengerApiException.message(), is(equalTo("Invalid OAuth access token.")));
        assertThat(messengerApiException.type(), is(equalTo(Optional.of("OAuthException"))));
        assertThat(messengerApiException.code(), is(equalTo(Optional.of(190))));
        assertThat(messengerApiException.fbTraceId(), is(equalTo(Optional.of("BLBz/WZt8dN"))));
    }

    @Test
    public void shouldHandleDeleteSuccessResponse() throws Exception {
        //given
        final HttpResponse successfulResponse = new HttpResponse(200, "{\"result\": \"success\"}");
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(successfulResponse);

        //when
        final SetupResponse setupResponse = messenger.deleteSettings(MessengerSettingProperty.GREETING);

        //then
        assertThat(setupResponse, is(notNullValue()));
        assertThat(setupResponse.result(), is(equalTo("success")));
    }

    @Test
    public void shouldHandleDeleteErrorResponse() throws Exception {
        //given
        final HttpResponse errorResponse = new HttpResponse(401, "{\n" +
                "  \"error\": {\n" +
                "    \"message\": \"Invalid OAuth access token.\",\n" +
                "    \"type\": \"OAuthException\",\n" +
                "    \"code\": 190,\n" +
                "    \"fbtrace_id\": \"BLBz/WZt8dN\"\n" +
                "  }\n" +
                "}");
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(errorResponse);

        //when
        MessengerApiException messengerApiException = null;
        try {
            messenger.deleteSettings(MessengerSettingProperty.GREETING);
        } catch (MessengerApiException e) {
            messengerApiException = e;
        }

        //then
        assertThat(messengerApiException, is(notNullValue()));
        assertThat(messengerApiException.message(), is(equalTo("Invalid OAuth access token.")));
        assertThat(messengerApiException.type(), is(equalTo(Optional.of("OAuthException"))));
        assertThat(messengerApiException.code(), is(equalTo(Optional.of(190))));
        assertThat(messengerApiException.fbTraceId(), is(equalTo(Optional.of("BLBz/WZt8dN"))));
    }
}