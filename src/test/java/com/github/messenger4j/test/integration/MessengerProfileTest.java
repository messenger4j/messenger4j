package com.github.messenger4j.test.integration;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.common.SupportedCountry;
import com.github.messenger4j.common.SupportedLocale;
import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.common.WebviewShareButtonState;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.messengerprofile.MessengerSettingProperty;
import com.github.messenger4j.messengerprofile.MessengerSettings;
import com.github.messenger4j.messengerprofile.SetupResponse;
import com.github.messenger4j.messengerprofile.getstarted.StartButton;
import com.github.messenger4j.messengerprofile.greeting.Greeting;
import com.github.messenger4j.messengerprofile.greeting.LocalizedGreeting;
import com.github.messenger4j.messengerprofile.homeurl.HomeUrl;
import com.github.messenger4j.messengerprofile.persistentmenu.LocalizedPersistentMenu;
import com.github.messenger4j.messengerprofile.persistentmenu.PersistentMenu;
import com.github.messenger4j.messengerprofile.persistentmenu.action.NestedCallToAction;
import com.github.messenger4j.messengerprofile.persistentmenu.action.PostbackCallToAction;
import com.github.messenger4j.messengerprofile.persistentmenu.action.UrlCallToAction;
import com.github.messenger4j.messengerprofile.targetaudience.AllTargetAudience;
import com.github.messenger4j.messengerprofile.targetaudience.BlacklistTargetAudience;
import com.github.messenger4j.messengerprofile.targetaudience.NoneTargetAudience;
import com.github.messenger4j.messengerprofile.targetaudience.WhitelistTargetAudience;
import com.github.messenger4j.spi.MessengerHttpClient;
import com.github.messenger4j.spi.MessengerHttpClient.HttpMethod;
import com.github.messenger4j.spi.MessengerHttpClient.HttpResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.github.messenger4j.spi.MessengerHttpClient.HttpMethod.DELETE;
import static com.github.messenger4j.spi.MessengerHttpClient.HttpMethod.POST;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Andriy Koretskyy
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public class MessengerProfileTest {

	private static final String PAGE_ACCESS_TOKEN = "PAGE_ACCESS_TOKEN";

	private final MessengerHttpClient mockHttpClient = mock(MessengerHttpClient.class);
	private final HttpResponse fakeResponse = new HttpResponse(200, "{\"result\": \"Successfully added new_thread's CTAs\"}");

	private final Messenger messenger = Messenger.create(PAGE_ACCESS_TOKEN, "test", "test", empty(), empty(), of(mockHttpClient));

	@Before
	public void beforeEach() throws Exception {
		when(mockHttpClient.execute(any(HttpMethod.class), anyString(), any())).thenReturn(fakeResponse);
	}

	@Test
	public void shouldSetupStartButton() throws Exception {
		// tag::setup-StartButton[]
		final MessengerSettings messengerSettings = MessengerSettings
				.create(of(StartButton.create("Button pressed")), empty(), empty(), empty(), empty(), empty(), empty());

		messenger.updateSettings(messengerSettings);
		// end::setup-StartButton[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{ \n" + "  \"get_started\":{\n" + "    \"payload\":\"Button pressed\"\n" + "  }\n" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldDeleteStartButton() throws Exception {
		// tag::setup-DeleteStartButton[]
		messenger.deleteSettings(MessengerSettingProperty.START_BUTTON);
		// end::setup-DeleteStartButton[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"fields\": [\n" + "    \"get_started\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupGreetingText() throws Exception {
		// tag::setup-GreetingText[]
		final Greeting greeting = Greeting.create("Hello!", LocalizedGreeting.create(SupportedLocale.en_US, "Timeless apparel for the masses."));
		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), of(greeting), empty(), empty(), empty(), empty(), empty());

		messenger.updateSettings(messengerSettings);
		// end::setup-GreetingText[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody =
				"{\n" + "  \"greeting\":[\n" + "    {\n" + "      \"locale\":\"default\",\n" + "      \"text\":\"Hello!\"\n" + "    }, {\n"
						+ "      \"locale\":\"en_US\",\n" + "      \"text\":\"Timeless apparel for the masses.\"\n" + "    }\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldDeleteGreetingText() throws Exception {
		// tag::setup-DeleteGreetingText[]
		messenger.deleteSettings(MessengerSettingProperty.GREETING);
		// end::setup-DeleteGreetingText[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"fields\": [\n" + "    \"greeting\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupPersistentMenu() throws Exception {
		// tag::setup-PersistentMenu[]
		final PostbackCallToAction callToActionAA = PostbackCallToAction.create("Pay Bill", "PAYBILL_PAYLOAD");
		final PostbackCallToAction callToActionAB = PostbackCallToAction.create("History", "HISTORY_PAYLOAD");
		final PostbackCallToAction callToActionAC = PostbackCallToAction.create("Contact Info", "CONTACT_INFO_PAYLOAD");

		final NestedCallToAction callToActionA = NestedCallToAction.create("My Account", Arrays.asList(callToActionAA, callToActionAB, callToActionAC));

		final UrlCallToAction callToActionB = UrlCallToAction
				.create("Latest News", new URL("http://petershats.parseapp.com/hat-news"), of(WebviewHeightRatio.FULL), empty(), empty(),
						of(WebviewShareButtonState.HIDE));

		final PersistentMenu persistentMenu = PersistentMenu
				.create(true, of(Arrays.asList(callToActionA, callToActionB)), LocalizedPersistentMenu.create(SupportedLocale.zh_CN, false, empty()));

		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), empty(), of(persistentMenu), empty(), empty(), empty(), empty());

		messenger.updateSettings(messengerSettings);
		// end::setup-PersistentMenu[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody =
				"{\n" + "  \"persistent_menu\":[\n" + "    {\n" + "      \"locale\":\"default\",\n" + "      \"composer_input_disabled\": true,\n"
						+ "      \"call_to_actions\":[\n" + "        {\n" + "          \"title\":\"My Account\",\n" + "          \"type\":\"nested\",\n"
						+ "          \"call_to_actions\":[\n" + "            {\n" + "              \"title\":\"Pay Bill\",\n"
						+ "              \"type\":\"postback\",\n" + "              \"payload\":\"PAYBILL_PAYLOAD\"\n" + "            },\n" + "            {\n"
						+ "              \"title\":\"History\",\n" + "              \"type\":\"postback\",\n"
						+ "              \"payload\":\"HISTORY_PAYLOAD\"\n" + "            },\n" + "            {\n"
						+ "              \"title\":\"Contact Info\",\n" + "              \"type\":\"postback\",\n"
						+ "              \"payload\":\"CONTACT_INFO_PAYLOAD\"\n" + "            }\n" + "          ]\n" + "        },\n" + "        {\n"
						+ "          \"type\":\"web_url\",\n" + "          \"title\":\"Latest News\",\n"
						+ "          \"url\":\"http://petershats.parseapp.com/hat-news\",\n" + "          \"webview_share_button\":\"hide\",\n"
						+ "          \"webview_height_ratio\":\"full\"\n" + "        }\n" + "      ]\n" + "    },\n" + "    {\n"
						+ "      \"locale\":\"zh_CN\",\n" + "      \"composer_input_disabled\":false\n" + "    }\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldDeletePersistentMenu() throws Exception {
		// tag::setup-DeletePersistentMenu[]
		messenger.deleteSettings(MessengerSettingProperty.PERSISTENT_MENU);
		// end::setup-DeletePersistentMenu[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"fields\": [\n" + "    \"persistent_menu\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupWhitelistedDomains() throws Exception {
		// tag::setup-WhitelistedDomains[]
		final List<URL> whitelistedDomains = Arrays.asList(new URL("http://example.url"), new URL("http://second-example.url"));

		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), empty(), empty(), of(whitelistedDomains), empty(), empty(), empty());

		messenger.updateSettings(messengerSettings);
		// end::setup-WhitelistedDomains[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody =
				"{\n" + "  \"whitelisted_domains\":[\n" + "    \"http://example.url\",\n" + "    \"http://second-example.url\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldDeleteWhitelistedDomains() throws Exception {
		// tag::setup-DeleteWhitelistedDomains[]
		messenger.deleteSettings(MessengerSettingProperty.WHITELISTED_DOMAINS);
		// end::setup-DeleteWhitelistedDomains[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"fields\": [\n" + "    \"whitelisted_domains\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupAccountLinkingUrl() throws Exception {
		// tag::setup-AccountLinkingUrl[]
		final MessengerSettings messengerSettings = MessengerSettings
				.create(empty(), empty(), empty(), empty(), of(new URL("http://example.url")), empty(), empty());

		messenger.updateSettings(messengerSettings);
		// end::setup-AccountLinkingUrl[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"account_linking_url\":\"http://example.url\"\n" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldDeleteAccountLinkingUrl() throws Exception {
		// tag::setup-DeleteAccountLinkingUrl[]
		messenger.deleteSettings(MessengerSettingProperty.ACCOUNT_LINKING_URL);
		// end::setup-DeleteAccountLinkingUrl[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"fields\": [\n" + "    \"account_linking_url\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupHomeUrl() throws Exception {
		// tag::setup-HomeUrl[]
		final HomeUrl homeUrl = HomeUrl.create(new URL("http://example.url"), true, of(WebviewShareButtonState.HIDE));

		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), empty(), empty(), empty(), empty(), of(homeUrl), empty());

		messenger.updateSettings(messengerSettings);
		// end::setup-HomeUrl[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody =
				"{\n" + "  \"home_url\" : {\n" + "     \"url\": \"http://example.url\",\n" + "     \"webview_height_ratio\": \"tall\",\n"
						+ "     \"webview_share_button\": \"hide\",\n" + "     \"in_test\":true\n" + "  }\n" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldDeleteHomeUrl() throws Exception {
		// tag::setup-DeleteHomeUrl[]
		messenger.deleteSettings(MessengerSettingProperty.HOME_URL);
		// end::setup-DeleteHomeUrl[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"fields\": [\n" + "    \"home_url\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupTargetAudienceCustomWhitelist() throws Exception {
		// tag::setup-TargetAudienceCustomWhitelist[]
		final WhitelistTargetAudience whitelistTargetAudience = WhitelistTargetAudience.create(Arrays.asList(SupportedCountry.US, SupportedCountry.CA));

		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), empty(), empty(), empty(), empty(), empty(), of(whitelistTargetAudience));

		messenger.updateSettings(messengerSettings);
		// end::setup-TargetAudienceCustomWhitelist[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "   \"target_audience\":{\n" + "     \"audience_type\":\"custom\",\n" + "     \"countries\":{\n"
				+ "       \"whitelist\":[\"US\", \"CA\"]\n" + "     }\n" + "   }" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupTargetAudienceCustomBlacklist() throws Exception {
		// tag::setup-TargetAudienceCustomBlacklist[]
		final BlacklistTargetAudience blacklistTargetAudience = BlacklistTargetAudience.create(Arrays.asList(SupportedCountry.US, SupportedCountry.CA));

		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), empty(), empty(), empty(), empty(), empty(), of(blacklistTargetAudience));

		messenger.updateSettings(messengerSettings);
		// end::setup-TargetAudienceCustomBlacklist[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "   \"target_audience\":{\n" + "     \"audience_type\":\"custom\",\n" + "     \"countries\":{\n"
				+ "       \"blacklist\":[\"US\", \"CA\"]\n" + "     }\n" + "   }" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupTargetAudienceAll() throws Exception {
		// tag::setup-TargetAudienceAll[]
		final AllTargetAudience allTargetAudience = AllTargetAudience.create();

		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), empty(), empty(), empty(), empty(), empty(), of(allTargetAudience));

		messenger.updateSettings(messengerSettings);
		// end::setup-TargetAudienceAll[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "   \"target_audience\":{\n" + "     \"audience_type\":\"all\"" + "   }" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldSetupTargetAudienceNone() throws Exception {
		// tag::setup-TargetAudienceNone[]
		final NoneTargetAudience noneTargetAudience = NoneTargetAudience.create();

		final MessengerSettings messengerSettings = MessengerSettings.create(empty(), empty(), empty(), empty(), empty(), empty(), of(noneTargetAudience));

		messenger.updateSettings(messengerSettings);
		// end::setup-TargetAudienceNone[]

		//then
		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "   \"target_audience\":{\n" + "     \"audience_type\":\"none\"" + "   }" + "}";
		verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldDeleteTargetAudience() throws Exception {
		// tag::setup-DeleteTargetAudience[]
		messenger.deleteSettings(MessengerSettingProperty.TARGET_AUDIENCE);
		// end::setup-DeleteTargetAudience[]

		final ArgumentCaptor<String> payloadCaptor = ArgumentCaptor.forClass(String.class);
		final String expectedJsonBody = "{\n" + "  \"fields\": [\n" + "    \"target_audience\"\n" + "  ]\n" + "}";
		verify(mockHttpClient).execute(eq(DELETE), endsWith(PAGE_ACCESS_TOKEN), payloadCaptor.capture());
		JSONAssert.assertEquals(expectedJsonBody, payloadCaptor.getValue(), true);
	}

	@Test
	public void shouldHandleUpdateSuccessResponse() throws Exception {
		final HttpResponse successfulResponse = new HttpResponse(200, "{\"result\": \"success\"}");
		when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(successfulResponse);

		final MessengerSettings messengerSettings = MessengerSettings
				.create(of(StartButton.create("test")), empty(), empty(), empty(), empty(), empty(), empty());
		final SetupResponse setupResponse = messenger.updateSettings(messengerSettings);

		assertThat(setupResponse, is(notNullValue()));
		assertThat(setupResponse.result(), is(equalTo("success")));
	}

	@Test
	public void shouldHandleUpdateErrorResponse() throws Exception {
		final HttpResponse errorResponse = new HttpResponse(401,
				"{\n" + "  \"error\": {\n" + "    \"message\": \"Invalid OAuth access token.\",\n" + "    \"type\": \"OAuthException\",\n"
						+ "    \"code\": 190,\n" + "    \"fbtrace_id\": \"BLBz/WZt8dN\"\n" + "  }\n" + "}");
		when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(errorResponse);

		MessengerApiException messengerApiException = null;
		try {
			final MessengerSettings messengerSettings = MessengerSettings
					.create(of(StartButton.create("test")), empty(), empty(), empty(), empty(), empty(), empty());
			messenger.updateSettings(messengerSettings);
		} catch (MessengerApiException e) {
			messengerApiException = e;
		}

		assertThat(messengerApiException, is(notNullValue()));
		assertThat(messengerApiException.message(), is(equalTo("Invalid OAuth access token.")));
		assertThat(messengerApiException.type(), is(equalTo(of("OAuthException"))));
		assertThat(messengerApiException.code(), is(equalTo(of(190))));
		assertThat(messengerApiException.fbTraceId(), is(equalTo(of("BLBz/WZt8dN"))));
	}

	@Test
	public void shouldHandleDeleteSuccessResponse() throws Exception {
		final HttpResponse successfulResponse = new HttpResponse(200, "{\"result\": \"success\"}");
		when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(successfulResponse);

		final SetupResponse setupResponse = messenger.deleteSettings(MessengerSettingProperty.GREETING);

		assertThat(setupResponse, is(notNullValue()));
		assertThat(setupResponse.result(), is(equalTo("success")));
	}

	@Test
	public void shouldHandleDeleteErrorResponse() throws Exception {
		final HttpResponse errorResponse = new HttpResponse(401,
				"{\n" + "  \"error\": {\n" + "    \"message\": \"Invalid OAuth access token.\",\n" + "    \"type\": \"OAuthException\",\n"
						+ "    \"code\": 190,\n" + "    \"fbtrace_id\": \"BLBz/WZt8dN\"\n" + "  }\n" + "}");
		when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(errorResponse);

		MessengerApiException messengerApiException = null;
		try {
			messenger.deleteSettings(MessengerSettingProperty.GREETING);
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
