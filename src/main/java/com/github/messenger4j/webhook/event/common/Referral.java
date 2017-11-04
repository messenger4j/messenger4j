package com.github.messenger4j.webhook.event.common;

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
    private final Optional<String> refPayload;
    private final Optional<String> adId;

    public Referral(@NonNull String source, @NonNull String type, @NonNull Optional<String> refPayload,
                    @NonNull Optional<String> adId) {
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
        return refPayload;
    }

    public Optional<String> adId() {
        return adId;
    }
}
