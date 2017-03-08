package com.github.messenger4j.receive.events;

import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsJsonObject;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_REF;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_REFERRAL;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SOURCE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TYPE;

import java.util.Date;
import java.util.Objects;

import com.google.gson.JsonObject;

/**
 * This event will occur when a m.me link with a ref parameter is used by a user with an existing 
 * thread resulting in a {@code Referral event} being delivered.
 *
 * <p>
 * For further information refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/referral">
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/referral
 * </a>
 * </p>
 *
 * @author uzrbin
 * @see Event
 */
public final class ReferralEvent extends TimestampedEvent {

    private final Referral referral;

    /**
     * <b>Internal</b> method to create an instance of {@link ReferralEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link ReferralEvent}
     */
    public static ReferralEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date timestamp = getPropertyAsDate(jsonObject, PROP_TIMESTAMP);
        
        final Referral referral = 
        		Referral.fromJson(getPropertyAsJsonObject(jsonObject, PROP_REFERRAL));

        return new ReferralEvent(senderId, recipientId, timestamp, referral);
    }

    public ReferralEvent(String senderId, String recipientId, Date timestamp, Referral referral) {
        super(senderId, recipientId, timestamp);
        this.referral = referral;
    }

    public Referral getReferral() {
        return referral;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReferralEvent that = (ReferralEvent) o;
        return Objects.equals(referral, that.referral);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), referral);
    }

    @Override
    public String toString() {
        return "ReferralEvent{" +
                "referral='" + referral + '\'' +
                "} super=" + super.toString();
    }
    

    /**
     * Custom data that is provided when an m.me link is called with a ref parameter.
     *
     * @author uzrbin
     */
    public static final class Referral {

    	private final String ref, source, type;

        protected static Referral fromJson(JsonObject jsonObject) {
        	if (jsonObject == null) return null;
        	
            final String ref = getPropertyAsString(jsonObject, PROP_REF);
            final String source = getPropertyAsString(jsonObject, PROP_SOURCE);
            final String type = getPropertyAsString(jsonObject, PROP_TYPE);
            return new Referral(ref, source, type);
        }

        public Referral(String ref, String source, String type) {
            this.ref = ref;
            this.source = source;
            this.type = type;
        }

        public String getRef() {
			return ref;
		}

		public String getSource() {
			return source;
		}

		public String getType() {
			return type;
		}

		@Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Referral that = (Referral) o;
            return Objects.equals(ref, that.ref) && 
            		Objects.equals(source, that.source) &&
            		Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ref, source, type);
        }

        @Override
        public String toString() {
            return "QuickReply{" +
                    "ref='" + ref + '\'' +
                    "source='" + source + '\'' +
                    "type='" + type + '\'' +
                    '}';
        }
    }
}