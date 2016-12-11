package com.github.messenger4j.send;

import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.internal.PreConditions;
import com.github.messenger4j.send.http.MessengerHttpClient;
import com.github.messenger4j.send.templates.Template;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
final class MessengerSendClientImpl implements MessengerSendClient {

    private static final String FB_GRAPH_API_URL = "https://graph.facebook.com/v2.8/me/messages?access_token=%s";

    private final Logger logger = LoggerFactory.getLogger(MessengerSendClientImpl.class);

    private final Gson gson;
    private final JsonParser jsonParser;
    private final String requestUrl;
    private final MessengerHttpClient httpClient;

    MessengerSendClientImpl(MessengerSendClientBuilder builder) {
        this.gson = new GsonBuilder().registerTypeAdapter(Float.class, floatSerializer()).create();
        this.jsonParser = new JsonParser();
        this.requestUrl = String.format(FB_GRAPH_API_URL, builder.pageAccessToken);
        this.httpClient = builder.httpClient == null ? new DefaultMessengerHttpClient() : builder.httpClient;

        logger.debug("{} initialized successfully.", MessengerSendClientImpl.class.getSimpleName());
    }

    private JsonSerializer<Float> floatSerializer() {
        return new JsonSerializer<Float>() {
            public JsonElement serialize(Float floatValue, java.lang.reflect.Type type, JsonSerializationContext context) {
                if (floatValue.isNaN() || floatValue.isInfinite()) {
                    return null;
                }
                return new JsonPrimitive(new BigDecimal(floatValue).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        };
    }

    @Override
    public MessengerResponse sendSenderAction(String recipientId, SenderAction senderAction)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(buildRecipient(recipientId))
                .senderAction(senderAction)
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendSenderAction(Recipient recipient, NotificationType notificationType,
                                              SenderAction senderAction)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .senderAction(senderAction)
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTextMessage(String recipientId, String text)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(buildRecipient(recipientId))
                .addMessage().text(text).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTextMessage(String recipientId, String text, List<QuickReply> quickReplies)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(buildRecipient(recipientId))
                .addMessage().text(text).quickReplies(quickReplies).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTextMessage(Recipient recipient, NotificationType notificationType, String text)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().text(text).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTextMessage(Recipient recipient, NotificationType notificationType, String text,
                                             List<QuickReply> quickReplies)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().text(text).quickReplies(quickReplies).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTextMessage(Recipient recipient, NotificationType notificationType, String text,
                                             String metadata)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().text(text).metadata(metadata).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTextMessage(Recipient recipient, NotificationType notificationType, String text,
                                             List<QuickReply> quickReplies, String metadata)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().text(text).quickReplies(quickReplies).metadata(metadata).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendImageAttachment(String recipientId, String imageUrl)
            throws MessengerApiException, MessengerIOException {

        final BinaryAttachment imageAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.IMAGE)
                .url(imageUrl)
                .build();
        return sendBinaryAttachment(recipientId, imageAttachment);
    }

    @Override
    public MessengerResponse sendImageAttachment(String recipientId, String imageUrl, List<QuickReply> quickReplies)
            throws MessengerApiException, MessengerIOException {

        final BinaryAttachment imageAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.IMAGE)
                .url(imageUrl)
                .build();
        final MessagingPayload payload = MessagingPayload.newBuilder(buildRecipient(recipientId))
                .addMessage().binaryAttachment(imageAttachment).quickReplies(quickReplies).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendAudioAttachment(String recipientId, String audioUrl)
            throws MessengerApiException, MessengerIOException {

        final BinaryAttachment audioAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.AUDIO)
                .url(audioUrl)
                .build();
        return sendBinaryAttachment(recipientId, audioAttachment);
    }

