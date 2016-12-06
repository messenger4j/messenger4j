package com.github.messenger4j.receive.events;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_DELIVERY;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_MIDS;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_WATERMARK;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsJsonArray;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * This event will occur when a message a page has sent has been delivered.
 *
 * <p>
 * For further information refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/message-delivered">
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/message-delivered
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see Event
 */
public final class MessageDeliveredEvent extends Event {

    private final Date watermark;
    private final List<String> mids;

    /**
     * <b>Internal</b> method to create an instance of {@link MessageDeliveredEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link MessageDeliveredEvent}
     */
    public static MessageDeliveredEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date watermark = getPropertyAsDate(jsonObject, PROP_DELIVERY, PROP_WATERMARK);
        final JsonArray midsJsonArray = getPropertyAsJsonArray(jsonObject, PROP_DELIVERY, PROP_MIDS);

        List<String> mids;
        if (midsJsonArray != null) {
            mids = new ArrayList<>(midsJsonArray.size());
            for (JsonElement midJsonElement : midsJsonArray) {
                mids.add(midJsonElement.getAsString());
            }
        } else {
            mids = Collections.emptyList();
        }

        return new MessageDeliveredEvent(senderId, recipientId, watermark, mids);
    }

    public MessageDeliveredEvent(String senderId, String recipientId, Date watermark, List<String> mids) {
        super(senderId, recipientId);
        this.watermark = watermark;
        this.mids = mids == null ? null : Collections.unmodifiableList(mids);
    }

    public Date getWatermark() {
        return watermark;
    }

    public List<String> getMids() {
        return mids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MessageDeliveredEvent that = (MessageDeliveredEvent) o;
        return Objects.equals(watermark, that.watermark) &&
                Objects.equals(mids, that.mids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), watermark, mids);
    }

    @Override
    public String toString() {
        return "MessageDeliveredEvent{" +
                "watermark=" + watermark +
                ", mids=" + mids +
                "} super=" + super.toString();
    }
}