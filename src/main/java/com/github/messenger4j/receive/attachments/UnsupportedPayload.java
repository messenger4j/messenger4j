package com.github.messenger4j.receive.attachments;

import java.util.Objects;

/**
 * {@link Payload} implementation that is used when the {@link Payload} of the {@link Attachment} is not supported.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see Attachment
 */
public final class UnsupportedPayload extends Payload {

    @Override
    public boolean isUnsupportedPayload() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass().getSimpleName());
    }

    @Override
    public String toString() {
        return "UnsupportedPayload{} super=" + super.toString();
    }
}
