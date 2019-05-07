:sourcedir: src/test/java/com/github/messenger4j/test/integration
:m4j-version: 1.1.0
:toc: macro

= messenger4j

image:https://travis-ci.org/messenger4j/messenger4j.svg?branch=master[Build Status,link=https://travis-ci.org/messenger4j/messenger4j]
image:https://api.codacy.com/project/badge/Grade/b26d8f1fe4794b89b2ba439f35ac2af4[Codacy Badge,link=https://www.codacy.com/app/max_11/messenger4j?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=messenger4j/messenger4j&amp;utm_campaign=Badge_Grade]
image:https://coveralls.io/repos/github/messenger4j/messenger4j/badge.svg[Coverage Status,link=https://coveralls.io/github/messenger4j/messenger4j]
image:https://img.shields.io/badge/license-MIT-blue.svg[License Badge, link=LICENSE]

*A Java library for building Chatbots on the Facebook Messenger Platform.*

Using messenger4j is easy.
Its modern object-oriented API is fully Java 8 compatible, expresses optionality, and is designed with immutability in mind.
It is fast, powerful, and at roughly 180KB, the library is very light.

For more information on the Facebook Messenger Platform refer to the https://developers.facebook.com/docs/messenger-platform[official documentation].

_Please note that messenger4j 1.0.0 is a complete rewrite and has a lot of breaking changes. +
Thanks for all your valuable feedback and effort to make this library even better._

toc::[]

== Download
=== Maven
[source,xml]
[subs="+attributes"]
----
<dependency>
  <groupId>com.github.messenger4j</groupId>
  <artifactId>messenger4j</artifactId>
  <version>{m4j-version}</version>
</dependency>
----

=== Gradle
[source]
[subs="+attributes"]
----
dependencies {
  compile 'com.github.messenger4j:messenger4j:{m4j-version}'
}
----

== Echo Example

[source,java,indent=0]
----
    final String payload =
        "{\n"
            + "  \"object\": \"page\",\n"
            + "  \"entry\": [{\n"
            + "    \"id\": \"1717527131834678\",\n"
            + "    \"time\": 1475942721780,\n"
            + "    \"messaging\": [{\n"
            + "      \"sender\": {\n"
            + "        \"id\": \"1256217357730577\"\n"
            + "      },\n"
            + "      \"recipient\": {\n"
            + "        \"id\": \"1717527131834678\"\n"
            + "      },\n"
            + "      \"timestamp\": 1475942721741,\n"
            + "      \"message\": {\n"
            + "        \"mid\": \"mid.1475942721728:3b9e3646712f9bed52\",\n"
            + "        \"seq\": 123,\n"
            + "        \"text\": \"Hello Chatbot\"\n"
            + "      }\n"
            + "    }]\n"
            + "  }]\n"
            + "}";

    final Messenger messenger = Messenger.create("PAGE_ACCESS_TOKEN", "APP_SECRET", "VERIFY_TOKEN");

    messenger.onReceiveEvents(
        payload,
        Optional.empty(),
        event -> {
          final String senderId = event.senderId();
          if (event.isTextMessageEvent()) {
            final String text = event.asTextMessageEvent().text();

            final TextMessage textMessage = TextMessage.create(text);
            final MessagePayload messagePayload =
                MessagePayload.create(senderId, MessagingType.RESPONSE, textMessage);

            try {
              messenger.send(messagePayload);
            } catch (MessengerApiException | MessengerIOException e) {
              // Oops, something went wrong
            }
          }
        });
----

== Reference
=== Instantiation
==== with default HTTP-Client (okHttp)
[source,java,indent=0]
----
    final Messenger messenger = Messenger.create("PAGE_ACCESS_TOKEN", "APP_SECRET", "VERIFY_TOKEN");
----

