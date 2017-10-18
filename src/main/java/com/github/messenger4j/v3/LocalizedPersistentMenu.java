package com.github.messenger4j.v3;

import com.github.messenger4j.setup.CallToAction;
import java.util.List;
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
public final class LocalizedPersistentMenu {

    private final String locale;
    private final boolean composerInputDisabled;
    private final List<CallToAction> callToActions;

    public static LocalizedPersistentMenu create(@NonNull SupportedLocale locale, boolean composerInputDisabled,
                                                 List<CallToAction> callToActions) {

        return create(locale.name(), composerInputDisabled, callToActions);
    }

    public static LocalizedPersistentMenu create(@NonNull String locale, boolean composerInputDisabled,
                                                 List<CallToAction> callToActions) {
        return new LocalizedPersistentMenu(locale, composerInputDisabled, callToActions);
    }

    private LocalizedPersistentMenu(String locale, boolean composerInputDisabled, List<CallToAction> callToActions) {
        this.locale = locale;
        this.composerInputDisabled = composerInputDisabled;
        this.callToActions = callToActions;
    }

    public String locale() {
        return locale;
    }

    public boolean composerInputDisabled() {
        return composerInputDisabled;
    }

    public Optional<List<CallToAction>> callToActions() {
        return Optional.ofNullable(callToActions);
    }
}
