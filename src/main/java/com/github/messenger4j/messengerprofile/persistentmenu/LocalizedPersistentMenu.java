package com.github.messenger4j.messengerprofile.persistentmenu;

import com.github.messenger4j.common.SupportedLocale;
import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.messengerprofile.persistentmenu.action.CallToAction;
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
    private final Optional<List<CallToAction>> callToActions;

    public static LocalizedPersistentMenu create(@NonNull SupportedLocale locale, boolean composerInputDisabled,
                                                 @NonNull Optional<List<CallToAction>> callToActions) {

        return create(locale.name(), composerInputDisabled, callToActions);
    }

    public static LocalizedPersistentMenu create(@NonNull String locale, boolean composerInputDisabled,
                                                 @NonNull Optional<List<CallToAction>> callToActions) {
        return new LocalizedPersistentMenu(locale, composerInputDisabled, callToActions);
    }

    private LocalizedPersistentMenu(String locale, boolean composerInputDisabled,
                                    Optional<List<CallToAction>> callToActions) {
        this.locale = locale;
        this.composerInputDisabled = composerInputDisabled;
        this.callToActions = callToActions.map(Lists::immutableList);
    }

    public String locale() {
        return locale;
    }

    public boolean composerInputDisabled() {
        return composerInputDisabled;
    }

    public Optional<List<CallToAction>> callToActions() {
        return callToActions;
    }
}