==== with custom HTTP-Client
[source,java,indent=0]
----
    final MyCustomMessengerHttpClient customHttpClient = new MyCustomMessengerHttpClient();
    final Messenger messenger =
        Messenger.create(
            "PAGE_ACCESS_TOKEN", "APP_SECRET", "VERIFY_TOKEN", Optional.of(customHttpClient));
----

=== Webhook / Receive Events
==== helper for initial webhook verification request issued by Facebook
[source,java,indent=0]
----
    messenger.verifyWebhook(mode, verifyToken);
----

==== handle incoming text message
[source,java,indent=0]
----
    final String payload =
        "{\"object\":\"page\",\"entry\":[{\"id\":\"1717527131834678\",\"time\":1475942721780,"
            + "\"messaging\":[{\"sender\":{\"id\":\"1256217357730577\"},\"recipient\":{\"id\":\"1717527131834678\"},"
            + "\"timestamp\":1475942721741,\"message\":{\"mid\":\"mid.1475942721728:3b9e3646712f9bed52\","
            + "\"seq\":123,\"text\":\"34wrr3wr\"}}]}]}";
    final String signature = "sha1=3daa41999293ff66c3eb313e04bcf77861bb0276";

    messenger.onReceiveEvents(
        payload,
        of(signature),
        event -> {
          final String senderId = event.senderId();
          final Instant timestamp = event.timestamp();

          if (event.isTextMessageEvent()) {
            final TextMessageEvent textMessageEvent = event.asTextMessageEvent();
            final String messageId = textMessageEvent.messageId();
            final String text = textMessageEvent.text();

            log.debug(
                "Received text message from '{}' at '{}' with content: {} (mid: {})",
                senderId,
                timestamp,
                text,
                messageId);
          }
        });
----

