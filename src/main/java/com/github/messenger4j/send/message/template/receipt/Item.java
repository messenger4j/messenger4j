package com.github.messenger4j.send.message.template.receipt;

import static java.util.Optional.empty;

import java.net.URL;
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
public final class Item {

    private final String title;
    private final float price;
    private final Optional<String> subtitle;
    private final Optional<Integer> quantity;
    private final Optional<String> currency;
    private final Optional<URL> imageUrl;

    public static Item create(@NonNull String title, float price) {
        return create(title, price, empty(), empty(), empty(), empty());
    }

    public static Item create(@NonNull String title, float price, @NonNull Optional<String> subtitle,
                              @NonNull Optional<Integer> quantity, @NonNull Optional<String> currency,
                              @NonNull Optional<URL> imageUrl) {

        return new Item(title, price, subtitle, quantity, currency, imageUrl);
    }

    private Item(String title, float price, Optional<String> subtitle, Optional<Integer> quantity,
                 Optional<String> currency, Optional<URL> imageUrl) {
        this.title = title;
        this.price = price;
        this.subtitle = subtitle;
        this.quantity = quantity;
        this.currency = currency;
        this.imageUrl = imageUrl;
    }

    public String title() {
        return title;
    }

    public float price() {
        return price;
    }

    public Optional<String> subtitle() {
        return subtitle;
    }

    public Optional<Integer> quantity() {
        return quantity;
    }

    public Optional<String> currency() {
        return currency;
    }

    public Optional<URL> imageUrl() {
        return imageUrl;
    }
}
