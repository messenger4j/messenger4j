package com.github.messenger4j.receive.attachments;

/**
 * This is the base implementation of the different {@link Payload} types providing the functionality to check
 * the type of the concrete implementation and to return the concrete type.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see Attachment
 */
public abstract class Payload {

    public boolean isUnsupportedPayload() {
        return false;
    }

    public boolean isBinaryPayload() {
        return false;
    }

    public boolean isLocationPayload() {
        return false;
    }

    public BinaryPayload asBinaryPayload() {
        throw new UnsupportedOperationException("not a BinaryPayload");
    }

    public LocationPayload asLocationPayload() {
        throw new UnsupportedOperationException("not a LocationPayload");
    }

    @Override
    public String toString() {
        return "Payload{}";
    }
}