==== handle incoming attachment message
[source,java,indent=0]
----
    final String payload =
        "{\n"
            + "    \"object\": \"page\",\n"
            + "    \"entry\": [{\n"
            + "        \"id\": \"PAGE_ID\",\n"
            + "        \"time\": 1458692752478,\n"
            + "        \"messaging\": [{\n"
            + "            \"sender\": {\n"
            + "                \"id\": \"USER_ID\"\n"
            + "            },\n"
            + "            \"recipient\": {\n"
            + "                \"id\": \"PAGE_ID\"\n"
            + "            },\n"
            + "            \"timestamp\": 1458692752478,\n"
            + "            \"message\": {\n"
            + "                \"mid\": \"mid.1458696618141:b4ef9d19ec21086067\",\n"
            + "                \"attachments\": [{\n"
            + "                    \"type\": \"image\",\n"
            + "                    \"payload\": {\n"
            + "                        \"url\": \"http://image.url\"\n"
            + "                    }\n"
            + "                }, {\n"
            + "                   \"type\":\"fallback\",\n"
            + "                   \"payload\":null,\n"
            + "                   \"title\":\"<TITLE_OF_THE_URL_ATTACHMENT>\",\n"
            + "                   \"URL\":\"<URL_OF_THE_ATTACHMENT>\"\n"
            + "                }, {\n"
            + "                    \"type\": \"location\",\n"
            + "                    \"payload\": {\n"
            + "                        \"coordinates\": {\n"
            + "                            \"lat\": 52.3765533,\n"
            + "                            \"long\": 9.7389123\n"
            + "                        }\n"
            + "                    }\n"
            + "                }]\n"
            + "            }\n"
            + "        }]\n"
            + "    }]\n"
            + "}";

    messenger.onReceiveEvents(
        payload,
        Optional.empty(),
        event -> {
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
----

==== more event types
In addition to the event types described above the following events are also supported:

* `PostbackEvent`
* `QuickReplyMessageEvent`
* `ReferralEvent`
* `OptInEvent`
* `AccountLinkingEvent`
* `MessageDeliveredEvent`
* `MessageReadEvent`
* `MessageEchoEvent`

=== Send API
==== send sender action
[source,java,indent=0]
----
    final String recipientId = "USER_ID";
    final SenderAction senderAction = SenderAction.MARK_SEEN;

    final SenderActionPayload payload = SenderActionPayload.create(recipientId, senderAction);

    messenger.send(payload);
----

==== send text message
[source,java,indent=0]
----
    final String recipientId = "USER_ID";
    final String text = "Hello Messenger Platform";

    final MessagePayload payload =
        MessagePayload.create(recipientId, MessagingType.RESPONSE, TextMessage.create(text));

    messenger.send(payload);
----

==== send text message with notification type and message tag
[source,java,indent=0]
----
    final Recipient recipient = IdRecipient.create("USER_ID");
    final TextMessage message = TextMessage.create("Hello Messenger Platform");
    final NotificationType notificationType = NotificationType.SILENT_PUSH;
    final MessageTag messageTag = MessageTag.APPLICATION_UPDATE;

    final MessagePayload payload =
        MessagePayload.create(
            recipient, MessagingType.RESPONSE, message, of(notificationType), of(messageTag));

    messenger.send(payload);
----

==== send text message with quick replies
[source,java,indent=0]
----
    final IdRecipient recipient = IdRecipient.create("<PSID>");

    final String text = "Here is a quick reply!";

    final TextQuickReply quickReplyA =
        TextQuickReply.create(
            "Search", "<POSTBACK_PAYLOAD>", of(new URL("http://example.com/img/red.png")));
    final LocationQuickReply quickReplyB = LocationQuickReply.create();
    final TextQuickReply quickReplyC =
        TextQuickReply.create("Something Else", "<POSTBACK_PAYLOAD>");

    final List<QuickReply> quickReplies = Arrays.asList(quickReplyA, quickReplyB, quickReplyC);

    final TextMessage message = TextMessage.create(text, of(quickReplies), empty());
    final MessagePayload payload =
        MessagePayload.create(recipient, MessagingType.RESPONSE, message);

    messenger.send(payload);
----

==== send text message with metadata
[source,java,indent=0]
----
    final IdRecipient recipient = IdRecipient.create("USER_ID");
    final NotificationType notificationType = NotificationType.SILENT_PUSH;
    final String text = "Hello Messenger Platform";
    final String metadata = "DEVELOPER_DEFINED_METADATA";

    final TextMessage textMessage = TextMessage.create(text, empty(), of(metadata));
    final MessagePayload payload =
        MessagePayload.create(
            recipient, MessagingType.RESPONSE, textMessage, of(notificationType), empty());

    messenger.send(payload);
----

==== send image attachment message using a URL
[source,java,indent=0]
----
    final String recipientId = "USER_ID";
    final String imageUrl = "https://petersapparel.com/img/shirt.png";

    final UrlRichMediaAsset richMediaAsset = UrlRichMediaAsset.create(IMAGE, new URL(imageUrl));
    final RichMediaMessage richMediaMessage = RichMediaMessage.create(richMediaAsset);
    final MessagePayload payload =
        MessagePayload.create(recipientId, MessagingType.RESPONSE, richMediaMessage);

    messenger.send(payload);
----

==== send reusable image attachment message using a URL
[source,java,indent=0]
----
    final IdRecipient recipient = IdRecipient.create("USER_ID");
    final NotificationType notificationType = NotificationType.NO_PUSH;
    final String imageUrl = "https://petersapparel.com/img/shirt.png";

    final UrlRichMediaAsset richMediaAsset =
        UrlRichMediaAsset.create(IMAGE, new URL(imageUrl), of(true));
    final RichMediaMessage richMediaMessage = RichMediaMessage.create(richMediaAsset);
    final MessagePayload payload =
        MessagePayload.create(
            recipient, MessagingType.RESPONSE, richMediaMessage, of(notificationType), empty());

    messenger.send(payload);
----

==== send image attachment message using an attachment ID
[source,java,indent=0]
----
    final IdRecipient recipient = IdRecipient.create("USER_ID");
    final NotificationType notificationType = NotificationType.NO_PUSH;
    final String attachmentId = "1745504518999123";

    final ReusableRichMediaAsset richMediaAsset =
        ReusableRichMediaAsset.create(IMAGE, attachmentId);
    final RichMediaMessage richMediaMessage = RichMediaMessage.create(richMediaAsset);
    final MessagePayload payload =
        MessagePayload.create(
            recipient, MessagingType.RESPONSE, richMediaMessage, of(notificationType), empty());

    messenger.send(payload);
----

==== send button template
[source,java,indent=0]
----
    final String recipientId = "USER_ID";

    final UrlButton buttonA =
        UrlButton.create("Show Website", new URL("https://petersapparel.parseapp.com"));
    final PostbackButton buttonB = PostbackButton.create("Start Chatting", "USER_DEFINED_PAYLOAD");
    final UrlButton buttonC =
        UrlButton.create(
            "Show Website",
            new URL("https://petersapparel.parseapp.com"),
            of(WebviewHeightRatio.FULL),
            of(true),
            of(new URL("https://petersfancyapparel.com/fallback")),
            empty());

    final List<Button> buttons = Arrays.asList(buttonA, buttonB, buttonC);
    final ButtonTemplate buttonTemplate =
        ButtonTemplate.create("What do you want to do next?", buttons);

    final TemplateMessage templateMessage = TemplateMessage.create(buttonTemplate);
    final MessagePayload payload =
        MessagePayload.create(recipientId, MessagingType.RESPONSE, templateMessage);

    messenger.send(payload);
----

==== send generic template with buttons
[source,java,indent=0]
----
    final String recipientId = "USER_ID";

    final List<Button> buttons =
        Arrays.asList(
            UrlButton.create(
                "Select Criteria",
                new URL("https://petersfancyapparel.com/criteria_selector"),
                of(WebviewHeightRatio.FULL),
                of(true),
                of(new URL("https://petersfancyapparel.com/fallback")),
                empty()),
            CallButton.create("Call Representative", "+15105551234"),
            PostbackButton.create("Start Chatting", "DEVELOPER_DEFINED_PAYLOAD"));

    final DefaultAction defaultAction =
        DefaultAction.create(
            new URL("https://peterssendreceiveapp.ngrok.io/view?item=103"),
            of(WebviewHeightRatio.TALL),
            of(true),
            of(new URL("https://peterssendreceiveapp.ngrok.io/")),
            of(WebviewShareButtonState.HIDE));

    final Element element =
        Element.create(
            "Welcome to Peters Hats",
            of("We have got the right hat for everyone."),
            of(new URL("https://petersfancybrownhats.com/company_image.png")),
            of(defaultAction),
            of(buttons));

    final GenericTemplate genericTemplate = GenericTemplate.create(singletonList(element));

    final MessagePayload payload =
        MessagePayload.create(
            recipientId, MessagingType.RESPONSE, TemplateMessage.create(genericTemplate));

    messenger.send(payload);
----

==== send receipt template
[source,java,indent=0]
----
    final String recipientId = "USER_ID";

    final Adjustment adjustment1 = Adjustment.create("New Customer Discount", 20.00F);
    final Adjustment adjustment2 = Adjustment.create("$10 Off Coupon", 10.00F);

    final Item item1 =
        Item.create(
            "Classic White T-Shirt",
            50F,
            of("100% Soft and Luxurious Cotton"),
            of(2),
            of("USD"),
            of(new URL("http://petersapparel.parseapp.com/img/whiteshirt.png")));

    final Item item2 =
        Item.create(
            "Classic Gray T-Shirt",
            25F,
            of("100% Soft and Luxurious Cotton"),
            of(1),
            of("USD"),
            of(new URL("http://petersapparel.parseapp.com/img/grayshirt.png")));

    final Address address =
        Address.create("1 Hacker Way", of(""), "Menlo Park", "94025", "CA", "US");
    final Summary summary = Summary.create(56.14F, of(75.00F), of(6.19F), of(4.95F));

    final ReceiptTemplate receiptTemplate =
        ReceiptTemplate.create(
            "Stephane Crozatier",
            "12345678902",
            "Visa 2345",
            "USD",
            summary,
            of(address),
            of(Arrays.asList(item1, item2)),
            of(Arrays.asList(adjustment1, adjustment2)),
            empty(),
            of(new URL("http://petersapparel.parseapp.com/order?order_id=123456")),
            empty(),
            of(ZonedDateTime.of(2015, 4, 7, 22, 14, 12, 0, ZoneOffset.UTC).toInstant()));

    final MessagePayload payload =
        MessagePayload.create(
            recipientId, MessagingType.RESPONSE, TemplateMessage.create(receiptTemplate));

    messenger.send(payload);
----

==== send list template
[source,java,indent=0]
----
    final String recipientId = "USER_ID";

    final Element element1 =
        Element.create(
            "Classic T-Shirt Collection",
            of("See all our colors"),
            of(new URL("https://peterssendreceiveapp.ngrok.io/img/collection.png")),
            of(
                DefaultAction.create(
                    new URL("https://peterssendreceiveapp.ngrok.io/shop_collection"),
                    of(WebviewHeightRatio.TALL),
                    of(true),
                    of(new URL("https://peterssendreceiveapp.ngrok.io/fallback")),
                    empty())),
            of(
                singletonList(
                    UrlButton.create(
                        "View",
                        new URL("https://peterssendreceiveapp.ngrok.io/collection"),
                        of(WebviewHeightRatio.TALL),
                        empty(),
                        empty(),
                        empty()))));

    final Element element2 =
        Element.create(
            "Classic White T-Shirt",
            of("100% Cotton, 200% Comfortable"),
            of(new URL("https://peterssendreceiveapp.ngrok.io/img/white-t-shirt.png")),
            of(
                DefaultAction.create(
                    new URL("https://peterssendreceiveapp.ngrok.io/view?item=100"),
                    of(WebviewHeightRatio.TALL),
                    empty(),
                    empty(),
                    empty())),
            of(
                singletonList(
                    UrlButton.create(
                        "Shop Now",
                        new URL("https://peterssendreceiveapp.ngrok.io/shop?item=100"),
                        of(WebviewHeightRatio.TALL),
                        empty(),
                        empty(),
                        empty()))));

    final Element element3 =
        Element.create(
            "Classic Blue T-Shirt",
            of("100% Cotton, 200% Comfortable"),
            of(new URL("https://peterssendreceiveapp.ngrok.io/img/blue-t-shirt.png")),
            of(
                DefaultAction.create(
                    new URL("https://peterssendreceiveapp.ngrok.io/view?item=101"),
                    of(WebviewHeightRatio.TALL),
                    empty(),
                    empty(),
                    empty())),
            of(
                singletonList(
                    UrlButton.create(
                        "Shop Now",
                        new URL("https://peterssendreceiveapp.ngrok.io/shop?item=101"),
                        of(WebviewHeightRatio.TALL),
                        empty(),
                        empty(),
                        empty()))));

    final Element element4 =
        Element.create(
            "Classic Black T-Shirt",
            of("100% Cotton, 200% Comfortable"),
            of(new URL("https://peterssendreceiveapp.ngrok.io/img/black-t-shirt.png")),
            of(
                DefaultAction.create(
                    new URL("https://peterssendreceiveapp.ngrok.io/view?item=102"),
                    of(WebviewHeightRatio.TALL),
                    empty(),
                    empty(),
                    empty())),
            of(
                singletonList(
                    UrlButton.create(
                        "Shop Now",
                        new URL("https://peterssendreceiveapp.ngrok.io/shop?item=102"),
                        of(WebviewHeightRatio.TALL),
                        empty(),
                        empty(),
                        empty()))));

    final ListTemplate listTemplate =
        ListTemplate.create(
            Arrays.asList(element1, element2, element3, element4),
            of(TopElementStyle.LARGE),
            of(singletonList(PostbackButton.create("View More", "payload"))));

    messenger.send(
        MessagePayload.create(
            recipientId, MessagingType.RESPONSE, TemplateMessage.create(listTemplate)));
----

==== send open graph template
[source,java,indent=0]
----
    final String recipientId = "USER_ID";

    final UrlButton urlButton =
        UrlButton.create("View More", new URL("https://en.wikipedia.org/wiki/Rickrolling"));
    final OpenGraphObject openGraphObject =
        OpenGraphObject.create(
            new URL("https://open.spotify.com/track/7GhIk7Il098yCjg4BQjzvb"),
            of(singletonList(urlButton)));
    final OpenGraphTemplate openGraphTemplate =
        OpenGraphTemplate.create(singletonList(openGraphObject));

    messenger.send(
        MessagePayload.create(
            recipientId, MessagingType.RESPONSE, TemplateMessage.create(openGraphTemplate)));
----

==== handle successful response
[source,java,indent=0]
----
    final UrlRichMediaAsset richMediaAsset =
        UrlRichMediaAsset.create(IMAGE, new URL("http://image.url"), of(true));
    final MessagePayload payload =
        MessagePayload.create(
            "USER_ID", MessagingType.RESPONSE, RichMediaMessage.create(richMediaAsset));

    final MessageResponse messageResponse = messenger.send(payload);

    final Optional<String> recipientId = messageResponse.recipientId();
    final Optional<String> messageId = messageResponse.messageId();
    final Optional<String> attachmentId = messageResponse.attachmentId();
    log.debug(
        "RecipientId: {} | MessageId: {} | AttachmentId: {}", recipientId, messageId, attachmentId);
----

=== User Profile API
==== query user information by user ID
[source,java,indent=0]
----
    final UserProfile userProfile = messenger.queryUserProfile(userId);
----

=== Messenger Profile API
==== set / update Get Started button
[source,java,indent=0]
----
    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            of(StartButton.create("Button pressed")),
            empty(),
            empty(),
            empty(),
            empty(),
            empty(),
            empty());

    messenger.updateSettings(messengerSettings);
----

==== delete Get Started button
[source,java,indent=0]
----
    messenger.deleteSettings(MessengerSettingProperty.START_BUTTON);
----

==== set / update greeting text
[source,java,indent=0]
----
    final Greeting greeting =
        Greeting.create(
            "Hello!",
            LocalizedGreeting.create(SupportedLocale.en_US, "Timeless apparel for the masses."));
    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(), of(greeting), empty(), empty(), empty(), empty(), empty());

    messenger.updateSettings(messengerSettings);
