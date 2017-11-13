package com.github.messenger4j.send.message.template.receipt;

import static java.util.Optional.empty;

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
public final class Summary {

    private final float totalCost;
    private final Optional<Float> subtotal;
    private final Optional<Float> totalTax;
    private final Optional<Float> shippingCost;

    public static Summary create(float totalCost) {
        return create(totalCost, empty(), empty(), empty());
    }

    public static Summary create(float totalCost, @NonNull Optional<Float> subtotal, @NonNull Optional<Float> totalTax,
                                 @NonNull Optional<Float> shippingCost) {
        return new Summary(totalCost, subtotal, totalTax, shippingCost);
    }

    private Summary(float totalCost, Optional<Float> subtotal, Optional<Float> totalTax, Optional<Float> shippingCost) {
        this.totalCost = totalCost;
        this.subtotal = subtotal;
        this.totalTax = totalTax;
        this.shippingCost = shippingCost;
    }

    public float totalCost() {
        return totalCost;
    }

    public Optional<Float> subtotal() {
        return subtotal;
    }

    public Optional<Float> totalTax() {
        return totalTax;
    }

    public Optional<Float> shippingCost() {
        return shippingCost;
    }
}
