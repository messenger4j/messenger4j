package com.github.messenger4j.v3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public final class MessengerSettingsSerializer implements JsonSerializer<MessengerSettings> {

    @Override
    public JsonElement serialize(MessengerSettings src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject messengerSettingsObject = new JsonObject();
        if (src.startButton() != null) {
            messengerSettingsObject.add("get_started", context.serialize(src.startButton()));
        }
        if (src.greeting() != null) {
            messengerSettingsObject.add("greeting", context.serialize(src.greeting().localizedGreetings()));
        }
        if (src.persistentMenu() != null) {
            messengerSettingsObject.add("persistent_menu", context.serialize(src.persistentMenu().localizedPersistentMenus()));
        }
        return messengerSettingsObject;
    }
}
