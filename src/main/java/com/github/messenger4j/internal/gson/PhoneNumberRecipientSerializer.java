package com.github.messenger4j.internal.gson;

import com.github.messenger4j.send.recipient.PhoneNumberRecipient;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
final class PhoneNumberRecipientSerializer implements JsonSerializer<PhoneNumberRecipient> {

    @Override
    public JsonElement serialize(PhoneNumberRecipient phoneNumberRecipient, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject phoneNumberRecipientObject = new JsonObject();
        phoneNumberRecipientObject.addProperty("phone_number", phoneNumberRecipient.phoneNumber());

        if (phoneNumberRecipient.firstName().isPresent() && phoneNumberRecipient.lastName().isPresent()) {
            final JsonObject nameObject = new JsonObject();
            nameObject.addProperty("first_name", phoneNumberRecipient.firstName().get());
            nameObject.addProperty("last_name", phoneNumberRecipient.lastName().get());
            phoneNumberRecipientObject.add("name", nameObject);
        }

        return phoneNumberRecipientObject;
    }
}
