package com.github.messenger4j.send.message.template.receipt;

import static java.util.Optional.empty;

import com.google.gson.annotations.SerializedName;
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
public final class Address {

    @SerializedName("street_1")
    private final String street1;
    private final String city;
    private final String postalCode;
    private final String state;
    private final String country;
    @SerializedName("street_2")
    private final Optional<String> street2;

    public static Address create(@NonNull String street1, @NonNull String city, @NonNull String postalCode,
                                 @NonNull String state, @NonNull String country) {
        return create(street1, empty(), city, postalCode, state, country);
    }

    public static Address create(@NonNull String street1, @NonNull Optional<String> street2, @NonNull String city,
                                 @NonNull String postalCode, @NonNull String state, @NonNull String country) {
        return new Address(street1, city, postalCode, state, country, street2);
    }

    private Address(String street1, String city, String postalCode, String state, String country, Optional<String> street2) {
        this.street1 = street1;
        this.city = city;
        this.postalCode = postalCode;
        this.state = state;
        this.country = country;
        this.street2 = street2;
    }

    public String street1() {
        return street1;
    }

    public String city() {
        return city;
    }

    public String postalCode() {
        return postalCode;
    }

    public String state() {
        return state;
    }

    public String country() {
        return country;
    }

    public Optional<String> street2() {
        return street2;
    }
}
