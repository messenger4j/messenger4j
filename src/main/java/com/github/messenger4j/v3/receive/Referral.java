package com.github.messenger4j.v3.receive;

import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class Referral {

    private final String source;
    private final String type;
    private final String refPayload;
    private final String adId;

    public Referral(@NonNull String source, @NonNull String type, String refPayload, String adId) {
        this.source = source;
        this.type = type;
        this.refPayload = refPayload;
        this.adId = adId;
    }

    public String source() {
        return source;
    }

    public String type() {
        return type;
    }

    public Optional<String> refPayload() {
        return Optional.ofNullable(refPayload);
    }

    public Optional<String> adId() {
        return Optional.ofNullable(adId);
    }
}
