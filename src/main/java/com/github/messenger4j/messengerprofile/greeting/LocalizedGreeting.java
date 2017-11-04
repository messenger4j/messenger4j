package com.github.messenger4j.messengerprofile.greeting;

import com.github.messenger4j.common.SupportedLocale;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class LocalizedGreeting {

    private final String locale;
    private final String text;

    public static LocalizedGreeting create(@NonNull SupportedLocale locale, @NonNull String text) {
        return create(locale.name(), text);
    }

    public static LocalizedGreeting create(@NonNull String locale, @NonNull String text) {
        return new LocalizedGreeting(locale, text);
    }

    private LocalizedGreeting(String locale, String text) {
        this.locale = locale;
        this.text = text;
    }

    public String locale() {
        return locale;
    }

    public String text() {
        return text;
    }
}
