package com.github.messenger4j.send.message.template;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.send.message.template.opengraph.OpenGraphObject;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class OpenGraphTemplate extends Template {

    private final List<OpenGraphObject> elements;

    public static OpenGraphTemplate create(@NonNull List<OpenGraphObject> elements) {
        return new OpenGraphTemplate(elements);
    }

    private OpenGraphTemplate(List<OpenGraphObject> elements) {
        super(Type.OPEN_GRAPH);
        this.elements = Lists.immutableList(elements);
    }

    public List<OpenGraphObject> elements() {
        return elements;
    }
}