----

==== delete greeting text
[source,java,indent=0]
----
    messenger.deleteSettings(MessengerSettingProperty.GREETING);
----

==== set / update persistent menu
[source,java,indent=0]
----
    final PostbackCallToAction callToActionAA =
        PostbackCallToAction.create("Pay Bill", "PAYBILL_PAYLOAD");
    final PostbackCallToAction callToActionAB =
        PostbackCallToAction.create("History", "HISTORY_PAYLOAD");
    final PostbackCallToAction callToActionAC =
        PostbackCallToAction.create("Contact Info", "CONTACT_INFO_PAYLOAD");

    final NestedCallToAction callToActionA =
        NestedCallToAction.create(
            "My Account", Arrays.asList(callToActionAA, callToActionAB, callToActionAC));

    final UrlCallToAction callToActionB =
        UrlCallToAction.create(
            "Latest News",
            new URL("http://petershats.parseapp.com/hat-news"),
            of(WebviewHeightRatio.FULL),
            empty(),
            empty(),
            of(WebviewShareButtonState.HIDE));

    final PersistentMenu persistentMenu =
        PersistentMenu.create(
            true,
            of(Arrays.asList(callToActionA, callToActionB)),
            LocalizedPersistentMenu.create(SupportedLocale.zh_CN, false, empty()));

    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(), empty(), of(persistentMenu), empty(), empty(), empty(), empty());

    messenger.updateSettings(messengerSettings);
