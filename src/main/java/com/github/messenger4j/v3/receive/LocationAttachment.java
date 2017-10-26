package com.github.messenger4j.v3.receive;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class LocationAttachment extends Attachment {

    private final Double latitude;
    private final Double longitude;

    public LocationAttachment(@NonNull Double latitude, @NonNull Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean isLocationAttachment() {
        return true;
    }

    @Override
    public LocationAttachment asLocationAttachment() {
        return this;
    }

    public Double latitude() {
        return latitude;
    }

    public Double longitude() {
        return longitude;
    }
}
