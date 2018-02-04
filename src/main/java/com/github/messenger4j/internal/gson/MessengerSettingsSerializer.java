package com.github.messenger4j.internal.gson;

import com.github.messenger4j.messengerprofile.MessengerSettings;
import com.github.messenger4j.messengerprofile.targetaudience.TargetAudience;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class MessengerSettingsSerializer implements JsonSerializer<MessengerSettings> {

    @Override
    public JsonElement serialize(MessengerSettings src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject messengerSettingsObject = new JsonObject();
        src.startButton().ifPresent(startButton ->
                messengerSettingsObject.add("get_started", context.serialize(startButton)));
        src.greeting().ifPresent(greeting ->
                messengerSettingsObject.add("greeting", context.serialize(greeting.localizedGreetings())));
        src.persistentMenu().ifPresent(persistentMenu ->
                messengerSettingsObject.add("persistent_menu", context.serialize(persistentMenu.localizedPersistentMenus())));
        src.whitelistedDomains().ifPresent(whitelistedDomains ->
                messengerSettingsObject.add("whitelisted_domains", context.serialize(whitelistedDomains)));
        src.accountLinkingUrl().ifPresent(accountLinkingUrl ->
                messengerSettingsObject.add("account_linking_url", context.serialize(accountLinkingUrl)));
        src.homeUrl().ifPresent(homeUrl ->
                messengerSettingsObject.add("home_url", context.serialize(homeUrl)));
        src.targetAudience().ifPresent(targetAudience ->
                messengerSettingsObject.add("target_audience", context.serialize(targetAudience, TargetAudience.class)));
        return messengerSettingsObject;
    }
}
