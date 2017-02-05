# messenger4j: A Java library for the Messenger Platform

If you are excited about building Chatbots on the Facebook Messenger Platform, messenger4j is all you need ✌

>It's fast, lightweight, feature-rich, and easy to use.

For more information on the Facebook Messenger Platform refer to the [official documentation][1].

## Features
* Full supported Receive API
    + Webhook verification
    + Signature verification
    + Automatic message / event type detection
    + Handler-based processing of messages / events
    + Fallback-mechanisms
* Full supported Send API
    + Text, Attachments, Buttons, Templates, Quick Replies - everything you need
    + Notification type support
    + Webview support
    + Fluent builder APIs
    + Pluggable HTTP-Client (`okHttp` is the default)
* Thread Settings support
    + Get Started Button
    + Greeting Text
    + Persistent Menu
* User Profile API support
* Messenger Extensions support
* JDK 7+ compatible
* JDK 8 lambda support
* 3 dependencies:
    + slf4j-api
    + gson
    + okHttp (optional)
* 150 kB JAR

## Binaries
To add a dependency on messenger4j using Maven, use the following:

```xml
<dependency>
  <groupId>com.github.messenger4j</groupId>
  <artifactId>messenger4j</artifactId>
  <version>0.8.0</version>
</dependency>
```

To add a dependency using Gradle:

```
dependencies {
  compile 'com.github.messenger4j:messenger4j:0.8.0'
}
```

## Showcase
For a complete sample application showcasing many features of the Messenger Platform take a look at the
[messenger4j-spring-boot-quickstart-template][2]. You can use it to quickly bootstrap your chatbot projects.

## Examples
> For almost all supported features you can take a look at the integration tests (`src/test/java/.../test/integration`) for a working example.

#### Receiving
Let's see how to handle an inbound text message:

```java
String payload = ... // callback request body
String signature = ... // 'X-Hub-Signature' request header

// JDK 8 version
MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder("APP_SECRET", "VERIFICATION_TOKEN")
        .onTextMessageEvent(event ->  System.out.printf("%s: %s", event.getSender().getId(), event.getText()))
        .build();

// JDK 7 version
MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder("APP_SECRET", "VERIFICATION_TOKEN")
        .onTextMessageEvent(new TextMessageEventHandler() {
            @Override
            public void handle(TextMessageEvent event) {
                System.out.printf("%s: %s", event.getSender().getId(), event.getText());
            }
        })
        .build();

receiveClient.processCallbackPayload(payload, signature);
```

But what if you are receiving an image / video / ... attachment?

Either register an `AttachmentMessageEventHandler` as well:
```java
MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder("APP_SECRET", "VERIFICATION_TOKEN")
        .onTextMessageEvent(event -> {
            System.out.printf("%s: %s", event.getSender().getId(), event.getText());
        })
        .onAttachmentMessageEvent(event -> {
            event.getAttachments().forEach(attachment -> System.out.println(attachment.getType()));
        })
        .build();
```
or register a `FallbackEventHandler` that handles all inbound messages or events for which no `EventHandler` has been registered:
```java
MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder("APP_SECRET", "VERIFICATION_TOKEN")
        .onTextMessageEvent(event -> {
            System.out.printf("%s: %s", event.getSender().getId(), event.getText());
        })
        .fallbackEventHandler(event -> {
            System.out.printf("%s: Sorry, cannot handle your request!" + event.getSender().getId());
        })
        .build();
```

In addition the following handlers are supported: 
* `AccountLinkingEventHandler`
* `EchoMessageEventHandler`
* `MessageDeliveredEventHandler`
* `MessageReadEventHandler`
* `OptInEventHandler`
* `PostbackEventHandler`
* `QuickReplyMessageEventHandler`

#### Sending
What's about sending messages back to the user?

Sending a text message is as simple as: 

```java
MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder("PAGE_ACCESS_TOKEN").build();
sendClient.sendTextMessage("RECIPIENT_ID", "Hi there, how are you today?");
```
And if you want to add `Quick Replies` to your response, just do the following:
```java
List<QuickReply> quickReplies = QuickReply.newListBuilder()
        .addTextQuickReply("great", "GREAT_PAYLOAD").toList()
        .addTextQuickReply("brilliant", "BRILLIANT_PAYLOAD").imageUrl("http://thumb-up-image.url").toList()
        .addLocationQuickReply().toList()
        .build();
        
sendClient.sendTextMessage("RECIPIENT_ID", "Hi there, how are you today?", quickReplies);
```

And of course - `Templates`. Creating them using the fluent builder API is super easy:
```java
ReceiptTemplate receipt = ReceiptTemplate.newBuilder("Stephane Crozatier", "12345678902", "USD", "Visa 2345")
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
        
sendClient.sendTemplate("RECIPIENT_ID", receipt);
```
```java
List<Button> buttons = Button.newListBuilder()
        .addUrlButton("View Website", "https://petersfancybrownhats.com").toList()
        .addPostbackButton("Start Chatting", "DEVELOPER_DEFINED_PAYLOAD").toList()
        .build();

GenericTemplate genericTemplate = GenericTemplate.newBuilder()
        .addElements()
            .addElement("Welcome to Peters Hats")
                .itemUrl("https://petersfancybrownhats.com")
                .imageUrl("https://petersfancybrownhats.com/company_image.png")
                .subtitle("We have got the right hat for everyone.")
                .buttons(buttons)
                .toList()
        .done()
        .build();

sendClient.sendTemplate("RECIPIENT_ID", genericTemplate);
```

In addition the following methods are available:
* `sendSenderAction`
* `sendImageAttachment`
* `sendAudioAttachment`
* `sendVideoAttachment`
* `sendFileAttachment`
* `sendBinaryAttachment` - for reusable attachments

#### The Echo Example
```java
MessengerSendClient sendClient = MessengerPlatform.newSendClientBuilder("PAGE_ACCESS_TOKEN").build();
        
MessengerReceiveClient receiveClient = MessengerPlatform.newReceiveClientBuilder("APP_SECRET", "VERIFICATION_TOKEN")
        .onTextMessageEvent(event -> {
            try {
                sendClient.sendTextMessage(event.getSender().getId(), "Echo: " + event.getText());
            } catch (MessengerApiException | MessengerIOException e) {
                // Oops, something went wrong
            }
        })
        .build();

receiveClient.processCallbackPayload(payload, signature);
```

## Things to do
Unordered list of planned improvements: 
* comprehensive documentation
* Receive API:
    + Referral support
* Send API: 
    + JavaDoc
    + Airline Templates
    + *upload* Binary Attachments
* Payment support
* Checkbox Plugin support

## License
This project is licensed under the terms of the [MIT license](LICENSE).


[1]: https://developers.facebook.com/docs/messenger-platform
[2]: https://github.com/messenger4j/messenger4j-spring-boot-quickstart-template
