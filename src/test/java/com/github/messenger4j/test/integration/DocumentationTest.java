package com.github.messenger4j.test.integration;

import static java.util.Optional.of;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.spi.MessengerHttpClient;
import com.github.messenger4j.webhook.event.AttachmentMessageEvent;
import com.github.messenger4j.webhook.event.TextMessageEvent;
import com.github.messenger4j.webhook.event.attachment.Attachment;
import com.github.messenger4j.webhook.event.attachment.LocationAttachment;
import com.github.messenger4j.webhook.event.attachment.RichMediaAttachment;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * README.adoc examples.
 *
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@Slf4j
public class DocumentationTest {

    @Test
    public void shouldInstantiateMessenger() throws Exception {
        // tag::doc-Create[]
        final Messenger messenger = Messenger.create("PAGE_ACCESS_TOKEN", "APP_SECRET", "VERIFY_TOKEN");
        // end::doc-Create[]

        assertThat(messenger, is(notNullValue()));
    }

    private final static class MyCustomMessengerHttpClient implements MessengerHttpClient {
        @Override
        public HttpResponse execute(HttpMethod httpMethod, String url, String jsonBody) throws IOException {
            return null;
        }
    }

    @Test
    public void shouldInstantiateMessengerCustomHttpClient() throws Exception {
        // tag::doc-CreateCustomHttp[]
        final MyCustomMessengerHttpClient customHttpClient = new MyCustomMessengerHttpClient();
        final Messenger messenger = Messenger.create("PAGE_ACCESS_TOKEN", "APP_SECRET",
                "VERIFY_TOKEN", Optional.empty(), Optional.empty(), Optional.of(customHttpClient));
        // end::doc-CreateCustomHttp[]

        assertThat(messenger, is(notNullValue()));
    }

    @Test
    public void shouldProcessReceivedEventsText() throws Exception {
        final Messenger messenger = Messenger.create("test", "60efff025951cddde78c8d03de52cc90", "test");

        // tag::doc-ReceiveEventsText[]
        final String payload = "{\"object\":\"page\",\"entry\":[{\"id\":\"1717527131834678\",\"time\":1475942721780," +
                "\"messaging\":[{\"sender\":{\"id\":\"1256217357730577\"},\"recipient\":{\"id\":\"1717527131834678\"}," +
                "\"timestamp\":1475942721741,\"message\":{\"mid\":\"mid.1475942721728:3b9e3646712f9bed52\"," +
                "\"seq\":123,\"text\":\"34wrr3wr\"}}]}]}";
        final String signature = "sha1=3daa41999293ff66c3eb313e04bcf77861bb0276";

        messenger.onReceiveEvents(payload, of(signature), event -> {
            final String senderId = event.senderId();
            final Instant timestamp = event.timestamp();

            if (event.isTextMessageEvent()) {
                final TextMessageEvent textMessageEvent = event.asTextMessageEvent();
                final String messageId = textMessageEvent.messageId();
                final String text = textMessageEvent.text();

                log.debug("Received text message from '{}' at '{}' with content: {} (mid: {})",
                        senderId, timestamp, text, messageId);
            }
        });
        // end::doc-ReceiveEventsText[]
    }

    @Test
    public void shouldProcessReceivedEventsAttachments() throws Exception {
        final Messenger messenger = Messenger.create("test", "test", "test");

        // tag::doc-ReceiveEventsAttachment[]
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
                "            }\n" +
                "        }]\n" +
                "    }]\n" +
                "}";

        messenger.onReceiveEvents(payload, Optional.empty(), event -> {
            final String senderId = event.senderId();
            final Instant timestamp = event.timestamp();

            log.debug("Received event from '{}' at '{}'", senderId, timestamp);

            if (event.isAttachmentMessageEvent()) {
                final AttachmentMessageEvent attachmentMessageEvent = event.asAttachmentMessageEvent();
                for (Attachment attachment : attachmentMessageEvent.attachments()) {
                    if (attachment.isRichMediaAttachment()) {
                        final RichMediaAttachment richMediaAttachment = attachment.asRichMediaAttachment();
                        final RichMediaAttachment.Type type = richMediaAttachment.type();
                        final URL url = richMediaAttachment.url();
                        log.debug("Received rich media attachment of type '{}' with url: {}", type, url);
                    }
                    if (attachment.isLocationAttachment()) {
                        final LocationAttachment locationAttachment = attachment.asLocationAttachment();
                        final double longitude = locationAttachment.longitude();
                        final double latitude = locationAttachment.latitude();
                        log.debug("Received location information (long: {}, lat: {})", longitude, latitude);
                    }
                }
            }
        });
        // end::doc-ReceiveEventsAttachment[]
    }

    @Test
    public void echoExample() throws Exception {
        // tag::doc-EchoExample[]
        final String payload = "{\n" +
                "  \"object\": \"page\",\n" +
                "  \"entry\": [{\n" +
                "    \"id\": \"1717527131834678\",\n" +
                "    \"time\": 1475942721780,\n" +
                "    \"messaging\": [{\n" +
                "      \"sender\": {\n" +
                "        \"id\": \"1256217357730577\"\n" +
                "      },\n" +
                "      \"recipient\": {\n" +
                "        \"id\": \"1717527131834678\"\n" +
                "      },\n" +
                "      \"timestamp\": 1475942721741,\n" +
                "      \"message\": {\n" +
                "        \"mid\": \"mid.1475942721728:3b9e3646712f9bed52\",\n" +
                "        \"seq\": 123,\n" +
                "        \"text\": \"Hello Chatbot\"\n" +
                "      }\n" +
                "    }]\n" +
                "  }]\n" +
                "}";

        final Messenger messenger = Messenger.create("PAGE_ACCESS_TOKEN", "APP_SECRET", "VERIFY_TOKEN");

        messenger.onReceiveEvents(payload, Optional.empty(), event -> {
            final String senderId = event.senderId();
            if (event.isTextMessageEvent()) {
                final String text = event.asTextMessageEvent().text();

                final TextMessage textMessage = TextMessage.create(text);
                final MessagePayload messagePayload = MessagePayload.create(senderId,
                        MessagingType.RESPONSE, textMessage);

                try {
                    messenger.send(messagePayload);
                } catch (MessengerApiException | MessengerIOException e) {
                    // Oops, something went wrong
                }
            }
        });
        // end::doc-EchoExample[]
    }
}
