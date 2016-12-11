package com.github.messenger4j.receive;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.internal.PreConditions;
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
import com.github.messenger4j.receive.handlers.EventHandler;
import com.github.messenger4j.receive.handlers.FallbackEventHandler;
import com.github.messenger4j.receive.handlers.MessageDeliveredEventHandler;
import com.github.messenger4j.receive.handlers.MessageReadEventHandler;
import com.github.messenger4j.receive.handlers.OptInEventHandler;
import com.github.messenger4j.receive.handlers.PostbackEventHandler;
import com.github.messenger4j.receive.handlers.QuickReplyMessageEventHandler;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;

/**
 * The {@code MessengerReceiveClientBuilder} is used to build instances of {@link MessengerReceiveClient} from
 * the provided values.
 *
 * <p>
 * It checks if the provided values satisfy the requirements. A {@link MessengerReceiveClient} object
 * created by a {@code MessengerReceiveClientBuilder} is well-configured.<br>
 * For each event that should be handled by your application you can provide the specific {@link EventHandler}.
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see MessengerReceiveClient
 * @see MessengerPlatform
 */
public final class MessengerReceiveClientBuilder {

    final String appSecret;
    final String verifyToken;
    boolean disableSignatureVerification;
    AttachmentMessageEventHandler attachmentMessageEventHandler;
    OptInEventHandler optInEventHandler;
    EchoMessageEventHandler echoMessageEventHandler;
    QuickReplyMessageEventHandler quickReplyMessageEventHandler;
    TextMessageEventHandler textMessageEventHandler;
    PostbackEventHandler postbackEventHandler;
    AccountLinkingEventHandler accountLinkingEventHandler;
    MessageReadEventHandler messageReadEventHandler;
    MessageDeliveredEventHandler messageDeliveredEventHandler;
    FallbackEventHandler fallbackEventHandler;

    /**
     * Constructs the builder. All parameter values are required. No {@link EventHandler} is set.
     *
     * @param appSecret   the {@code Application Secret} of your {@code Facebook App} connected to your {@code Facebook Page}
     * @param verifyToken the {@code Verification Token} that has been provided by you during the setup of the {@code Webhook}
     */
    public MessengerReceiveClientBuilder(String appSecret, String verifyToken) {
        PreConditions.notNullOrBlank(appSecret, "appSecret");
        PreConditions.notNullOrBlank(verifyToken, "verifyToken");

        this.appSecret = appSecret;
        this.verifyToken = verifyToken;
    }

    /**
     * Disables the signature verification. The signature verification ensures the integrity and origin of the payload.
     *
     * <p>
     * <b>Disabling the signature verification is not recommended!</b>
     * </p>
     *
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder disableSignatureVerification() {
        this.disableSignatureVerification = true;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link AttachmentMessageEvent}.
     *
     * @param attachmentMessageEventHandler an {@code AttachmentMessageEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onAttachmentMessageEvent(AttachmentMessageEventHandler attachmentMessageEventHandler) {
        this.attachmentMessageEventHandler = attachmentMessageEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link OptInEvent}.
     *
     * @param optInEventHandler an {@code OptInEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onOptInEvent(OptInEventHandler optInEventHandler) {
        this.optInEventHandler = optInEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link EchoMessageEvent}.
     *
     * @param echoMessageEventHandler an {@code EchoMessageEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onEchoMessageEvent(EchoMessageEventHandler echoMessageEventHandler) {
        this.echoMessageEventHandler = echoMessageEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link QuickReplyMessageEvent}.
     *
     * @param quickReplyMessageEventHandler a {@code QuickReplyMessageEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onQuickReplyMessageEvent(QuickReplyMessageEventHandler quickReplyMessageEventHandler) {
        this.quickReplyMessageEventHandler = quickReplyMessageEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link TextMessageEvent}.
     *
     * @param textMessageEventHandler a {@code TextMessageEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onTextMessageEvent(TextMessageEventHandler textMessageEventHandler) {
        this.textMessageEventHandler = textMessageEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link PostbackEvent}.
     *
     * @param postbackEventHandler a {@code PostbackEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onPostbackEvent(PostbackEventHandler postbackEventHandler) {
        this.postbackEventHandler = postbackEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link AccountLinkingEvent}.
     *
     * @param accountLinkingEventHandler an {@code AccountLinkingEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onAccountLinkingEvent(AccountLinkingEventHandler accountLinkingEventHandler) {
        this.accountLinkingEventHandler = accountLinkingEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link MessageReadEvent}.
     *
     * @param messageReadEventHandler a {@code MessageReadEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onMessageReadEvent(MessageReadEventHandler messageReadEventHandler) {
        this.messageReadEventHandler = messageReadEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link MessageDeliveredEvent}.
     *
     * @param messageDeliveredEventHandler a {@code MessageDeliveredEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder onMessageDeliveredEvent(MessageDeliveredEventHandler messageDeliveredEventHandler) {
        this.messageDeliveredEventHandler = messageDeliveredEventHandler;
        return this;
    }

    /**
     * Sets the {@link EventHandler} responsible for handling the {@link FallbackEvent}.
     *
     * @param fallbackEventHandler a {@code FallbackEventHandler}
     * @return the {@code MessengerReceiveClientBuilder}
     */
    public MessengerReceiveClientBuilder fallbackEventHandler(FallbackEventHandler fallbackEventHandler) {
        this.fallbackEventHandler = fallbackEventHandler;
        return this;
    }

    /**
     * Returns an instance of {@code MessengerReceiveClient} created from the fields set on this builder.
     *
     * @return a {@code MessengerReceiveClient}
     */
    public MessengerReceiveClient build() {
        return new MessengerReceiveClientImpl(this);
    }
}