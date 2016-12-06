package com.github.messenger4j.receive.events;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ACCOUNT_LINKING;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_AUTHORIZATION_CODE;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_ID;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_RECIPIENT;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_SENDER;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_STATUS;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_TIMESTAMP;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDate;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

import com.google.gson.JsonObject;
import java.util.Date;
import java.util.Objects;

/**
 * This event will occur when the {@code Linked Account} or {@code Unlink Account} call-to-action
 * has been tapped.
 *
 * <p>
 * For further information refer to:<br>
 * <a href="https://developers.facebook.com/docs/messenger-platform/webhook-reference/account-linking">
 * https://developers.facebook.com/docs/messenger-platform/webhook-reference/account-linking
 * </a>
 * </p>
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 * @see Event
 * @see AccountLinkingStatus
 */
public final class AccountLinkingEvent extends TimestampedEvent {

    private final AccountLinkingStatus status;
    private final String authorizationCode;

    /**
     * <b>Internal</b> method to create an instance of {@link AccountLinkingEvent} from the given
     * event as JSON structure.
     *
     * @param jsonObject the event as JSON structure
     * @return the created {@link AccountLinkingEvent}
     */
    public static AccountLinkingEvent fromJson(JsonObject jsonObject) {
        final String senderId = getPropertyAsString(jsonObject, PROP_SENDER, PROP_ID);
        final String recipientId = getPropertyAsString(jsonObject, PROP_RECIPIENT, PROP_ID);
        final Date timestamp = getPropertyAsDate(jsonObject, PROP_TIMESTAMP);
        final String status = getPropertyAsString(jsonObject, PROP_ACCOUNT_LINKING, PROP_STATUS);
        final String authorizationCode = getPropertyAsString(jsonObject, PROP_ACCOUNT_LINKING, PROP_AUTHORIZATION_CODE);

        final AccountLinkingStatus accountLinkingStatus = (status == null ? null : AccountLinkingStatus.valueOf(status.toUpperCase()));

        return new AccountLinkingEvent(senderId, recipientId, timestamp, accountLinkingStatus, authorizationCode);
    }

    public AccountLinkingEvent(String senderId, String recipientId, Date timestamp, AccountLinkingStatus status,
                                String authorizationCode) {

        super(senderId, recipientId, timestamp);
        this.status = status;
        this.authorizationCode = authorizationCode;
    }

    public AccountLinkingStatus getStatus() {
        return status;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountLinkingEvent that = (AccountLinkingEvent) o;
        return status == that.status &&
                Objects.equals(authorizationCode, that.authorizationCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status, authorizationCode);
    }

    @Override
    public String toString() {
        return "AccountLinkingEvent{" +
                "status=" + status +
                ", authorizationCode='" + authorizationCode + '\'' +
                "} super=" + super.toString();
    }

    /**
     * The account linking status.
     *
     * An account can be {@code linked} or {@code unlinked}.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum AccountLinkingStatus {
        LINKED, UNLINKED
    }
}