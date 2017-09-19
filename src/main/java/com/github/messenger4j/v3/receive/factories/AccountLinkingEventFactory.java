package com.github.messenger4j.v3.receive.factories;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ACCOUNT_LINKING;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_AUTHORIZATION_CODE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_STATUS;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsInstant;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.v3.receive.AccountLinkingEvent;
import com.google.gson.JsonObject;
import java.time.Instant;
import lombok.NonNull;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class AccountLinkingEventFactory implements BaseEventFactory<AccountLinkingEvent> {

    @Override
    public boolean isResponsible(@NonNull JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_ACCOUNT_LINKING);
    }

    @Override
    public AccountLinkingEvent createEventFromJson(@NonNull JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP).get();
        final String status = getPropertyAsString(messagingEvent, PROP_ACCOUNT_LINKING, PROP_STATUS);
        final String authorizationCode = getPropertyAsString(messagingEvent, PROP_ACCOUNT_LINKING, PROP_AUTHORIZATION_CODE);

        final AccountLinkingEvent.Status accountLinkingStatus = (status == null ? null :
                AccountLinkingEvent.Status.valueOf(status.toUpperCase()));
        return new AccountLinkingEvent(senderId, recipientId, timestamp, accountLinkingStatus, authorizationCode);
    }
}
