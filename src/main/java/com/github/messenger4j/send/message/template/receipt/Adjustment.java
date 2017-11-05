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
public final class Adjustment {

    private final Optional<String> name;
    private final Optional<Float> amount;

    public static Adjustment create(@NonNull String name) {
        return new Adjustment(Optional.of(name), empty());
    }

    public static Adjustment create(float amount) {
        return new Adjustment(empty(), Optional.of(amount));
    }

    public static Adjustment create(@NonNull String name, float amount) {
        return new Adjustment(Optional.of(name), Optional.of(amount));
    }

    private Adjustment(Optional<String> name, Optional<Float> amount) {
        this.name = name;
        this.amount = amount;
    }

    public Optional<String> name() {
        return name;
    }

    public Optional<Float> amount() {
        return amount;
    }
}
