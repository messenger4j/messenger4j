package com.github.messenger4j.send.templates;

import static java.util.Optional.empty;

import com.github.messenger4j.v3.ImageAspectRatio;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class GenericTemplate extends Template {

    private final List<Element> elements;
    private final Optional<ImageAspectRatio> imageAspectRatio;
    private final Optional<Boolean> sharable;

    public static GenericTemplate create(@NonNull List<Element> elements) {
        return create(elements, empty(), empty());
    }

    public static GenericTemplate create(@NonNull List<Element> elements, @NonNull Optional<ImageAspectRatio> imageAspectRatio,
                                         @NonNull Optional<Boolean> sharable) {
        return new GenericTemplate(elements, imageAspectRatio, sharable);
    }

    private GenericTemplate(List<Element> elements, Optional<ImageAspectRatio> imageAspectRatio, Optional<Boolean> sharable) {
        super(Type.GENERIC);
        this.elements = elements;
        this.imageAspectRatio = imageAspectRatio;
        this.sharable = sharable;
    }

    public List<Element> elements() {
        return elements;
    }

    public Optional<ImageAspectRatio> imageAspectRatio() {
        return imageAspectRatio;
    }

    public Optional<Boolean> sharable() {
        return sharable;
    }
}
