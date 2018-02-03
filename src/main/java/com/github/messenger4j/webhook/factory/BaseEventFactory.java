package com.github.messenger4j.webhook.factory;

import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_AD_ID;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_IDENTIFIER;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_REF;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_SOURCE;
import static com.github.messenger4j.internal.gson.GsonUtil.Constants.PROP_TYPE;
import static com.github.messenger4j.internal.gson.GsonUtil.getPropertyAsString;

import com.github.messenger4j.webhook.event.BaseEvent;
import com.github.messenger4j.webhook.event.common.PriorMessage;
import com.github.messenger4j.webhook.event.common.Referral;
import com.google.gson.JsonObject;
import java.util.Optional;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
interface BaseEventFactory<E extends BaseEvent> {

    boolean isResponsible(JsonObject messagingEvent);

    E createEventFromJson(JsonObject messagingEvent);

    default PriorMessage getPriorMessageFromJsonObject(JsonObject jsonObject) {
        final String source = getPropertyAsString(jsonObject, PROP_SOURCE)
                .orElseThrow(IllegalArgumentException::new);
        final String identifier = getPropertyAsString(jsonObject, PROP_IDENTIFIER)
                .orElseThrow(IllegalArgumentException::new);
        return new PriorMessage(source, identifier);
    }

    default Referral createReferralFromJson(JsonObject jsonObject) {
        final String source = getPropertyAsString(jsonObject, PROP_SOURCE).orElseThrow(IllegalArgumentException::new);
        final String type = getPropertyAsString(jsonObject, PROP_TYPE).orElseThrow(IllegalArgumentException::new);
        final Optional<String> refPayload = getPropertyAsString(jsonObject, PROP_REF);
        final Optional<String> adId = getPropertyAsString(jsonObject, PROP_AD_ID);

        return new Referral(source, type, refPayload, adId);
    }
}
