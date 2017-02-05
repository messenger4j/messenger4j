package com.github.messenger4j.test.integration.send;

import static com.github.messenger4j.common.MessengerHttpClient.HttpMethod.POST;
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

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.common.MessengerHttpClient.HttpMethod;
import com.github.messenger4j.common.MessengerHttpClient.HttpResponse;
import com.github.messenger4j.common.WebviewHeightRatio;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.send.BinaryAttachment;
import com.github.messenger4j.send.MessengerResponse;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.QuickReply;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import com.github.messenger4j.send.buttons.Button;
import com.github.messenger4j.send.templates.ButtonTemplate;
import com.github.messenger4j.send.templates.GenericTemplate;
import com.github.messenger4j.send.templates.ListTemplate;
import com.github.messenger4j.send.templates.ReceiptTemplate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class MessengerSendClientTest {

    private static final String PAGE_ACCESS_TOKEN = "PAGE_ACCESS_TOKEN";

    private MessengerSendClient messengerSendClient;
    private MessengerHttpClient mockHttpClient = mock(MessengerHttpClient.class);

    private final HttpResponse fakeResponse = new HttpResponse(200, "{\n" +
            "  \"recipient_id\": \"USER_ID\",\n" +
            "  \"message_id\": \"mid.1473372944816:94f72b88c597657974\"\n" +
            "}");

    @Before
    public void beforeEach() throws Exception {
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(fakeResponse);
        messengerSendClient = MessengerPlatform.newSendClientBuilder(PAGE_ACCESS_TOKEN)
                .httpClient(mockHttpClient)
                .build();
    }

    @Test
    public void shouldSendSenderAction() throws Exception {
        //given
        final String recipientId = "USER_ID";
        final SenderAction senderAction = SenderAction.MARK_SEEN;

        //when
        messengerSendClient.sendSenderAction(recipientId, senderAction);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},"
                + "\"sender_action\":\"mark_seen\"}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendTextMessage() throws Exception {
        //given
        final String recipientId = "USER_ID";
        final String text = "Hello Messenger Platform";

        //when
        messengerSendClient.sendTextMessage(recipientId, text);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},"
                + "\"message\":{\"text\":\"Hello Messenger Platform\"}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendTextMessageWithQuickReplies() throws Exception {
        //given
        final Recipient recipient = Recipient.newBuilder().recipientId("USER_ID").build();
        final NotificationType notificationType = NotificationType.SILENT_PUSH;
        final String text = "Pick a color:";
        final List<QuickReply> quickReplies = QuickReply.newListBuilder()
                .addTextQuickReply("Red", "PAYLOAD_FOR_PICKING_RED").toList()
                .addTextQuickReply("Green", "PAYLOAD_FOR_PICKING_GREEN").toList()
                .build();

        //when
        messengerSendClient.sendTextMessage(recipient, notificationType, text, quickReplies);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},"
                + "\"notification_type\":\"SILENT_PUSH\","
                + "\"message\":{\"text\":\"Pick a color:\","
                + "\"quick_replies\":["
                + "{\"content_type\":\"text\",\"title\":\"Red\",\"payload\":\"PAYLOAD_FOR_PICKING_RED\"},"
                + "{\"content_type\":\"text\",\"title\":\"Green\",\"payload\":\"PAYLOAD_FOR_PICKING_GREEN\"}"
                + "]}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendTextMessageWithMetadata() throws Exception {
        //given
        final Recipient recipient = Recipient.newBuilder().recipientId("USER_ID").build();
        final NotificationType notificationType = NotificationType.SILENT_PUSH;
        final String text = "Hello Messenger Platform";
        final String metadata = "DEVELOPER_DEFINED_METADATA";

        //when
        messengerSendClient.sendTextMessage(recipient, notificationType, text, metadata);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},"
                + "\"notification_type\":\"SILENT_PUSH\","
                + "\"message\":{"
                + "\"text\":\"Hello Messenger Platform\","
                + "\"metadata\":\"DEVELOPER_DEFINED_METADATA\""
                + "}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendImageAttachmentMessageWithUrl() throws Exception {
        //given
        final String recipientId = "USER_ID";
        final String imageUrl = "https://petersapparel.com/img/shirt.png";

        //when
        messengerSendClient.sendImageAttachment(recipientId, imageUrl);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},"
                + "\"message\":{\"attachment\":{"
                + "\"type\":\"image\","
                + "\"payload\":{\"url\":\"https://petersapparel.com/img/shirt.png\"}"
                + "}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendReusableImageAttachmentMessageWithUrl() throws Exception {
        //given
        final Recipient recipient = Recipient.newBuilder().recipientId("USER_ID").build();
        final NotificationType notificationType = NotificationType.NO_PUSH;
        final BinaryAttachment reusableImageAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.IMAGE)
                .url("https://petersapparel.com/img/shirt.png")
                .isReusable(true)
                .build();

        //when
        messengerSendClient.sendBinaryAttachment(recipient, notificationType, reusableImageAttachment);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},"
                + "\"notification_type\":\"NO_PUSH\","
                + "\"message\":{\"attachment\":{"
                + "\"type\":\"image\","
                + "\"payload\":{\"url\":\"https://petersapparel.com/img/shirt.png\",\"is_reusable\":true}"
                + "}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendImageAttachmentMessageWithAttachmentId() throws Exception {
        //given
        final Recipient recipient = Recipient.newBuilder().recipientId("USER_ID").build();
        final NotificationType notificationType = NotificationType.NO_PUSH;
        final BinaryAttachment imageAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.IMAGE)
                .attachmentId("1745504518999123")
                .build();

        //when
        messengerSendClient.sendBinaryAttachment(recipient, notificationType, imageAttachment);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},"
                + "\"notification_type\":\"NO_PUSH\","
                + "\"message\":{\"attachment\":{"
                + "\"type\":\"image\","
                + "\"payload\":{\"attachment_id\":\"1745504518999123\"}"
                + "}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSendingVideoAttachmentMessageWithQuickReplies() throws Exception {
        //given
        final Recipient recipient = Recipient.newBuilder().recipientId("USER_ID").build();
        final NotificationType notificationType = NotificationType.REGULAR;
        final BinaryAttachment videoAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.VIDEO)
                .url("https://www.example-video-url.com")
                .build();
        final List<QuickReply> quickReplies = QuickReply.newListBuilder()
                .addTextQuickReply("Red", "PAYLOAD_FOR_PICKING_RED").toList()
                .addLocationQuickReply().toList()
                .build();

        //when
        messengerSendClient.sendBinaryAttachment(recipient, notificationType, videoAttachment, quickReplies);

        //then - throw exception
    }

    @Test
    public void shouldSendButtonTemplateMessage() throws Exception {
        //given
        final String recipientId = "USER_ID";

        final List<Button> buttons = Button.newListBuilder()
                .addUrlButton("Show Website", "https://petersapparel.parseapp.com").toList()
                .addPostbackButton("Start Chatting", "USER_DEFINED_PAYLOAD").toList()
                .addUrlButton("Show Website", "https://petersapparel.parseapp.com")
                    .webviewHeightRatio(WebviewHeightRatio.FULL)
                    .messengerExtensions(true)
                    .fallbackUrl("https://petersfancyapparel.com/fallback")
                    .toList()
                .build();

        final ButtonTemplate buttonTemplate = ButtonTemplate.newBuilder("What do you want to do next?", buttons).build();

        //when
        messengerSendClient.sendTemplate(recipientId, buttonTemplate);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"}," +
                "\"message\":{\"attachment\":{\"type\":\"template\",\"payload\":{\"text\":\"What do you want to do next?\"" +
                ",\"buttons\":[{\"url\":\"https://petersapparel.parseapp.com\",\"title\":\"Show Website\"" +
                ",\"type\":\"web_url\"},{\"payload\":\"USER_DEFINED_PAYLOAD\",\"title\":\"Start Chatting\"" +
                ",\"type\":\"postback\"},{\"url\":\"https://petersapparel.parseapp.com\",\"webview_height_ratio\":\"full\"" +
                ",\"messenger_extensions\":true,\"fallback_url\":\"https://petersfancyapparel.com/fallback\"" +
                ",\"title\":\"Show Website\",\"type\":\"web_url\"}],\"template_type\":\"button\"}}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendGenericTemplateWithUrlAndPostbackButtonsMessage() throws Exception {
        //given
        final String recipientId = "USER_ID";

        final List<Button> buttons = Button.newListBuilder()
                .addUrlButton("View Website", "https://petersfancybrownhats.com").toList()
                .addPostbackButton("Start Chatting", "DEVELOPER_DEFINED_PAYLOAD").toList()
                .build();

        final GenericTemplate genericTemplate = GenericTemplate.newBuilder()
                .addElements()
                .addElement("Welcome to Peters Hats")
                .itemUrl("https://petersfancybrownhats.com")
                .imageUrl("https://petersfancybrownhats.com/company_image.png")
                .subtitle("We have got the right hat for everyone.")
                .buttons(buttons)
                .toList()
                .done()
                .build();

        //when
        messengerSendClient.sendTemplate(recipientId, genericTemplate);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"}," +
                "\"message\":{\"attachment\":{\"type\":\"template\",\"payload\":{\"elements\":[{" +
                "\"title\":\"Welcome to Peters Hats\",\"item_url\":\"https://petersfancybrownhats.com\"," +
                "\"image_url\":\"https://petersfancybrownhats.com/company_image.png\"," +
                "\"subtitle\":\"We have got the right hat for everyone.\",\"buttons\":[{" +
                "\"url\":\"https://petersfancybrownhats.com\",\"title\":\"View Website\",\"type\":\"web_url\"}," +
                "{\"payload\":\"DEVELOPER_DEFINED_PAYLOAD\",\"title\":\"Start Chatting\",\"type\":\"postback\"}]}]," +
                "\"template_type\":\"generic\"}}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendGenericTemplateWithLogInAndLogOutButtonsMessage() throws Exception {
        //given
        final String recipientId = "USER_ID";

        final List<Button> buttons = Button.newListBuilder()
                .addLogInButton("https://www.example.com/authorize").toList()
                .addLogOutButton().toList()
                .build();

        final GenericTemplate genericTemplate = GenericTemplate.newBuilder()
                .addElements()
                .addElement("Welcome to M-Bank")
                .imageUrl("http://www.example.com/images/m-bank.png")
                .buttons(buttons)
                .toList()
                .done()
                .build();

        //when
        messengerSendClient.sendTemplate(recipientId, genericTemplate);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},\"message\":" +
                "{\"attachment\":{\"type\":\"template\",\"payload\":{\"elements\":" +
                "[{\"title\":\"Welcome to M-Bank\",\"image_url\":\"http://www.example.com/images/m-bank.png\"," +
                "\"buttons\":[{\"url\":\"https://www.example.com/authorize\",\"type\":\"account_link\"}," +
                "{\"type\":\"account_unlink\"}]}],\"template_type\":\"generic\"}}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendReceiptTemplateMessage() throws Exception {
        //given
        final String recipientId = "USER_ID";

        final ReceiptTemplate receiptTemplate = ReceiptTemplate.newBuilder("Stephane Crozatier", "12345678902", "USD",
                "Visa 2345")
                .orderUrl("http://petersapparel.parseapp.com/order?order_id=123456")
                .timestamp(1428444852L)
                .addElements()
                .addElement("Classic White T-Shirt", 50F)
                .subtitle("100% Soft and Luxurious Cotton")
                .quantity(2)
                .currency("USD")
                .imageUrl("http://petersapparel.parseapp.com/img/whiteshirt.png")
                .toList()
                .addElement("Classic Gray T-Shirt", 25F)
                .subtitle("100% Soft and Luxurious Cotton")
                .quantity(1)
                .currency("USD")
                .imageUrl("http://petersapparel.parseapp.com/img/grayshirt.png")
                .toList()
                .done()
                .addAddress("1 Hacker Way", "Menlo Park", "94025", "CA", "US").street2("").done()
                .addSummary(56.14F).subtotal(75.00F).shippingCost(4.95F).totalTax(6.19F).done()
                .addAdjustments()
                .addAdjustment()
                .name("New Customer Discount")
                .amount(20.00F)
                .toList()
                .addAdjustment()
                .name("$10 Off Coupon")
                .amount(10.00F)
                .toList()
                .done()
                .build();

        //when
        messengerSendClient.sendTemplate(recipientId, receiptTemplate);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"}," +
                "\"message\":{\"attachment\":{\"type\":\"template\",\"payload\":{\"recipient_name\":\"Stephane Crozatier\"," +
                "\"order_number\":\"12345678902\",\"currency\":\"USD\",\"payment_method\":\"Visa 2345\"," +
                "\"timestamp\":\"1428444852\",\"order_url\":\"http://petersapparel.parseapp.com/order?order_id\\u003d123456\"," +
                "\"elements\":[{\"title\":\"Classic White T-Shirt\",\"subtitle\":\"100% Soft and Luxurious Cotton\"," +
                "\"quantity\":2,\"price\":50.00,\"currency\":\"USD\"," +
                "\"image_url\":\"http://petersapparel.parseapp.com/img/whiteshirt.png\"},{\"title\":\"Classic Gray T-Shirt\"," +
                "\"subtitle\":\"100% Soft and Luxurious Cotton\",\"quantity\":1,\"price\":25.00,\"currency\":\"USD\"," +
                "\"image_url\":\"http://petersapparel.parseapp.com/img/grayshirt.png\"}]," +
                "\"address\":{\"street_1\":\"1 Hacker Way\",\"street_2\":\"\",\"city\":\"Menlo Park\",\"postal_code\":\"94025\"," +
                "\"state\":\"CA\",\"country\":\"US\"},\"summary\":{\"total_cost\":56.14,\"total_tax\":6.19," +
                "\"shipping_cost\":4.95,\"subtotal\":75.00},\"adjustments\":[{\"name\":\"New Customer Discount\"," +
                "\"amount\":20.00},{\"name\":\"$10 Off Coupon\",\"amount\":10.00}],\"template_type\":\"receipt\"}}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldSendListTemplateMessage() throws Exception {
        //given
        final String recipientId = "USER_ID";

        final ListTemplate listTemplate = ListTemplate.newBuilder(ListTemplate.TopElementStyle.LARGE)
                .buttons(Button.newListBuilder().addPostbackButton("View More", "payload").toList().build())
                .addElements()
                    .addElement("Classic T-Shirt Collection")
                .subtitle("See all our colors")
                .imageUrl("https://peterssendreceiveapp.ngrok.io/img/collection.png")
                .buttons(Button.newListBuilder()
                        .addUrlButton("View", "https://peterssendreceiveapp.ngrok.io/collection")
                        .webviewHeightRatio(WebviewHeightRatio.TALL)
                        .toList()
                        .build())
                .addDefaultAction("https://peterssendreceiveapp.ngrok.io/shop_collection")
                    .webviewHeightRatio(WebviewHeightRatio.TALL)
                    .messengerExtensions(true)
                    .fallbackUrl("https://peterssendreceiveapp.ngrok.io/fallback")
                    .done()
                .toList()
                    .addElement("Classic White T-Shirt")
                    .subtitle("100% Cotton, 200% Comfortable")
                    .imageUrl("https://peterssendreceiveapp.ngrok.io/img/white-t-shirt.png")
                    .buttons(Button.newListBuilder()
                            .addUrlButton("Shop Now", "https://peterssendreceiveapp.ngrok.io/shop?item=100")
                            .webviewHeightRatio(WebviewHeightRatio.TALL).toList().build())
                .addDefaultAction("https://peterssendreceiveapp.ngrok.io/view?item=100")
                        .webviewHeightRatio(WebviewHeightRatio.TALL)
                .done()
                .toList()
                    .addElement("Classic Blue T-Shirt")
                    .subtitle("100% Cotton, 200% Comfortable")
                    .imageUrl("https://peterssendreceiveapp.ngrok.io/img/blue-t-shirt.png")
                    .buttons(Button.newListBuilder()
                            .addUrlButton("Shop Now", "https://peterssendreceiveapp.ngrok.io/shop?item=101")
                            .webviewHeightRatio(WebviewHeightRatio.TALL).toList().build())
                .addDefaultAction("https://peterssendreceiveapp.ngrok.io/view?item=101")
                        .webviewHeightRatio(WebviewHeightRatio.TALL)
                .done()
                .toList()
                    .addElement("Classic Black T-Shirt")
                    .subtitle("100% Cotton, 200% Comfortable")
                    .imageUrl("https://peterssendreceiveapp.ngrok.io/img/black-t-shirt.png")
                    .buttons(Button.newListBuilder()
                            .addUrlButton("Shop Now", "https://peterssendreceiveapp.ngrok.io/shop?item=102")
                            .webviewHeightRatio(WebviewHeightRatio.TALL).toList().build())
                .addDefaultAction("https://peterssendreceiveapp.ngrok.io/view?item=102")
                        .webviewHeightRatio(WebviewHeightRatio.TALL)
                .done()
                .toList()
                .done()
                .build();

        //when
        messengerSendClient.sendTemplate(recipientId, listTemplate);

        //then
        final String expectedJsonBody = "{\"recipient\":{\"id\":\"USER_ID\"},\"message\":{\"attachment\":{\"type\":\"template\"," +
                "\"payload\":{\"top_element_style\":\"large\",\"buttons\":[{\"payload\":\"payload\",\"title\":\"View More\"," +
                "\"type\":\"postback\"}],\"elements\":[{\"title\":\"Classic T-Shirt Collection\"," +
                "\"subtitle\":\"See all our colors\",\"image_url\":\"https://peterssendreceiveapp.ngrok.io/img/collection.png\"," +
                "\"buttons\":[{\"url\":\"https://peterssendreceiveapp.ngrok.io/collection\",\"webview_height_ratio\":\"tall\"," +
                "\"title\":\"View\",\"type\":\"web_url\"}],\"default_action\":{\"type\":\"web_url\"," +
                "\"url\":\"https://peterssendreceiveapp.ngrok.io/shop_collection\",\"webview_height_ratio\":\"tall\"," +
                "\"messenger_extensions\":true,\"fallback_url\":\"https://peterssendreceiveapp.ngrok.io/fallback\"}}," +
                "{\"title\":\"Classic White T-Shirt\",\"subtitle\":\"100% Cotton, 200% Comfortable\"," +
                "\"image_url\":\"https://peterssendreceiveapp.ngrok.io/img/white-t-shirt.png\"," +
                "\"buttons\":[{\"url\":\"https://peterssendreceiveapp.ngrok.io/shop?item\\u003d100\",\"webview_height_ratio\":\"tall\"," +
                "\"title\":\"Shop Now\",\"type\":\"web_url\"}],\"default_action\":{\"type\":\"web_url\"," +
                "\"url\":\"https://peterssendreceiveapp.ngrok.io/view?item\\u003d100\",\"webview_height_ratio\":\"tall\"}}," +
                "{\"title\":\"Classic Blue T-Shirt\",\"subtitle\":\"100% Cotton, 200% Comfortable\"," +
                "\"image_url\":\"https://peterssendreceiveapp.ngrok.io/img/blue-t-shirt.png\"," +
                "\"buttons\":[{\"url\":\"https://peterssendreceiveapp.ngrok.io/shop?item\\u003d101\",\"webview_height_ratio\":\"tall\"," +
                "\"title\":\"Shop Now\",\"type\":\"web_url\"}],\"default_action\":{\"type\":\"web_url\"," +
                "\"url\":\"https://peterssendreceiveapp.ngrok.io/view?item\\u003d101\",\"webview_height_ratio\":\"tall\"}}," +
                "{\"title\":\"Classic Black T-Shirt\",\"subtitle\":\"100% Cotton, 200% Comfortable\"," +
                "\"image_url\":\"https://peterssendreceiveapp.ngrok.io/img/black-t-shirt.png\"," +
                "\"buttons\":[{\"url\":\"https://peterssendreceiveapp.ngrok.io/shop?item\\u003d102\",\"webview_height_ratio\":\"tall\"," +
                "\"title\":\"Shop Now\",\"type\":\"web_url\"}],\"default_action\":{\"type\":\"web_url\"," +
                "\"url\":\"https://peterssendreceiveapp.ngrok.io/view?item\\u003d102\",\"webview_height_ratio\":\"tall\"}}]," +
                "\"template_type\":\"list\"}}}}";
        verify(mockHttpClient).execute(eq(POST), endsWith(PAGE_ACCESS_TOKEN), eq(expectedJsonBody));
    }

    @Test
    public void shouldHandleSuccessResponse() throws Exception {
        //given
        final HttpResponse successfulResponse = new HttpResponse(200, "{\n" +
                "  \"recipient_id\": \"USER_ID\",\n" +
                "  \"message_id\": \"mid.1473372944816:94f72b88c597657974\",\n" +
                "  \"attachment_id\": \"1745504518999123\"\n" +
                "}");
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(successfulResponse);

        //when
        final MessengerResponse messengerResponse = messengerSendClient.sendTextMessage("recipient id", "text");

        //then
        assertThat(messengerResponse, is(notNullValue()));
        assertThat(messengerResponse.getRecipientId(), is(equalTo("USER_ID")));
        assertThat(messengerResponse.getMessageId(), is(equalTo("mid.1473372944816:94f72b88c597657974")));
        assertThat(messengerResponse.getAttachmentId(), is(equalTo("1745504518999123")));
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
        when(mockHttpClient.execute(any(HttpMethod.class), anyString(), anyString())).thenReturn(errorResponse);

        //when
        MessengerApiException messengerApiException = null;
        try {
            messengerSendClient.sendTextMessage("recipient id", "text");
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