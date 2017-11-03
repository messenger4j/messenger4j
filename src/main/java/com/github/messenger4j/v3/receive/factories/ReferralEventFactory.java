package com.github.messenger4j.v3.receive.factories;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_AD_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_REF;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_REFERRAL;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SOURCE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsInstant;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsJsonObject;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.hasProperty;

import com.github.messenger4j.v3.receive.Referral;
import com.github.messenger4j.v3.receive.ReferralEvent;
import com.google.gson.JsonObject;
import java.time.Instant;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class ReferralEventFactory implements BaseEventFactory<ReferralEvent> {

    @Override
    public boolean isResponsible(JsonObject messagingEvent) {
        return hasProperty(messagingEvent, PROP_REFERRAL);
    }

    @Override
    public ReferralEvent createEventFromJson(JsonObject messagingEvent) {
        final String senderId = getPropertyAsString(messagingEvent, PROP_SENDER, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final String recipientId = getPropertyAsString(messagingEvent, PROP_RECIPIENT, PROP_ID)
                .orElseThrow(IllegalArgumentException::new);
        final Instant timestamp = getPropertyAsInstant(messagingEvent, PROP_TIMESTAMP)
                .orElseThrow(IllegalArgumentException::new);
        final Referral referral = getPropertyAsJsonObject(messagingEvent, PROP_REFERRAL)
                .map(jsonObject -> {
                    final String source = getPropertyAsString(jsonObject, PROP_SOURCE).orElseThrow(IllegalArgumentException::new);
                    final String type = getPropertyAsString(jsonObject, PROP_TYPE).orElseThrow(IllegalArgumentException::new);
                    final Optional<String> refPayload = getPropertyAsString(jsonObject, PROP_REF);
                    final Optional<String> adId = getPropertyAsString(jsonObject, PROP_AD_ID);

                    return new Referral(source, type, refPayload, adId);
                }).orElseThrow(IllegalArgumentException::new);

        return new ReferralEvent(senderId, recipientId, timestamp, referral);
    }
}
