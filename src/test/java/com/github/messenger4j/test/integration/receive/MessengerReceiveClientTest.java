package com.github.messenger4j.test.integration.receive;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.receive.MessengerReceiveClientBuilder;
import com.github.messenger4j.receive.events.AccountLinkingEvent;
import com.github.messenger4j.receive.events.AttachmentMessageEvent;
import com.github.messenger4j.receive.events.EchoMessageEvent;
import com.github.messenger4j.receive.events.FallbackEvent;
import com.github.messenger4j.receive.events.MessageDeliveredEvent;
import com.github.messenger4j.receive.events.MessageReadEvent;
import com.github.messenger4j.receive.events.OptInEvent;
import com.github.messenger4j.receive.events.PostbackEvent;
import com.github.messenger4j.receive.events.QuickReplyMessageEvent;
import com.github.messenger4j.receive.events.TextMessageEvent;
import com.github.messenger4j.receive.handlers.AccountLinkingEventHandler;
import com.github.messenger4j.receive.handlers.AttachmentMessageEventHandler;
import com.github.messenger4j.receive.handlers.EchoMessageEventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.github.messenger4j.receive.handlers.MessageDeliveredEventHandler;
import com.github.messenger4j.receive.handlers.MessageReadEventHandler;
import com.github.messenger4j.receive.handlers.OptInEventHandler;
import com.github.messenger4j.receive.handlers.PostbackEventHandler;
import com.github.messenger4j.receive.handlers.QuickReplyMessageEventHandler;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class MessengerReceiveClientTest {

    private MessengerReceiveClientBuilder builder;
    private AttachmentMessageEventHandler mockAttachmentMessageEventHandler = mock(AttachmentMessageEventHandler.class);
    private OptInEventHandler mockOptInEventHandler = mock(OptInEventHandler.class);
    private EchoMessageEventHandler mockEchoMessageEventHandler = mock(EchoMessageEventHandler.class);
    private QuickReplyMessageEventHandler mockQuickReplyMessageEventHandler = mock(QuickReplyMessageEventHandler.class);
    private TextMessageEventHandler mockTextMessageEventHandler = mock(TextMessageEventHandler.class);
    private PostbackEventHandler mockPostbackEventHandler = mock(PostbackEventHandler.class);
    private AccountLinkingEventHandler mockAccountLinkingEventHandler = mock(AccountLinkingEventHandler.class);
    private MessageReadEventHandler mockMessageReadEventHandler = mock(MessageReadEventHandler.class);
    private MessageDeliveredEventHandler mockMessageDeliveredEventHandler = mock(MessageDeliveredEventHandler.class);
    private FallbackEventHandler mockFallbackEventHandler = mock(FallbackEventHandler.class);

    @Before
    public void beforeEach() {
        builder = MessengerPlatform.newReceiveClientBuilder("60efff025951cddde78c8d03de52cc90", "CUSTOM_VERIFY_TOKEN")
                .onAttachmentMessageEvent(mockAttachmentMessageEventHandler)
                .onOptInEvent(mockOptInEventHandler)
                .onEchoMessageEvent(mockEchoMessageEventHandler)
                .onQuickReplyMessageEvent(mockQuickReplyMessageEventHandler)
                .onTextMessageEvent(mockTextMessageEventHandler)
                .onPostbackEvent(mockPostbackEventHandler)
                .onAccountLinkingEvent(mockAccountLinkingEventHandler)
                .onMessageReadEvent(mockMessageReadEventHandler)
                .onMessageDeliveredEvent(mockMessageDeliveredEventHandler)
                .fallbackEventHandler(mockFallbackEventHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfObjectTypeIsNotPage() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"testValue\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"message\": {\n" +
                "                \"mid\": \"mid.1457764197618:41d102a3e1ae206a38\",\n" +
                "                \"text\": \"hello, world!\",\n" +
                "                \"quick_reply\": {\n" +
                "                    \"payload\": \"DEVELOPER_DEFINED_PAYLOAD\"\n" +
                "                }\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then - throw exception
    }

    @Test
    public void shouldHandleAttachmentMessageEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"message\": {\n" +
                "                \"mid\": \"mid.1458696618141:b4ef9d19ec21086067\",\n" +
                "                \"attachments\": [{\n" +
                "                    \"type\": \"image\",\n" +
                "                    \"payload\": {\n" +
                "                        \"url\": \"IMAGE_URL\"\n" +
                "                    }\n" +
                "                }, {\n" +
                "                    \"type\": \"location\",\n" +
                "                    \"payload\": {\n" +
                "                        \"coordinates\": {\n" +
                "                            \"lat\": 52.3765533,\n" +
                "                            \"long\": 9.7389123\n" +
                "                        }\n" +
                "                    }\n" +
                "                }]\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<AttachmentMessageEvent> argument = ArgumentCaptor.forClass(AttachmentMessageEvent.class);
        verify(mockAttachmentMessageEventHandler).handle(argument.capture());
        final AttachmentMessageEvent attachmentMessageEvent = argument.getValue();

        assertThat(attachmentMessageEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(attachmentMessageEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(attachmentMessageEvent.getTimestamp(), equalTo(new Date(1458692752478L)));
        assertThat(attachmentMessageEvent.getMid(), equalTo("mid.1458696618141:b4ef9d19ec21086067"));
        assertThat(attachmentMessageEvent.getAttachments(), hasSize(2));

        final AttachmentMessageEvent.Attachment firstAttachment = attachmentMessageEvent.getAttachments().get(0);
        assertThat(firstAttachment.getType(), equalTo(AttachmentMessageEvent.AttachmentType.IMAGE));
        assertThat(firstAttachment.getPayload().asBinaryPayload().getUrl(), equalTo("IMAGE_URL"));

        final AttachmentMessageEvent.Attachment secondAttachment = attachmentMessageEvent.getAttachments().get(1);
        assertThat(secondAttachment.getType(), equalTo(AttachmentMessageEvent.AttachmentType.LOCATION));
        assertThat(secondAttachment.getPayload().asLocationPayload().getCoordinates().getLatitude(),
                equalTo(52.3765533));
        assertThat(secondAttachment.getPayload().asLocationPayload().getCoordinates().getLongitude(),
                equalTo(9.7389123));

        verifyZeroInteractions(mockOptInEventHandler, mockEchoMessageEventHandler,
                mockQuickReplyMessageEventHandler, mockTextMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleUnsupportedPayloadAttachmentMessageEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"message\": {\n" +
                "                \"mid\": \"mid.1458696618141:b4ef9d19ec21086067\",\n" +
                "                \"attachments\": [{\n" +
                "                    \"type\": \"image\",\n" +
                "                    \"payload\": {\n" +
                "                        \"UNSUPPORTED_PAYLOAD_TYPE\": \"SOME_DATA\"\n" +
                "                    }\n" +
                "                }, {\n" +
                "                    \"type\": \"location\",\n" +
                "                    \"payload\": {\n" +
                "                        \"coordinates\": {\n" +
                "                            \"lat\": 52.3765533,\n" +
                "                            \"long\": 9.7389123\n" +
                "                        }\n" +
                "                    }\n" +
                "                }]\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<AttachmentMessageEvent> argument = ArgumentCaptor.forClass(AttachmentMessageEvent.class);
        verify(mockAttachmentMessageEventHandler).handle(argument.capture());
        final AttachmentMessageEvent attachmentMessageEvent = argument.getValue();

        assertThat(attachmentMessageEvent.getAttachments(), hasSize(2));
        final AttachmentMessageEvent.Attachment firstAttachment = attachmentMessageEvent.getAttachments().get(0);
        assertThat(firstAttachment.getPayload().isUnsupportedPayload(), is(true));

        verifyZeroInteractions(mockOptInEventHandler, mockEchoMessageEventHandler,
                mockQuickReplyMessageEventHandler, mockTextMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleOptInEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1234567890,\n" +
                "            \"optin\": {\n" +
                "                \"ref\": \"PASS_THROUGH_PARAM\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<OptInEvent> argument = ArgumentCaptor.forClass(OptInEvent.class);
        verify(mockOptInEventHandler).handle(argument.capture());
        final OptInEvent optInEvent = argument.getValue();

        assertThat(optInEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(optInEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(optInEvent.getTimestamp(), equalTo(new Date(1234567890L)));
        assertThat(optInEvent.getRef(), equalTo("PASS_THROUGH_PARAM"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockEchoMessageEventHandler,
                mockQuickReplyMessageEventHandler, mockTextMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleTextEchoMessageEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1480114700424,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1480114700296,\n" +
                "            \"message\": {\n" +
                "                \"is_echo\": true,\n" +
                "                \"app_id\": 1517776481860111,\n" +
                "                \"metadata\": \"DEVELOPER_DEFINED_METADATA_STRING\",\n" +
                "                \"mid\": \"mid.1457764197618:41d102a3e1ae206a38\",\n" +
                "                \"seq\": 282,\n" +
                "                \"text\": \"hello, text message world!\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<EchoMessageEvent> argument = ArgumentCaptor.forClass(EchoMessageEvent.class);
        verify(mockEchoMessageEventHandler).handle(argument.capture());
        final EchoMessageEvent echoMessageEvent = argument.getValue();

        assertThat(echoMessageEvent.getSender().getId(), equalTo("PAGE_ID"));
        assertThat(echoMessageEvent.getRecipient().getId(), equalTo("USER_ID"));
        assertThat(echoMessageEvent.getTimestamp(), equalTo(new Date(1480114700296L)));
        assertThat(echoMessageEvent.getAppId(), equalTo("1517776481860111"));
        assertThat(echoMessageEvent.getMetadata(), equalTo("DEVELOPER_DEFINED_METADATA_STRING"));
        assertThat(echoMessageEvent.getMid(), equalTo("mid.1457764197618:41d102a3e1ae206a38"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockQuickReplyMessageEventHandler, mockTextMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleTemplateEchoMessageEvent() throws Exception {
        //given
        final String payload = "{\"object\":\"page\",\"entry\":[{\"id\":\"171999997131834678\",\"time\":1480120722215," +
                "\"messaging\":[{\"sender\":{\"id\":\"17175299999834678\"},\"recipient\":{\"id\":\"1256299999730577\"}," +
                "\"timestamp\":1480120402725,\"message\":{\"is_echo\":true,\"app_id\":1559999994822905," +
                "\"mid\":\"mid.1480199999925:83392d9f65\",\"seq\":294,\"attachments\":[{\"title\":\"Samsung Gear VR, " +
                "Oculus Rift\",\"url\":null,\"type\":\"template\",\"payload\":{\"template_type\":\"receipt\"," +
                "\"recipient_name\":\"Peter Chang\",\"order_number\":\"order-505.0\",\"currency\":\"USD\"," +
                "\"timestamp\":1428444852,\"payment_method\":\"Visa 1234\",\"summary\":{\"total_cost\":626.66," +
                "\"total_tax\":57.67,\"subtotal\":698.99,\"shipping_cost\":20}," +
                "\"address\":{\"city\":\"Menlo Park\",\"country\":\"US\",\"postal_code\":\"94025\",\"state\":\"CA\"," +
                "\"street_1\":\"1 Hacker Way\",\"street_2\":\"\"},\"elements\":[{\"title\":\"Samsung Gear VR\"," +
                "\"quantity\":1,\"image_url\":" +
                "\"https:\\/\\/raw.githubusercontent.com\\/fbsamples\\/messenger-platform-samples\\/master\\/node\\" +
                "/public\\/assets\\/gearvrsq.png\",\"price\":99.99,\"subtitle\":\"Frost White\"},{\"title\":" +
                "\"Oculus Rift\",\"quantity\":1,\"image_url\":\"https:\\/\\/raw.githubusercontent.com\\/fbsamples\\" +
                "/messenger-platform-samples\\/master\\/node\\/public\\/assets\\/riftsq.png\",\"price\":599," +
                "\"subtitle\":\"Includes: headset, sensor, remote\"}],\"adjustments\":[{\"name\":\"New Customer Discount\"," +
                "\"amount\":-50},{\"name\":\"$100 Off Coupon\",\"amount\":-100}]}}]}}]}]}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<EchoMessageEvent> argument = ArgumentCaptor.forClass(EchoMessageEvent.class);
        verify(mockEchoMessageEventHandler).handle(argument.capture());
        final EchoMessageEvent echoMessageEvent = argument.getValue();

        assertThat(echoMessageEvent.getSender().getId(), equalTo("17175299999834678"));
        assertThat(echoMessageEvent.getRecipient().getId(), equalTo("1256299999730577"));
        assertThat(echoMessageEvent.getTimestamp(), equalTo(new Date(1480120402725L)));
        assertThat(echoMessageEvent.getAppId(), equalTo("1559999994822905"));
        assertThat(echoMessageEvent.getMetadata(), is(nullValue()));
        assertThat(echoMessageEvent.getMid(), equalTo("mid.1480199999925:83392d9f65"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockQuickReplyMessageEventHandler, mockTextMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleQuickReplyMessageEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"message\": {\n" +
                "                \"mid\": \"mid.1457764197618:41d102a3e1ae206a38\",\n" +
                "                \"text\": \"hello, world!\",\n" +
                "                \"quick_reply\": {\n" +
                "                    \"payload\": \"DEVELOPER_DEFINED_PAYLOAD\"\n" +
                "                }\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<QuickReplyMessageEvent> argument = ArgumentCaptor.forClass(QuickReplyMessageEvent.class);
        verify(mockQuickReplyMessageEventHandler).handle(argument.capture());
        final QuickReplyMessageEvent quickReplyMessageEvent = argument.getValue();

        assertThat(quickReplyMessageEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(quickReplyMessageEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(quickReplyMessageEvent.getTimestamp(), equalTo(new Date(1458692752478L)));
        assertThat(quickReplyMessageEvent.getMid(), equalTo("mid.1457764197618:41d102a3e1ae206a38"));
        assertThat(quickReplyMessageEvent.getText(), equalTo("hello, world!"));
        assertThat(quickReplyMessageEvent.getQuickReply().getPayload(), equalTo("DEVELOPER_DEFINED_PAYLOAD"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockTextMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleTextMessageEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"message\": {\n" +
                "                \"mid\": \"mid.1457764197618:41d102a3e1ae206a38\",\n" +
                "                \"text\": \"hello, text message world!\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<TextMessageEvent> argument = ArgumentCaptor.forClass(TextMessageEvent.class);
        verify(mockTextMessageEventHandler).handle(argument.capture());
        final TextMessageEvent textMessageEvent = argument.getValue();

        assertThat(textMessageEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(textMessageEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(textMessageEvent.getTimestamp(), equalTo(new Date(1458692752478L)));
        assertThat(textMessageEvent.getMid(), equalTo("mid.1457764197618:41d102a3e1ae206a38"));
        assertThat(textMessageEvent.getText(), equalTo("hello, text message world!"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandlePostbackEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"postback\": {\n" +
                "                \"payload\": \"USER_DEFINED_PAYLOAD\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<PostbackEvent> argument = ArgumentCaptor.forClass(PostbackEvent.class);
        verify(mockPostbackEventHandler).handle(argument.capture());
        final PostbackEvent postbackEvent = argument.getValue();

        assertThat(postbackEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(postbackEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(postbackEvent.getTimestamp(), equalTo(new Date(1458692752478L)));
        assertThat(postbackEvent.getPayload(), equalTo("USER_DEFINED_PAYLOAD"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockTextMessageEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleAccountLinkingEventWithStatusLinked() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1234567890,\n" +
                "            \"account_linking\": {\n" +
                "                \"status\": \"linked\",\n" +
                "                \"authorization_code\": \"PASS_THROUGH_AUTHORIZATION_CODE\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<AccountLinkingEvent> argument = ArgumentCaptor.forClass(AccountLinkingEvent.class);
        verify(mockAccountLinkingEventHandler).handle(argument.capture());
        final AccountLinkingEvent accountLinkingEvent = argument.getValue();

        assertThat(accountLinkingEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(accountLinkingEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(accountLinkingEvent.getTimestamp(), equalTo(new Date(1234567890L)));
        assertThat(accountLinkingEvent.getStatus(), equalTo(AccountLinkingEvent.AccountLinkingStatus.LINKED));
        assertThat(accountLinkingEvent.getAuthorizationCode(), equalTo("PASS_THROUGH_AUTHORIZATION_CODE"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockTextMessageEventHandler,
                mockPostbackEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleAccountLinkingEventWithStatusUnlinked() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1234567890,\n" +
                "            \"account_linking\": {\n" +
                "                \"status\": \"unlinked\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<AccountLinkingEvent> argument = ArgumentCaptor.forClass(AccountLinkingEvent.class);
        verify(mockAccountLinkingEventHandler).handle(argument.capture());
        final AccountLinkingEvent accountLinkingEvent = argument.getValue();

        assertThat(accountLinkingEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(accountLinkingEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(accountLinkingEvent.getTimestamp(), equalTo(new Date(1234567890L)));
        assertThat(accountLinkingEvent.getStatus(), equalTo(AccountLinkingEvent.AccountLinkingStatus.UNLINKED));
        assertThat(accountLinkingEvent.getAuthorizationCode(), nullValue());

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockTextMessageEventHandler,
                mockPostbackEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleMessageReadEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458668856463,\n" +
                "            \"read\": {\n" +
                "                \"watermark\": 1458668856253\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<MessageReadEvent> argument = ArgumentCaptor.forClass(MessageReadEvent.class);
        verify(mockMessageReadEventHandler).handle(argument.capture());
        final MessageReadEvent messageReadEvent = argument.getValue();

        assertThat(messageReadEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(messageReadEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(messageReadEvent.getTimestamp(), equalTo(new Date(1458668856463L)));
        assertThat(messageReadEvent.getWatermark(), equalTo(new Date(1458668856253L)));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockTextMessageEventHandler,
                mockPostbackEventHandler, mockAccountLinkingEventHandler, mockMessageDeliveredEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleMessageDeliveredEventWithMids() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"delivery\": {\n" +
                "                \"mids\": [\n" +
                "                    \"mid.1458668856218:ed81099e15d3f4f233\"\n" +
                "                ],\n" +
                "                \"watermark\": 1458668856253\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<MessageDeliveredEvent> argument = ArgumentCaptor.forClass(MessageDeliveredEvent.class);
        verify(mockMessageDeliveredEventHandler).handle(argument.capture());
        final MessageDeliveredEvent messageDeliveredEvent = argument.getValue();

        assertThat(messageDeliveredEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(messageDeliveredEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(messageDeliveredEvent.getWatermark(), equalTo(new Date(1458668856253L)));
        assertThat(messageDeliveredEvent.getMids(), hasSize(1));
        assertThat(messageDeliveredEvent.getMids().get(0), equalTo("mid.1458668856218:ed81099e15d3f4f233"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockTextMessageEventHandler,
                mockPostbackEventHandler, mockAccountLinkingEventHandler, mockMessageReadEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldHandleMessageDeliveredEventWithoutMids() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"delivery\": {\n" +
                "                \"watermark\": 1458668856253\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<MessageDeliveredEvent> argument = ArgumentCaptor.forClass(MessageDeliveredEvent.class);
        verify(mockMessageDeliveredEventHandler).handle(argument.capture());
        final MessageDeliveredEvent messageDeliveredEvent = argument.getValue();

        assertThat(messageDeliveredEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(messageDeliveredEvent.getRecipient().getId(), equalTo("PAGE_ID"));
        assertThat(messageDeliveredEvent.getWatermark(), equalTo(new Date(1458668856253L)));
        assertThat(messageDeliveredEvent.getMids(), is(emptyCollectionOf(String.class)));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockTextMessageEventHandler,
                mockPostbackEventHandler, mockAccountLinkingEventHandler, mockMessageReadEventHandler,
                mockFallbackEventHandler);
    }

    @Test
    public void shouldCallFallbackEventHandlerIfHandlerForConcreteEventIsNotRegistered() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"message\": {\n" +
                "                \"mid\": \"mid.1457764197618:41d102a3e1ae206a38\",\n" +
                "                \"text\": \"hello, text message world!\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder
                .onTextMessageEvent(null)
                .disableSignatureVerification()
                .build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<FallbackEvent> argument = ArgumentCaptor.forClass(FallbackEvent.class);
        verify(mockFallbackEventHandler).handle(argument.capture());
        final FallbackEvent fallbackEvent = argument.getValue();

        assertThat(fallbackEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(fallbackEvent.getRecipient().getId(), equalTo("PAGE_ID"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockTextMessageEventHandler);
    }

    @Test
    public void shouldCallFallbackEventHandlerIfMessagingEventTypeIsUnsupported() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458692752478,\n" +
                "            \"EVENT_TYPE_THAT_IS_UNSUPPORTED\": {\n" +
                "                \"mid\": \"mid.1457764197618:41d102a3e1ae206a38\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder
                .disableSignatureVerification()
                .build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then
        final ArgumentCaptor<FallbackEvent> argument = ArgumentCaptor.forClass(FallbackEvent.class);
        verify(mockFallbackEventHandler).handle(argument.capture());
        final FallbackEvent fallbackEvent = argument.getValue();

        assertThat(fallbackEvent.getSender().getId(), equalTo("USER_ID"));
        assertThat(fallbackEvent.getRecipient().getId(), equalTo("PAGE_ID"));

        verifyZeroInteractions(mockAttachmentMessageEventHandler, mockOptInEventHandler,
                mockEchoMessageEventHandler, mockQuickReplyMessageEventHandler, mockPostbackEventHandler,
                mockAccountLinkingEventHandler, mockMessageReadEventHandler, mockMessageDeliveredEventHandler,
                mockTextMessageEventHandler);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfNoPayloadProvided() throws Exception {
        //given
        final String payload = null;
        final MessengerReceiveClient messengerReceiveClient = builder.disableSignatureVerification().build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then - throw exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfNoSignatureProvidedAndVerificationNotDisabled() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"sender\": {\n" +
                "                \"id\": \"USER_ID\"\n" +
                "            },\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458668856463,\n" +
                "            \"read\": {\n" +
                "                \"watermark\": 1458668856253,\n" +
                "                \"seq\": 38\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        final MessengerReceiveClient messengerReceiveClient = builder.build();

        //when
        messengerReceiveClient.processCallbackPayload(payload);

        //then - throw exception
    }

    @Test
    public void shouldVerifyTheGivenSignature() throws Exception {
        //given
        final String payload = "{\"object\":\"page\",\"entry\":[{\"id\":\"1717527131834678\",\"time\":1475942721780," +
                "\"messaging\":[{\"sender\":{\"id\":\"1256217357730577\"},\"recipient\":{\"id\":\"1717527131834678\"}," +
                "\"timestamp\":1475942721741,\"message\":{\"mid\":\"mid.1475942721728:3b9e3646712f9bed52\"," +
                "\"seq\":123,\"text\":\"34wrr3wr\"}}]}]}";

        final String signature = "sha1=3daa41999293ff66c3eb313e04bcf77861bb0276";
        final MessengerReceiveClient messengerReceiveClient = builder.build();

        //when
        messengerReceiveClient.processCallbackPayload(payload, signature);

        //then
        final ArgumentCaptor<TextMessageEvent> argument = ArgumentCaptor.forClass(TextMessageEvent.class);
        verify(mockTextMessageEventHandler).handle(argument.capture());
        final TextMessageEvent textMessageEvent = argument.getValue();

        assertThat(textMessageEvent.getText(), is(equalTo("34wrr3wr")));
    }

    @Test(expected = MessengerVerificationException.class)
    public void shouldThrowExceptionIfSignatureIsInvalid() throws Exception {
        //given
        final String payload = "{\"object\":\"page\",\"entry\":[{\"id\":\"1717527131834678\",\"time\":1475942721780," +
                "\"messaging\":[{\"sender\":{\"id\":\"1256217357730577\"},\"recipient\":{\"id\":\"1717527131834678\"}," +
                "\"timestamp\":1475942721741,\"message\":{\"mid\":\"mid.1475942721728:3b9e3646712f9bed52\"," +
                "\"seq\":123,\"text\":\"CHANGED_TEXT_SO_SIGNATURE_IS_INVALID\"}}]}]}";

        final String signature = "sha1=3daa41999293ff66c3eb313e04bcf77861bb0276";
        final MessengerReceiveClient messengerReceiveClient = builder.build();

        //when
        messengerReceiveClient.processCallbackPayload(payload, signature);

        //then - throw exception
    }

    @Test
    public void shouldVerifyTheWebhook() throws Exception {
        //given
        final String mode = "subscribe";
        final String verifyToken = "CUSTOM_VERIFY_TOKEN";
        final String challenge = "CUSTOM_CHALLENGE";
        final MessengerReceiveClient messengerReceiveClient = builder.build();

        //when
        final String returnedChallenge = messengerReceiveClient.verifyWebhook(mode, verifyToken, challenge);

        //then
        assertThat(returnedChallenge, is(equalTo(challenge)));
    }

    @Test(expected = MessengerVerificationException.class)
    public void shouldThrowExceptionIfVerifyModeIsInvalid() throws Exception {
        //given
        final String mode = "INVALID_MODE";
        final String verifyToken = "CUSTOM_VERIFY_TOKEN";
        final String challenge = "CUSTOM_CHALLENGE";
        final MessengerReceiveClient messengerReceiveClient = builder.build();

        //when
        messengerReceiveClient.verifyWebhook(mode, verifyToken, challenge);

        //then - throw exception
    }

    @Test(expected = MessengerVerificationException.class)
    public void shouldThrowExceptionIfVerifyTokenIsInvalid() throws Exception {
        //given
        final String mode = "subscribe";
        final String verifyToken = "INVALID_VERIFY_TOKEN";
        final String challenge = "CUSTOM_CHALLENGE";
        final MessengerReceiveClient messengerReceiveClient = builder.build();

        //when
        messengerReceiveClient.verifyWebhook(mode, verifyToken, challenge);

        //then - throw exception
    }
}