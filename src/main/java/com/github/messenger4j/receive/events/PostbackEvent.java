package com.github.messenger4j.receive.events;

import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsJsonObject;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_PAYLOAD;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_POSTBACK;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_REFERRAL;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import com.github.messenger4j.receive.events.ReferralEvent.Referral;
import com.google.gson.JsonObject;

/**
 * This event will occur when a {@code Postback button}, {@code Get Started button}, {@code Persistent menu}
 * or {@code Structured Message} is tapped.
 *
 * <p>
 * For further information refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/postback">
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/postback
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see Event
 */
public final class PostbackEvent extends TimestampedEvent {

    private final String payload;
    private final Optional<Referral> referral;

    /**
     * <b>Internal</b> method to create an instance of {@link PostbackEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link PostbackEvent}
     */
    public static PostbackEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date timestamp = getPropertyAsDate(jsonObject, PROP_TIMESTAMP);
        final String payload = getPropertyAsString(jsonObject, PROP_POSTBACK, PROP_PAYLOAD);
        final JsonObject referralJson = getPropertyAsJsonObject(jsonObject, PROP_POSTBACK, PROP_REFERRAL);

        return new PostbackEvent(senderId, recipientId, timestamp, payload, 
        		Optional.ofNullable(ReferralEvent.Referral.fromJson(referralJson)));
    }

    public PostbackEvent(String senderId, String recipientId, Date timestamp, 
    		String payload, Optional<Referral> referral) {
        super(senderId, recipientId, timestamp);
        this.payload = payload;
        this.referral = referral;
    }

    public String getPayload() {
        return payload;
    }

    public Optional<Referral> getReferral() {
        return referral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PostbackEvent that = (PostbackEvent) o;
        return Objects.equals(payload, that.payload) && 
        		Objects.equals(referral, that.referral);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), payload, referral);
    }

    @Override
    public String toString() {
        return "PostbackEvent{" +
                "payload='" + payload + '\'' +
                "} super=" + super.toString();
    }
}