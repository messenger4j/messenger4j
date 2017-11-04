package com.github.messenger4j.messengerprofile.greeting;

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

    public static final String USER_FIRST_NAME = "{{user_first_name}}";
    public static final String USER_LAST_NAME = "{{user_last_name}}";
    public static final String USER_FULL_NAME = "{{user_full_name}}";

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
