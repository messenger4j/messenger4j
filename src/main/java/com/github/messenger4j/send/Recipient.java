package com.github.messenger4j.send;

import com.github.messenger4j.internal.PreConditions;
import java.util.Objects;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class Recipient {

    private final String id;
    private final String phoneNumber;

    public static Builder newBuilder() {
        return new Builder();
    }

    private Recipient(RecipientIdBuilder builder) {
        id = builder.recipientId;
        phoneNumber = null;
    }

    private Recipient(PhoneNumberBuilder builder) {
        phoneNumber = builder.phoneNumber;
        id = null;
    }

    public String getId() {
        return this.id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return Objects.equals(id, recipient.id) &&
                Objects.equals(phoneNumber, recipient.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber);
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "id='" + id + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class Builder {

        private Builder() {
        }

        public RecipientIdBuilder recipientId(String recipientId) {
            return new RecipientIdBuilder(recipientId);
        }

        public PhoneNumberBuilder phoneNumber(String phoneNumber) {
            return new PhoneNumberBuilder(phoneNumber);
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class RecipientIdBuilder {
        private final String recipientId;

        private RecipientIdBuilder(String recipientId) {
            PreConditions.notNullOrBlank(recipientId, "recipientId");
            this.recipientId = recipientId;
        }

        public Recipient build() {
            return new Recipient(this);
        }
    }

    /**
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public static final class PhoneNumberBuilder {
        private final String phoneNumber;

        private PhoneNumberBuilder(String phoneNumber) {
            PreConditions.notNullOrBlank(phoneNumber, "phoneNumber");
            this.phoneNumber = phoneNumber;
        }

        public Recipient build() {
            return new Recipient(this);
        }
    }
}