----

==== delete persistent menu
[source,java,indent=0]
----
    messenger.deleteSettings(MessengerSettingProperty.PERSISTENT_MENU);
----

==== set / update whitelisted domains
[source,java,indent=0]
----
    final List<URL> whitelistedDomains =
        Arrays.asList(new URL("http://example.url"), new URL("http://second-example.url"));

    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(), empty(), empty(), of(whitelistedDomains), empty(), empty(), empty());

    messenger.updateSettings(messengerSettings);
----

==== delete whitelisted domains
[source,java,indent=0]
----
    messenger.deleteSettings(MessengerSettingProperty.WHITELISTED_DOMAINS);
----

==== set / update account linking url
[source,java,indent=0]
----
    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(),
            empty(),
            empty(),
            empty(),
            of(new URL("http://example.url")),
            empty(),
            empty());

    messenger.updateSettings(messengerSettings);
----

==== delete account linking url
[source,java,indent=0]
----
    messenger.deleteSettings(MessengerSettingProperty.ACCOUNT_LINKING_URL);
----

==== set / update home url
[source,java,indent=0]
----
    final HomeUrl homeUrl =
        HomeUrl.create(new URL("http://example.url"), true, of(WebviewShareButtonState.HIDE));

    final MessengerSettings messengerSettings =
        MessengerSettings.create(empty(), empty(), empty(), empty(), empty(), of(homeUrl), empty());

    messenger.updateSettings(messengerSettings);
