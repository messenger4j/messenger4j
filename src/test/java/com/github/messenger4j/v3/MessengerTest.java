package com.github.messenger4j.v3;

import static com.github.messenger4j.v3.RichMedia.Type.IMAGE;
import static com.github.messenger4j.v3.RichMedia.Type.VIDEO;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.github.messenger4j.common.MessengerHttpClient;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.send.MessageResponse;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.QuickReply;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import com.github.messenger4j.send.templates.GenericTemplate;
import com.github.messenger4j.setup.CallToAction;
import com.github.messenger4j.setup.CallToActionType;
import com.github.messenger4j.setup.SetupResponse;
import com.github.messenger4j.user.UserProfile;
import com.github.messenger4j.v3.receive.Event;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public class MessengerTest {

    private final Messenger messenger = Messenger.create("test", "test", "test");

    public void should_instantiate_a_messenger_instance() {
        final String pageAccessToken = "pageAccessToken";
        final String appSecret = "appSecret";
        final String verifyToken = "verifyToken";

        final Messenger messenger = Messenger.create(pageAccessToken, appSecret, verifyToken);

        assertThat(messenger, is(notNullValue()));
    }

    public void should_instantiate_a_messenger_instance_with_custom_http_client() {
        final String pageAccessToken = "pageAccessToken";
        final String appSecret = "appSecret";
        final String verifyToken = "verifyToken";
        final MessengerHttpClient customHttpClient = (httpMethod, url, jsonBody) -> null;

        final Messenger messenger = Messenger.create(pageAccessToken, appSecret, verifyToken, customHttpClient);

        assertThat(messenger, is(notNullValue()));
    }

    public void should_send_a_text_message() throws Exception {
        final String text = "Hello World";
        final String recipientId = "recipientId";

        final Message message = Message.newBuilder().text(text).build();
        final MessagePayload messagePayload = MessagePayload.newBuilder()
                .recipientId(recipientId)
                .message(message)
                .build();
        final MessageResponse response = messenger.send(messagePayload);

        assertThat(response, is(notNullValue()));
    }

    public void should_send_a_text_message_with_notification_type() throws Exception {
        final String text = "Hello World";
        final Recipient recipient = Recipient.newBuilder().recipientId("recipientId").build();
        final NotificationType notificationType = NotificationType.SILENT_PUSH;

        final Message message = Message.newBuilder().text(text).build();
        final MessagePayload messagePayload = MessagePayload.newBuilder()
                .recipient(recipient)
                .message(message)
                .notificationType(notificationType)
                .build();
        final MessageResponse response = messenger.send(messagePayload);

        assertThat(response, is(notNullValue()));
    }

    public void should_send_a_sender_action() throws Exception {
        final SenderAction senderAction = SenderAction.MARK_SEEN;
        final Recipient recipient = Recipient.newBuilder().recipientId("recipientId").build();

        final MessagePayload messagePayload = MessagePayload.newBuilder()
                .recipient(recipient)
                .senderAction(senderAction)
                .build();
        final MessageResponse response = messenger.send(messagePayload);

        assertThat(response, is(notNullValue()));
    }

    public void should_send_a_sender_action_with_notification_type() throws Exception {
        final SenderAction senderAction = SenderAction.MARK_SEEN;
        final Recipient recipient = Recipient.newBuilder().recipientId("recipientId").build();
        final NotificationType notificationType = NotificationType.NO_PUSH;

        final MessagePayload messagePayload = MessagePayload.newBuilder()
                .recipient(recipient)
                .senderAction(senderAction)
                .notificationType(notificationType)
                .build();
        final MessageResponse response = messenger.send(messagePayload);

        assertThat(response, is(notNullValue()));
    }

    public void should_send_a_template() throws Exception {
        final GenericTemplate template = GenericTemplate.newBuilder()
                .addElements()
                .addElement("Test").toList().done()
                .build();
        final String recipientId = "recipientId";

        final Message message = Message.newBuilder().template(template).build();
        final MessagePayload messagePayload = MessagePayload.newBuilder()
                .recipientId(recipientId)
                .message(message)
                .build();
        final MessageResponse response = messenger.send(messagePayload);

        assertThat(response, is(notNullValue()));
    }

    public void should_send_a_video_message() throws Exception {
        final String recipientId = "recipientId";
        final String videoUrl = "http://video.url";

        final RichMedia richMedia = RichMedia.createByUrl(VIDEO, videoUrl);
        final Message message = Message.newBuilder().richMedia(richMedia).build();
        final MessagePayload messagePayload = MessagePayload.newBuilder()
                .recipientId(recipientId)
                .message(message)
                .build();
        final MessageResponse response = messenger.send(messagePayload);

        assertThat(response, is(notNullValue()));
    }

    public void should_send_an_image_message_with_quick_replies() throws Exception {
        final String recipientId = "recipientId";
        final String imageUrl = "http://image.url";
        final List<QuickReply> quickReplies = QuickReply.newListBuilder()
                .addTextQuickReply("Test", "Test").toList()
                .build();

        final RichMedia richMedia = RichMedia.createByUrl(IMAGE, imageUrl);
        final Message message = Message.newBuilder().richMedia(richMedia).quickReplies(quickReplies).build();
        final MessagePayload messagePayload = MessagePayload.newBuilder()
                .recipientId(recipientId)
                .message(message)
                .build();
        final MessageResponse response = messenger.send(messagePayload);

        assertThat(response, is(notNullValue()));
    }

    public void should_handle_MessengerApiException() {
    }

    public void should_handle_MessengerIOException() {
    }


    public void should_handle_incoming_event_check_signature() throws MessengerVerificationException {
        final String requestPayload = "{}";
        final String signature = "signature";
        final Consumer<Event> eventHandler = System.out::println;

        messenger.onReceiveEvents(requestPayload, signature, eventHandler);
    }

    public void should_verify_the_webhook() throws MessengerVerificationException {
        final String mode = "mode";
        final String verifyToken = "verifyToken";

        messenger.verifyWebhook(mode, verifyToken);
    }

    public void should_query_the_user_profile_by_id() throws MessengerApiException, MessengerIOException {
        final String userId = "userId";

        final UserProfile userProfile = messenger.queryUserProfileById(userId);

        assertThat(userProfile, is(notNullValue()));
    }

    public void should_setup_the_persistent_menu() {
        final SupportedLocale localeZA = SupportedLocale.af_ZA;
        final SupportedLocale localeDE = SupportedLocale.de_DE;
        final CallToAction callToAction1 = CallToAction.newBuilder()
                .type(CallToActionType.POSTBACK).payload("payload").title("Test")
                .build();
        final CallToAction callToAction2 = CallToAction.newBuilder()
                .type(CallToActionType.POSTBACK).payload("payload").title("Test")
                .build();


        //TODO: rewrite
        final PersistentMenuConfiguration persistentMenuConfigZA = PersistentMenuConfiguration
                .create(true, callToAction1, callToAction2);
        final PersistentMenuConfiguration persistentMenuConfigDE = PersistentMenuConfiguration
                .create(callToAction1, callToAction2);
        final PersistentMenuConfiguration persistentMenuConfigDefault = PersistentMenuConfiguration.create(false);

        final PersistentMenu persistentMenu = PersistentMenu.newBuilder()
                .defaultConfiguration(persistentMenuConfigDefault)
                .addConfiguration(localeZA, persistentMenuConfigZA)
                .addConfiguration(localeDE, persistentMenuConfigDE)
                .build();

        final SetupResponse setupResponse = messenger.setupPersistentMenu(persistentMenu);

        assertThat(setupResponse, is(notNullValue()));
    }

    public void should_setup_the_get_started_button() {
        final String payload = "payload";

        final SetupResponse setupResponse = messenger.setupGetStartedButton(payload);

        assertThat(setupResponse, is(notNullValue()));
    }

    public void should_setup_the_greeting_text() {
        final SupportedLocale localeZA = SupportedLocale.af_ZA;
        final String text = "text";

        final GreetingText greetingText = GreetingText.newBuilder()
                .defaultGreeting(text)
                .addGreeting(localeZA, text)
                .build();
        final SetupResponse setupResponse = messenger.setupGreetingText(greetingText);

        assertThat(setupResponse, is(notNullValue()));
    }

}