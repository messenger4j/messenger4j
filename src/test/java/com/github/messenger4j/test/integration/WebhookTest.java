package com.github.messenger4j.test.integration;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import com.github.messenger4j.webhook.Event;
import com.github.messenger4j.webhook.event.*;
import com.github.messenger4j.webhook.event.attachment.Attachment;
import com.github.messenger4j.webhook.event.attachment.RichMediaAttachment;
import com.github.messenger4j.webhook.event.common.PriorMessage;
import com.github.messenger4j.webhook.event.nlp.NLPEntity;
import java.net.URL;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public class WebhookTest {

    @SuppressWarnings("unchecked")
    private final Consumer<Event> mockEventHandler = (Consumer<Event>) mock(Consumer.class);
    private final Messenger messenger = Messenger.create("test", "60efff025951cddde78c8d03de52cc90", "CUSTOM_VERIFY_TOKEN");

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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

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
                "                        \"url\": \"http://image.url\"\n" +
                "                    }\n" +
                "                }, {\n" +
                "                   \"type\":\"fallback\",\n" +
                "                   \"payload\":null,\n" +
                "                   \"title\":\"<TITLE_OF_THE_URL_ATTACHMENT>\",\n" +
                "                   \"URL\":\"<URL_OF_THE_ATTACHMENT>\"\n" +
                "                }, {\n" +
                "                    \"type\": \"location\",\n" +
                "                    \"payload\": {\n" +
                "                        \"coordinates\": {\n" +
                "                            \"lat\": 52.3765533,\n" +
                "                            \"long\": 9.7389123\n" +
                "                        }\n" +
                "                    }\n" +
                "                }]\n" +
                "            },\n" +
                "             \"prior_message\": {\n" +
                "               \"source\":\"checkbox_plugin\",\n" +
                "               \"identifier\":\"<USER_REF>\"\n" +
                "             }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final AttachmentMessageEvent attachmentMessageEvent = event.asAttachmentMessageEvent();
        assertThat(attachmentMessageEvent.senderId(), equalTo("USER_ID"));
        assertThat(attachmentMessageEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(attachmentMessageEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(attachmentMessageEvent.messageId(), equalTo("mid.1458696618141:b4ef9d19ec21086067"));
        assertThat(attachmentMessageEvent.attachments(), hasSize(3));

        final Attachment firstAttachment = attachmentMessageEvent.attachments().get(0);
        assertThat(firstAttachment.isRichMediaAttachment(), is(true));
        assertThat(firstAttachment.asRichMediaAttachment().type(), equalTo(RichMediaAttachment.Type.IMAGE));
        assertThat(firstAttachment.asRichMediaAttachment().url(), equalTo(new URL("http://image.url")));

        final Attachment secondAttachment = attachmentMessageEvent.attachments().get(1);
        assertThat(secondAttachment.isFallbackAttachment(), is(true));
        final String fallbackAttachmentJson = "{\n" +
                "        \"type\":\"fallback\",\n" +
                "        \"payload\":null,\n" +
                "      \t\"title\":\"<TITLE_OF_THE_URL_ATTACHMENT>\",\n" +
                "      \t\"URL\":\"<URL_OF_THE_ATTACHMENT>\"\n" +
                "      }";
        JSONAssert.assertEquals(fallbackAttachmentJson, secondAttachment.asFallbackAttachment().json(), true);

        final Attachment thirdAttachment = attachmentMessageEvent.attachments().get(2);
        assertThat(thirdAttachment.isLocationAttachment(), is(true));
        assertThat(thirdAttachment.asLocationAttachment().latitude(), equalTo(52.3765533));
        assertThat(thirdAttachment.asLocationAttachment().longitude(), equalTo(9.7389123));

        assertThat(attachmentMessageEvent.priorMessage().isPresent(), is(true));
        final PriorMessage priorMessage = attachmentMessageEvent.priorMessage().get();
        assertThat(priorMessage.source(), equalTo("checkbox_plugin"));
        assertThat(priorMessage.identifier(), equalTo("<USER_REF>"));
    }

    @Test
    public void shouldHandleOptInEventWithSenderId() throws Exception {
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final OptInEvent optInEvent = event.asOptInEvent();
        assertThat(optInEvent.senderId(), equalTo("USER_ID"));
        assertThat(optInEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(optInEvent.timestamp(), equalTo(Instant.ofEpochMilli(1234567890L)));
        assertThat(optInEvent.refPayload(), equalTo(of("PASS_THROUGH_PARAM")));
        assertThat(optInEvent.userRefPayload().isPresent(), is(false));
    }

    @Test
    public void shouldHandleOptInEventWithUserRef() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "            \"recipient\": {\n" +
                "                \"id\": \"PAGE_ID\"\n" +
                "            },\n" +
                "            \"timestamp\": 1234567890,\n" +
                "            \"optin\": {\n" +
                "                \"ref\": \"PASS_THROUGH_PARAM\",\n" +
                "                \"user_ref\": \"REF_FROM_CHECKBOX_PLUGIN\"\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final OptInEvent optInEvent = event.asOptInEvent();
        assertThat(optInEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(optInEvent.timestamp(), equalTo(Instant.ofEpochMilli(1234567890L)));
        assertThat(optInEvent.refPayload(), equalTo(of("PASS_THROUGH_PARAM")));
        assertThat(optInEvent.userRefPayload().isPresent(), is(true));
        assertThat(optInEvent.userRefPayload().get(), equalTo("REF_FROM_CHECKBOX_PLUGIN"));

        try {
            optInEvent.senderId();
            Assert.fail("UnsupportedOperationException expected.");
        } catch (UnsupportedOperationException e) {
            // Expected exception if senderId is not present.
        }
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final MessageEchoEvent messageEchoEvent = event.asMessageEchoEvent();
        assertThat(messageEchoEvent.senderId(), equalTo("PAGE_ID"));
        assertThat(messageEchoEvent.recipientId(), equalTo("USER_ID"));
        assertThat(messageEchoEvent.timestamp(), equalTo(Instant.ofEpochMilli(1480114700296L)));
        assertThat(messageEchoEvent.messageId(), equalTo("mid.1457764197618:41d102a3e1ae206a38"));
        assertThat(messageEchoEvent.appId(), equalTo("1517776481860111"));
        assertThat(messageEchoEvent.metadata(), equalTo(of("DEVELOPER_DEFINED_METADATA_STRING")));
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final MessageEchoEvent messageEchoEvent = event.asMessageEchoEvent();
        assertThat(messageEchoEvent.senderId(), equalTo("17175299999834678"));
        assertThat(messageEchoEvent.recipientId(), equalTo("1256299999730577"));
        assertThat(messageEchoEvent.timestamp(), equalTo(Instant.ofEpochMilli(1480120402725L)));
        assertThat(messageEchoEvent.messageId(), equalTo("mid.1480199999925:83392d9f65"));
        assertThat(messageEchoEvent.appId(), equalTo("1559999994822905"));
        assertThat(messageEchoEvent.metadata(), is(empty()));
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
                "            },\n" +
                "             \"prior_message\": {\n" +
                "               \"source\":\"checkbox_plugin\",\n" +
                "               \"identifier\":\"<USER_REF>\"\n" +
                "             }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final QuickReplyMessageEvent quickReplyMessageEvent = event.asQuickReplyMessageEvent();
        assertThat(quickReplyMessageEvent.senderId(), equalTo("USER_ID"));
        assertThat(quickReplyMessageEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(quickReplyMessageEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(quickReplyMessageEvent.messageId(), equalTo("mid.1457764197618:41d102a3e1ae206a38"));
        assertThat(quickReplyMessageEvent.text(), equalTo("hello, world!"));
        assertThat(quickReplyMessageEvent.payload(), equalTo("DEVELOPER_DEFINED_PAYLOAD"));
        assertThat(quickReplyMessageEvent.priorMessage().isPresent(), is(true));
        final PriorMessage priorMessage = quickReplyMessageEvent.priorMessage().get();
        assertThat(priorMessage.source(), equalTo("checkbox_plugin"));
        assertThat(priorMessage.identifier(), equalTo("<USER_REF>"));
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final TextMessageEvent textMessageEvent = event.asTextMessageEvent();
        assertThat(textMessageEvent.senderId(), equalTo("USER_ID"));
        assertThat(textMessageEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(textMessageEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(textMessageEvent.messageId(), equalTo("mid.1457764197618:41d102a3e1ae206a38"));
        assertThat(textMessageEvent.text(), equalTo("hello, text message world!"));
        assertThat(textMessageEvent.nlpEntities().isPresent(), is(false));
        assertThat(textMessageEvent.priorMessage().isPresent(), is(false));
    }

    @Test
    public void shouldHandleTextMessageEventWithPriorMessage() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "             \"sender\":{\n" +
                "               \"id\":\"<PSID>\"\n" +
                "             },\n" +
                "             \"recipient\":{\n" +
                "               \"id\":\"<PAGE_ID>\"\n" +
                "             },\n" +
                "             \"timestamp\":1458692752478,\n" +
                "             \"message\":{\n" +
                "               \"mid\":\"mid.1457744567618:41d102a3e1ae206a38\",\n" +
                "               \"text\":\"Thanks for messaging me!\"\n" +
                "             },\n" +
                "             \"prior_message\": {\n" +
                "               \"source\":\"checkbox_plugin\",\n" +
                "               \"identifier\":\"<USER_REF>\"\n" +
                "             }\n" +
                "           }]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final TextMessageEvent textMessageEvent = event.asTextMessageEvent();
        assertThat(textMessageEvent.senderId(), equalTo("<PSID>"));
        assertThat(textMessageEvent.recipientId(), equalTo("<PAGE_ID>"));
        assertThat(textMessageEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(textMessageEvent.messageId(), equalTo("mid.1457744567618:41d102a3e1ae206a38"));
        assertThat(textMessageEvent.text(), equalTo("Thanks for messaging me!"));
        assertThat(textMessageEvent.priorMessage().isPresent(), is(true));

        final PriorMessage priorMessage = textMessageEvent.priorMessage().get();
        assertThat(priorMessage.source(), equalTo("checkbox_plugin"));
        assertThat(priorMessage.identifier(), equalTo("<USER_REF>"));
    }

    @Test
    public void shouldHandleTextMessageEventWithNlp() throws Exception {
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
                "                \"text\": \"hello, text message world!\",\n" +
                "                \"nlp\": {\n" +
                "                    \"entities\": {\n" +
                "                        \"greetings\": [{\n" +
                "                            \"confidence\": 0.98786211036043,\n" +
                "                            \"value\": \"true\"\n" +
                "                        }]\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final TextMessageEvent textMessageEvent = event.asTextMessageEvent();
        assertThat(textMessageEvent.senderId(), equalTo("USER_ID"));
        assertThat(textMessageEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(textMessageEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(textMessageEvent.messageId(), equalTo("mid.1457764197618:41d102a3e1ae206a38"));
        assertThat(textMessageEvent.text(), equalTo("hello, text message world!"));
        assertThat(textMessageEvent.nlpEntities().isPresent(), is(true));

        final Map<String, Set<NLPEntity>> nlpEntities = textMessageEvent.nlpEntities().get();
        final Set<NLPEntity> greetingEntities = nlpEntities.get("greetings");
        assertThat(greetingEntities, notNullValue());
        assertThat(greetingEntities.size(), equalTo(1));

        final NLPEntity greetingNlpEntity = greetingEntities.iterator().next();
        final Greeting greeting = greetingNlpEntity.as(Greeting.class);
        assertThat(greeting.confidence, equalTo(0.98786211036043));
        assertThat(greeting.value, equalTo("true"));
    }

    private class Greeting {
        double confidence;
        String value;
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
                "             \"sender\":{\n" +
                "               \"id\":\"<PSID>\"\n" +
                "             },\n" +
                "             \"recipient\":{\n" +
                "               \"id\":\"<PAGE_ID>\"\n" +
                "             },\n" +
                "             \"timestamp\":1458692752478,\n" +
                "             \"postback\":{\n" +
                "               \"title\": \"<TITLE_FOR_THE_CTA>\",  \n" +
                "               \"payload\": \"<USER_DEFINED_PAYLOAD>\",\n" +
                "               \"referral\": {\n" +
                "                 \"ref\": \"<REF DATA IF SPECIFIED IN THE AD>\",\n" +
                "                 \"ad_id\": \"<ID OF THE AD>\",\n" +
                "                 \"source\": \"<SHORTLINK>\",\n" +
                "                 \"type\": \"OPEN_THREAD\"\n" +
                "               }\n" +
                "             },\n" +
                "             \"prior_message\": {\n" +
                "               \"source\":\"checkbox_plugin\",\n" +
                "               \"identifier\":\"<USER_REF>\"\n" +
                "             }\n" +
                "           }]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final PostbackEvent postbackEvent = event.asPostbackEvent();
        assertThat(postbackEvent.senderId(), equalTo("<PSID>"));
        assertThat(postbackEvent.recipientId(), equalTo("<PAGE_ID>"));
        assertThat(postbackEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(postbackEvent.title(), equalTo("<TITLE_FOR_THE_CTA>"));
        assertThat(postbackEvent.payload(), equalTo(of("<USER_DEFINED_PAYLOAD>")));
        assertThat(postbackEvent.referral().isPresent(), is(true));
        assertThat(postbackEvent.referral().get().source(), equalTo("<SHORTLINK>"));
        assertThat(postbackEvent.referral().get().type(), equalTo("OPEN_THREAD"));
        assertThat(postbackEvent.referral().get().refPayload(), equalTo(of("<REF DATA IF SPECIFIED IN THE AD>")));
        assertThat(postbackEvent.referral().get().adId(), equalTo(of("<ID OF THE AD>")));
        assertThat(postbackEvent.priorMessage().isPresent(), is(true));

        final PriorMessage priorMessage = postbackEvent.priorMessage().get();
        assertThat(priorMessage.source(), equalTo("checkbox_plugin"));
        assertThat(priorMessage.identifier(), equalTo("<USER_REF>"));
    }

    @Test
    public void shouldHandlePostbackEventWithoutReferral() throws Exception {
        //given
        final String payload = "{\n" +
                "    \"object\": \"page\",\n" +
                "    \"entry\": [{\n" +
                "        \"id\": \"PAGE_ID\",\n" +
                "        \"time\": 1458692752478,\n" +
                "        \"messaging\": [{\n" +
                "  \"sender\":{\n" +
                "    \"id\":\"<PSID>\"\n" +
                "  },\n" +
                "  \"recipient\":{\n" +
                "    \"id\":\"<PAGE_ID>\"\n" +
                "  },\n" +
                "  \"timestamp\":1458692752478,\n" +
                "  \"postback\":{\n" +
                "    \"title\": \"<TITLE_FOR_THE_CTA>\",  \n" +
                "    \"payload\": \"<USER_DEFINED_PAYLOAD>\"\n" +
                "  }\n" +
                "}]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final PostbackEvent postbackEvent = event.asPostbackEvent();
        assertThat(postbackEvent.senderId(), equalTo("<PSID>"));
        assertThat(postbackEvent.recipientId(), equalTo("<PAGE_ID>"));
        assertThat(postbackEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(postbackEvent.title(), equalTo("<TITLE_FOR_THE_CTA>"));
        assertThat(postbackEvent.payload(), equalTo(of("<USER_DEFINED_PAYLOAD>")));
        assertThat(postbackEvent.referral().isPresent(), is(false));
    }

    @Test
    public void shouldHandleMeLinkReferralEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "  \"object\":\"page\",\n" +
                "  \"entry\":[\n" +
                "    {\n" +
                "      \"id\":\"<PAGE_ID>\",\n" +
                "      \"time\":1458692752478,\n" +
                "      \"messaging\":[\n" +
                "      {\n" +
                "          \"sender\":{\n" +
                "            \"id\":\"<USER_ID>\"\n" +
                "          },\n" +
                "          \"recipient\":{\n" +
                "            \"id\":\"<PAGE_ID>\"\n" +
                "          },\n" +
                "          \"timestamp\":1458692752478,\n" +
                "          \"referral\": {\n" +
                "            \"ref\": \"<REF DATA PASSED IN M.ME PARAM>\",\n" +
                "            \"source\": \"SHORTLINK\",\n" +
                "            \"type\": \"OPEN_THREAD\"\n" +
                "          }\n" +
                "      }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final ReferralEvent referralEvent = event.asReferralEvent();
        assertThat(referralEvent.senderId(), equalTo("<USER_ID>"));
        assertThat(referralEvent.recipientId(), equalTo("<PAGE_ID>"));
        assertThat(referralEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(referralEvent.referral().source(), equalTo("SHORTLINK"));
        assertThat(referralEvent.referral().type(), equalTo("OPEN_THREAD"));
        assertThat(referralEvent.referral().refPayload(), equalTo(of("<REF DATA PASSED IN M.ME PARAM>")));
    }

    @Test
    public void shouldHandleAdReferralEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "  \"object\":\"page\",\n" +
                "  \"entry\":[\n" +
                "    {\n" +
                "      \"id\":\"<PAGE_ID>\",\n" +
                "      \"time\":1458692752478,\n" +
                "      \"messaging\":[\n" +
                "      {\n" +
                "          \"sender\":{\n" +
                "            \"id\":\"<USER_ID>\"\n" +
                "          },\n" +
                "          \"recipient\":{\n" +
                "            \"id\":\"<PAGE_ID>\"\n" +
                "          },\n" +
                "          \"timestamp\":1458692752478,\n" +
                "          \"referral\": {\n" +
                "            \"ref\": \"<REF DATA IF SPECIFIED IN THE AD>\",\n" +
                "            \"ad_id\": \"<ID OF THE AD>\",\n" +
                "            \"source\": \"ADS\",\n" +
                "            \"type\": \"OPEN_THREAD\"\n" +
                "          }\n" +
                "      }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final ReferralEvent referralEvent = event.asReferralEvent();
        assertThat(referralEvent.senderId(), equalTo("<USER_ID>"));
        assertThat(referralEvent.recipientId(), equalTo("<PAGE_ID>"));
        assertThat(referralEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(referralEvent.referral().source(), equalTo("ADS"));
        assertThat(referralEvent.referral().type(), equalTo("OPEN_THREAD"));
        assertThat(referralEvent.referral().refPayload(), equalTo(of("<REF DATA IF SPECIFIED IN THE AD>")));
        assertThat(referralEvent.referral().adId(), equalTo(of("<ID OF THE AD>")));
    }

    @Test
    public void shouldHandleParametricMessengerCodeReferralEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "  \"object\":\"page\",\n" +
                "  \"entry\":[\n" +
                "    {\n" +
                "      \"id\":\"<PAGE_ID>\",\n" +
                "      \"time\":1458692752478,\n" +
                "      \"messaging\":[\n" +
                "      {\n" +
                "          \"sender\":{\n" +
                "            \"id\":\"<USER_ID>\"\n" +
                "          },\n" +
                "          \"recipient\":{\n" +
                "            \"id\":\"<PAGE_ID>\"\n" +
                "          },\n" +
                "          \"timestamp\":1458692752478,\n" +
                "          \"referral\": {\n" +
                "            \"ref\": \"<REF DATA PASSED IN CODE>\",\n" +
                "            \"source\": \"MESSENGER_CODE\",\n" +
                "            \"type\": \"OPEN_THREAD\"\n" +
                "          }\n" +
                "      }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final ReferralEvent referralEvent = event.asReferralEvent();
        assertThat(referralEvent.senderId(), equalTo("<USER_ID>"));
        assertThat(referralEvent.recipientId(), equalTo("<PAGE_ID>"));
        assertThat(referralEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(referralEvent.referral().source(), equalTo("MESSENGER_CODE"));
        assertThat(referralEvent.referral().type(), equalTo("OPEN_THREAD"));
        assertThat(referralEvent.referral().refPayload(), equalTo(of("<REF DATA PASSED IN CODE>")));
    }

    @Test
    public void shouldHandleDiscoverTabReferralEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "  \"object\":\"page\",\n" +
                "  \"entry\":[\n" +
                "    {\n" +
                "      \"id\":\"<PAGE_ID>\",\n" +
                "      \"time\":1458692752478,\n" +
                "      \"messaging\":[\n" +
                "      {\n" +
                "          \"sender\":{\n" +
                "            \"id\":\"<USER_ID>\"\n" +
                "          },\n" +
                "          \"recipient\":{\n" +
                "            \"id\":\"<PAGE_ID>\"\n" +
                "          },\n" +
                "          \"timestamp\":1458692752478,\n" +
                "          \"referral\": {\n" +
                "            \"source\": \"DISCOVER_TAB\",\n" +
                "            \"type\": \"OPEN_THREAD\"\n" +
                "          }\n" +
                "      }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final ReferralEvent referralEvent = event.asReferralEvent();
        assertThat(referralEvent.senderId(), equalTo("<USER_ID>"));
        assertThat(referralEvent.recipientId(), equalTo("<PAGE_ID>"));
        assertThat(referralEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));
        assertThat(referralEvent.referral().source(), equalTo("DISCOVER_TAB"));
        assertThat(referralEvent.referral().type(), equalTo("OPEN_THREAD"));
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final AccountLinkingEvent accountLinkingEvent = event.asAccountLinkingEvent();
        assertThat(accountLinkingEvent.senderId(), equalTo("USER_ID"));
        assertThat(accountLinkingEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(accountLinkingEvent.timestamp(), equalTo(Instant.ofEpochMilli(1234567890L)));
        assertThat(accountLinkingEvent.status(), equalTo(AccountLinkingEvent.Status.LINKED));
        assertThat(accountLinkingEvent.authorizationCode(), equalTo(of("PASS_THROUGH_AUTHORIZATION_CODE")));
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final AccountLinkingEvent accountLinkingEvent = event.asAccountLinkingEvent();
        assertThat(accountLinkingEvent.senderId(), equalTo("USER_ID"));
        assertThat(accountLinkingEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(accountLinkingEvent.timestamp(), equalTo(Instant.ofEpochMilli(1234567890L)));
        assertThat(accountLinkingEvent.status(), equalTo(AccountLinkingEvent.Status.UNLINKED));
        assertThat(accountLinkingEvent.authorizationCode(), is(empty()));
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
                "                \"id\": \"\"\n" +
                "            },\n" +
                "            \"timestamp\": 1458668856463,\n" +
                "            \"read\": {\n" +
                "                \"watermark\": 1458668856253\n" +
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final MessageReadEvent messageReadEvent = event.asMessageReadEvent();
        assertThat(messageReadEvent.senderId(), equalTo("USER_ID"));
        assertThat(messageReadEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(messageReadEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458668856463L)));
        assertThat(messageReadEvent.watermark(), equalTo(Instant.ofEpochMilli(1458668856253L)));
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final MessageDeliveredEvent messageDeliveredEvent = event.asMessageDeliveredEvent();
        assertThat(messageDeliveredEvent.senderId(), equalTo("USER_ID"));
        assertThat(messageDeliveredEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(messageDeliveredEvent.watermark(), equalTo(Instant.ofEpochMilli(1458668856253L)));
        assertThat(messageDeliveredEvent.messageIds().isPresent(), is(true));
        assertThat(messageDeliveredEvent.messageIds().get(), hasSize(1));
        assertThat(messageDeliveredEvent.messageIds().get().get(0), equalTo("mid.1458668856218:ed81099e15d3f4f233"));
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final MessageDeliveredEvent messageDeliveredEvent = event.asMessageDeliveredEvent();
        assertThat(messageDeliveredEvent.senderId(), equalTo("USER_ID"));
        assertThat(messageDeliveredEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(messageDeliveredEvent.watermark(), equalTo(Instant.ofEpochMilli(1458668856253L)));
        assertThat(messageDeliveredEvent.messageIds().isPresent(), is(false));
    }

    @Test
    public void shouldHandleEventTypeThatIsUnsupported() throws Exception {
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

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        assertThat(event.senderId(), equalTo("USER_ID"));
        assertThat(event.recipientId(), equalTo("PAGE_ID"));
        assertThat(event.timestamp(), equalTo(Instant.ofEpochMilli(1458692752478L)));

        assertThat(event.isAccountLinkingEvent(), is(false));
        assertThat(event.isMessageDeliveredEvent(), is(false));
        assertThat(event.isMessageEchoEvent(), is(false));
        assertThat(event.isMessageReadEvent(), is(false));
        assertThat(event.isOptInEvent(), is(false));
        assertThat(event.isPostbackEvent(), is(false));
        assertThat(event.isReferralEvent(), is(false));
        assertThat(event.isAttachmentMessageEvent(), is(false));
        assertThat(event.isQuickReplyMessageEvent(), is(false));
        assertThat(event.isTextMessageEvent(), is(false));
    }

    @Test
    public void shouldHandleInstantGameEvent() throws Exception {
        //given
        final String payload = "{\n" +
                "   \"object\":\"page\",\n" +
                "   \"entry\":[\n" +
                "      {\n" +
                "         \"id\":\"1717527131834678\",\n" +
                "         \"time\":\"1458692752478\",\n" +
                "         \"messaging\":[\n" +
                "            {\n" +
                "               \"sender\":{\n" +
                "                  \"id\":\"USER_ID\"\n" +
                "               },\n" +
                "               \"recipient\":{\n" +
                "                  \"id\":\"PAGE_ID\"\n" +
                "               },\n" +
                "               \"timestamp\":\"1458668856463\",\n" +
                "               \"game_play\":{\n" +
                "                  \"game_id\":\"<GAME-APP-ID>\",\n" +
                "                  \"player_id\":\"<PLAYER-ID>\",\n" +
                "                  \"context_type\":\"SOLO\",\n" +
                "                  \"context_id\": 666666, \n" +
                "                  \"payload\":\"PAYLOAD\"\n" +
                "               }\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   ]\n" +
                "}";

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        final InstantGameEvent messageReadEvent = event.asInstantGameEvent();
        assertThat(messageReadEvent.senderId(), equalTo("USER_ID"));
        assertThat(messageReadEvent.recipientId(), equalTo("PAGE_ID"));
        assertThat(messageReadEvent.timestamp(), equalTo(Instant.ofEpochMilli(1458668856463L)));
        assertThat(messageReadEvent.gameId(), equalTo("<GAME-APP-ID>"));
        assertThat(messageReadEvent.playerId(), equalTo("<PLAYER-ID>"));
        assertThat(messageReadEvent.contextType(), equalTo("SOLO"));
        assertThat(messageReadEvent.contextId(), equalTo(of("666666")));
        assertThat(messageReadEvent.payload(), equalTo(of("PAYLOAD")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfNoPayloadProvided() throws Exception {
        //given
        final String payload = null;

        //when
        messenger.onReceiveEvents(payload, empty(), mockEventHandler);

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

        //when
        messenger.onReceiveEvents(payload, of(signature), mockEventHandler);

        //then
        final ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(mockEventHandler).accept(eventCaptor.capture());
        final Event event = eventCaptor.getValue();

        assertThat(event.asTextMessageEvent().text(), is(equalTo("34wrr3wr")));
    }

    @Test(expected = MessengerVerificationException.class)
    public void shouldThrowExceptionIfSignatureIsInvalid() throws Exception {
        //given
        final String payload = "{\"object\":\"page\",\"entry\":[{\"id\":\"1717527131834678\",\"time\":1475942721780," +
                "\"messaging\":[{\"sender\":{\"id\":\"1256217357730577\"},\"recipient\":{\"id\":\"1717527131834678\"}," +
                "\"timestamp\":1475942721741,\"message\":{\"mid\":\"mid.1475942721728:3b9e3646712f9bed52\"," +
                "\"seq\":123,\"text\":\"CHANGED_TEXT_SO_SIGNATURE_IS_INVALID\"}}]}]}";
        final String signature = "sha1=3daa41999293ff66c3eb313e04bcf77861bb0276";

        //when
        messenger.onReceiveEvents(payload, of(signature), mockEventHandler);

        //then - throw exception
    }

    @Test
    public void shouldVerifyTheWebhook() throws Exception {
        final String mode = "subscribe";
        final String verifyToken = "CUSTOM_VERIFY_TOKEN";

        // tag::webhook-Verify[]
        messenger.verifyWebhook(mode, verifyToken);
        // end::webhook-Verify[]

        //no exception is thrown
        assertThat(true, is(true));
    }

    @Test(expected = MessengerVerificationException.class)
    public void shouldThrowExceptionIfVerifyModeIsInvalid() throws Exception {
        //given
        final String mode = "INVALID_MODE";
        final String verifyToken = "CUSTOM_VERIFY_TOKEN";

        //when
        messenger.verifyWebhook(mode, verifyToken);

        //then - throw exception
    }

    @Test(expected = MessengerVerificationException.class)
    public void shouldThrowExceptionIfVerifyTokenIsInvalid() throws Exception {
        //given
        final String mode = "subscribe";
        final String verifyToken = "INVALID_VERIFY_TOKEN";

        //when
        messenger.verifyWebhook(mode, verifyToken);

        //then - throw exception
    }
}