----

==== delete home url
[source,java,indent=0]
----
    messenger.deleteSettings(MessengerSettingProperty.HOME_URL);
----

==== set / update target audience (open to all)
[source,java,indent=0]
----
    final AllTargetAudience allTargetAudience = AllTargetAudience.create();

    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(), empty(), empty(), empty(), empty(), empty(), of(allTargetAudience));

    messenger.updateSettings(messengerSettings);
----

==== set / update target audience (closed to all)
[source,java,indent=0]
----
    final NoneTargetAudience noneTargetAudience = NoneTargetAudience.create();

    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(), empty(), empty(), empty(), empty(), empty(), of(noneTargetAudience));

    messenger.updateSettings(messengerSettings);
----

==== set / update target audience (custom whitelist)
[source,java,indent=0]
----
    final WhitelistTargetAudience whitelistTargetAudience =
        WhitelistTargetAudience.create(Arrays.asList(SupportedCountry.US, SupportedCountry.CA));

    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(), empty(), empty(), empty(), empty(), empty(), of(whitelistTargetAudience));

    messenger.updateSettings(messengerSettings);
----

==== set / update target audience (custom blacklist)
[source,java,indent=0]
----
    final BlacklistTargetAudience blacklistTargetAudience =
        BlacklistTargetAudience.create(Arrays.asList(SupportedCountry.US, SupportedCountry.CA));

    final MessengerSettings messengerSettings =
        MessengerSettings.create(
            empty(), empty(), empty(), empty(), empty(), empty(), of(blacklistTargetAudience));

    messenger.updateSettings(messengerSettings);
----

==== delete target audience
[source,java,indent=0]
----
    messenger.deleteSettings(MessengerSettingProperty.TARGET_AUDIENCE);
----

== Requirements
* Java 8+
* slf4j
* Gson
* okHttp (optional => HTTP-Client is pluggable)

== Contributing
Contributions are very welcome!
Please perform changes and submit pull requests from the `develop` branch instead of `master`, and open an issue before start working.
When submitting code, please make every effort to follow existing conventions and style in order to keep the code as readable as possible.
Please also make sure your code compiles by running `mvn clean verify`.

== License
This project is licensed under the terms of the link:LICENSE[MIT license].