    @Override
    public MessengerResponse sendVideoAttachment(String recipientId, String videoUrl)
            throws MessengerApiException, MessengerIOException {

        final BinaryAttachment videoAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.VIDEO)
                .url(videoUrl)
                .build();
        return sendBinaryAttachment(recipientId, videoAttachment);
    }

    @Override
    public MessengerResponse sendFileAttachment(String recipientId, String fileUrl)
            throws MessengerApiException, MessengerIOException {

        final BinaryAttachment fileAttachment = BinaryAttachment.newBuilder(BinaryAttachment.Type.FILE)
                .url(fileUrl)
                .build();
        return sendBinaryAttachment(recipientId, fileAttachment);
    }

    @Override
    public MessengerResponse sendBinaryAttachment(String recipientId, BinaryAttachment binaryAttachment)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(buildRecipient(recipientId))
                .addMessage().binaryAttachment(binaryAttachment).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendBinaryAttachment(Recipient recipient, NotificationType notificationType,
                                                  BinaryAttachment binaryAttachment)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().binaryAttachment(binaryAttachment).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendBinaryAttachment(Recipient recipient, NotificationType notificationType,
                                                  BinaryAttachment binaryAttachment, List<QuickReply> quickReplies)
            throws MessengerApiException, MessengerIOException {

        checkBinaryAttachmentTypeUsageWithQuickReplies(binaryAttachment);
        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().binaryAttachment(binaryAttachment).quickReplies(quickReplies).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendBinaryAttachment(Recipient recipient, NotificationType notificationType,
                                                  BinaryAttachment binaryAttachment, String metadata)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().binaryAttachment(binaryAttachment).metadata(metadata).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendBinaryAttachment(Recipient recipient, NotificationType notificationType,
                                                  BinaryAttachment binaryAttachment, List<QuickReply> quickReplies,
                                                  String metadata)
            throws MessengerApiException, MessengerIOException {

        checkBinaryAttachmentTypeUsageWithQuickReplies(binaryAttachment);
        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().binaryAttachment(binaryAttachment).quickReplies(quickReplies).metadata(metadata).done()
                .build();
        return sendMessagingPayload(payload);
    }

    private void checkBinaryAttachmentTypeUsageWithQuickReplies(BinaryAttachment binaryAttachment) {
        PreConditions.isTrue(binaryAttachment.getType().equals(BinaryAttachment.Type.IMAGE), "quickReplies just work with " +
                "text message, image attachments and templates");
    }

    @Override
    public MessengerResponse sendTemplate(String recipientId, Template template)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(buildRecipient(recipientId))
                .addMessage().template(template).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTemplate(Recipient recipient, NotificationType notificationType, Template template)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().template(template).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTemplate(Recipient recipient, NotificationType notificationType, Template template,
                                          List<QuickReply> quickReplies)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().template(template).quickReplies(quickReplies).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTemplate(Recipient recipient, NotificationType notificationType, Template template,
                                          String metadata)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().template(template).metadata(metadata).done()
                .build();
        return sendMessagingPayload(payload);
    }

    @Override
    public MessengerResponse sendTemplate(Recipient recipient, NotificationType notificationType, Template template,
                                          List<QuickReply> quickReplies, String metadata)
            throws MessengerApiException, MessengerIOException {

        final MessagingPayload payload = MessagingPayload.newBuilder(recipient)
                .notificationType(notificationType)
                .addMessage().template(template).quickReplies(quickReplies).metadata(metadata).done()
                .build();
        return sendMessagingPayload(payload);
    }

    private Recipient buildRecipient(String recipientId) {
        return Recipient.newBuilder().recipientId(recipientId).build();
    }

    private MessengerResponse sendMessagingPayload(MessagingPayload messagingPayload)
            throws MessengerApiException, MessengerIOException {

        try {
            final String jsonBody = this.gson.toJson(messagingPayload);
            final MessengerHttpClient.Response response = this.httpClient.executePost(this.requestUrl, jsonBody);
            final JsonObject responseJsonObject = this.jsonParser.parse(response.getBody()).getAsJsonObject();

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                return MessengerResponse.fromJson(responseJsonObject);
            } else {
                throw MessengerApiException.fromJson(responseJsonObject);
            }
        } catch (IOException e) {
            throw new MessengerIOException(e);
        }
    }
}