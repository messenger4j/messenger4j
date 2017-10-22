package com.github.messenger4j.send.templates;

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
    private final Boolean sharable;
    private final ImageAspectRatio imageAspectRatio;

    public static Builder newBuilder(@NonNull List<Element> elements) {
        return new Builder(elements);
    }

    public GenericTemplate(@NonNull List<Element> elements, Boolean sharable, ImageAspectRatio imageAspectRatio) {
        super(TemplateType.GENERIC);
        this.elements = elements;
        this.sharable = sharable;
        this.imageAspectRatio = imageAspectRatio;
    }

    public List<Element> elements() {
        return elements;
    }

    public Optional<Boolean> sharable() {
        return Optional.ofNullable(sharable);
    }

    public Optional<ImageAspectRatio> imageAspectRatio() {
        return Optional.ofNullable(imageAspectRatio);
    }

    /**
     * @author Max Grabenhorst
     * @since 1.0.0
     */
    public static final class Builder {

        private final List<Element> elements;
        private Boolean sharable;
        private ImageAspectRatio imageAspectRatio;

        private Builder(List<Element> elements) {
            this.elements = elements;
        }

        public Builder sharable(boolean sharable) {
            this.sharable = sharable;
            return this;
        }

        public Builder imageAspectRatio(@NonNull ImageAspectRatio imageAspectRatio) {
            this.imageAspectRatio = imageAspectRatio;
            return this;
        }

        public GenericTemplate build() {
            return new GenericTemplate(elements, sharable, imageAspectRatio);
        }
    }
}
