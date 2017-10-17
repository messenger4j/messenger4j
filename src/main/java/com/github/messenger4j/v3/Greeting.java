package com.github.messenger4j.v3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class Greeting {

    private final List<LocalizedGreeting> localizedGreetings;

    public static Greeting create(@NonNull String defaultGreetingText, @NonNull LocalizedGreeting... localizedGreetings) {
        final List<LocalizedGreeting> localizedGreetingList = new ArrayList<>(localizedGreetings.length + 1);
        localizedGreetingList.add(LocalizedGreeting.create("default", defaultGreetingText));
        localizedGreetingList.addAll(Arrays.asList(localizedGreetings));
        return new Greeting(localizedGreetingList);
    }

    private Greeting(List<LocalizedGreeting> localizedGreetings) {
        this.localizedGreetings = Collections.unmodifiableList(localizedGreetings);
    }

    public List<LocalizedGreeting> localizedGreetings() {
        return localizedGreetings;
    }
}
