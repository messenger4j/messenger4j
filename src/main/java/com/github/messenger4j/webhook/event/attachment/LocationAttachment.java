package com.github.messenger4j.webhook.event.attachment;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode(callSuper = false)
public final class LocationAttachment extends Attachment {

    private final double latitude;
    private final double longitude;

    public LocationAttachment(double latitude, double longitude) {
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

    public double latitude() {
        return latitude;
    }

    public double longitude() {
        return longitude;
    }
}
