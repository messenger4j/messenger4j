package com.github.messenger4j.receive.attachments;

import com.github.messenger4j.receive.events.AttachmentMessageEvent;
import com.google.gson.JsonObject;

import java.util.Objects;

import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_PAYLOAD;
import static com.github.messenger4j.internal.JsonHelper.Constants.PROP_URL;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsString;

/**
 * {@link Payload} implementation that is used when the {@link Payload} of the {@link Attachment} is a binary attachment.
 * E.g. an image, file, audio or video attachment.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see Attachment
 */
public final class BinaryPayload extends Payload {

    private final String url;

    public static BinaryPayload fromJson(JsonObject jsonObject) {
        final String url = getPropertyAsString(jsonObject, PROP_PAYLOAD, PROP_URL);
        return new BinaryPayload(url);
    }

    public BinaryPayload(String url) {
        this.url = url;
    }

    @Override
    public boolean isBinaryPayload() {
        return true;
    }

    @Override
    public BinaryPayload asBinaryPayload() {
        return this;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryPayload that = (BinaryPayload) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "BinaryPayload{" +
                "url='" + url + '\'' +
                "} super=" + super.toString();
    }
}
