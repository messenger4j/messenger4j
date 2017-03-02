package com.github.messenger4j.receive.attachments;

import com.google.gson.JsonObject;

import java.util.Objects;

import static com.github.messenger4j.internal.JsonHelper.Constants.*;
import static com.github.messenger4j.internal.JsonHelper.getPropertyAsDouble;

/**
 * {@link Payload} implementation that is used when the {@link Payload} of the {@link Attachment} is a location.
 *
 * @author Albert Hoekman
 * @since 0.9.0
 * @see Attachment
 * @see Coordinates
 */
public final class LocationPayload extends Payload {

    private final Coordinates coordinates;

    public static LocationPayload fromJson(JsonObject jsonObject) {
        final Double latitude = getPropertyAsDouble(jsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LAT);
        final Double longitude = getPropertyAsDouble(jsonObject, PROP_PAYLOAD, PROP_COORDINATES, PROP_LONG);
        return new LocationPayload(latitude, longitude);
    }

    public LocationPayload(Double latitude, Double longitude) {
        this.coordinates = new Coordinates(latitude, longitude);
    }

    @Override
    public boolean isLocationPayload() {
        return true;
    }

    @Override
    public LocationPayload asLocationPayload() {
        return this;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationPayload that = (LocationPayload) o;
        return Objects.equals(coordinates, that.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }

    @Override
    public String toString() {
        return "LocationPayload{" +
                "coordinates=" + coordinates +
                "} super=" + super.toString();
    }
}
