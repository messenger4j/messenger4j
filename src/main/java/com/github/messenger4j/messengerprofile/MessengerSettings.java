package com.github.messenger4j.messengerprofile;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.messengerprofile.getstarted.StartButton;
import com.github.messenger4j.messengerprofile.greeting.Greeting;
import com.github.messenger4j.messengerprofile.homeurl.HomeUrl;
import com.github.messenger4j.messengerprofile.persistentmenu.PersistentMenu;
import com.github.messenger4j.messengerprofile.targetaudience.TargetAudience;
import java.net.URL;
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
public final class MessengerSettings {

    private final Optional<StartButton> startButton;
    private final Optional<Greeting> greeting;
    private final Optional<PersistentMenu> persistentMenu;
    private final Optional<List<URL>> whitelistedDomains;
    private final Optional<URL> accountLinkingUrl;
    private final Optional<HomeUrl> homeUrl;
    private final Optional<TargetAudience> targetAudience;

    public static MessengerSettings create(@NonNull Optional<StartButton> startButton,
                                           @NonNull Optional<Greeting> greeting,
                                           @NonNull Optional<PersistentMenu> persistentMenu,
                                           @NonNull Optional<List<URL>> whitelistedDomains,
                                           @NonNull Optional<URL> accountLinkingUrl,
                                           @NonNull Optional<HomeUrl> homeUrl,
                                           @NonNull Optional<TargetAudience> targetAudience) {
        return new MessengerSettings(startButton, greeting, persistentMenu, whitelistedDomains, accountLinkingUrl,
                homeUrl, targetAudience);
    }

    private MessengerSettings(Optional<StartButton> startButton,
                              Optional<Greeting> greeting,
                              Optional<PersistentMenu> persistentMenu,
                              Optional<List<URL>> whitelistedDomains,
                              Optional<URL> accountLinkingUrl,
                              Optional<HomeUrl> homeUrl,
                              Optional<TargetAudience> targetAudience) {
        this.startButton = startButton;
        this.greeting = greeting;
        this.persistentMenu = persistentMenu;
        this.whitelistedDomains = whitelistedDomains.map(Lists::immutableList);
        this.accountLinkingUrl = accountLinkingUrl;
        this.homeUrl = homeUrl;
        this.targetAudience = targetAudience;
    }

    public Optional<StartButton> startButton() {
        return startButton;
    }

    public Optional<Greeting> greeting() {
        return greeting;
    }

    public Optional<PersistentMenu> persistentMenu() {
        return persistentMenu;
    }

    public Optional<List<URL>> whitelistedDomains() {
        return whitelistedDomains;
    }

    public Optional<URL> accountLinkingUrl() {
        return accountLinkingUrl;
    }

    public Optional<HomeUrl> homeUrl() {
        return homeUrl;
    }

    public Optional<TargetAudience> targetAudience() {
        return targetAudience;
    }
}
