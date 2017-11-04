package com.github.messenger4j.messengerprofile.persistentmenu.action;

import com.github.messenger4j.internal.Lists;
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
public final class NestedCallToAction extends CallToAction {

    private final List<CallToAction> callToActions;

    public static NestedCallToAction create(@NonNull String title, @NonNull List<CallToAction> callToActions) {
        return new NestedCallToAction(title, callToActions);
    }

    private NestedCallToAction(String title, List<CallToAction> callToActions) {
        super(Type.NESTED, title);
        this.callToActions = Lists.immutableList(callToActions);
    }

    public List<CallToAction> callToActions() {
        return callToActions;
    }
}
