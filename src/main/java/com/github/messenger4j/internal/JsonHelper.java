package com.github.messenger4j.internal;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;

/**
 * <b>Internal</b> helper class used to support processing of JSON structures.
 *
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public final class JsonHelper {

    private JsonHelper() {
    }

    public static JsonElement getProperty(JsonObject jsonObject, Constants... propertyPath) {
        if (jsonObject == null) {
            return null;
        }
        JsonObject internalValue = jsonObject;
        for (int i = 0; i <= propertyPath.length - 2; i++) {
            final JsonElement property = internalValue.get(propertyPath[i].getValue());
            if (property == null || !property.isJsonObject()) {
                return null;
            }
            internalValue = property.getAsJsonObject();
        }
        final JsonElement property = internalValue.get(propertyPath[propertyPath.length - 1].getValue());
        return property == null || property.isJsonNull() ? null : property;
    }

    public static boolean hasProperty(JsonObject jsonObject, Constants... propertyPath) {
        return getProperty(jsonObject, propertyPath) != null;
    }

    public static String getPropertyAsString(JsonObject jsonObject, Constants... propertyPath) {
        final JsonElement jsonElement = getProperty(jsonObject, propertyPath);
        return jsonElement == null ? null : jsonElement.getAsString();
    }

    public static Integer getPropertyAsInt(JsonObject jsonObject, Constants... propertyPath) {
        final JsonElement jsonElement = getProperty(jsonObject, propertyPath);
        return jsonElement == null ? null : jsonElement.getAsInt();
    }

    public static Long getPropertyAsLong(JsonObject jsonObject, Constants... propertyPath) {
        final JsonElement jsonElement = getProperty(jsonObject, propertyPath);
        return jsonElement == null ? null : jsonElement.getAsLong();
    }

    public static Date getPropertyAsDate(JsonObject jsonObject, Constants... propertyPath) {
        final Long longValue = getPropertyAsLong(jsonObject, propertyPath);
        return longValue == null ? null : new Date(longValue);
    }

    public static Double getPropertyAsDouble(JsonObject jsonObject, Constants... propertyPath) {
        final JsonElement jsonElement = getProperty(jsonObject, propertyPath);
        return jsonElement == null ? null : jsonElement.getAsDouble();
    }

    public static Float getPropertyAsFloat(JsonObject jsonObject, Constants... propertyPath) {
        final JsonElement jsonElement = getProperty(jsonObject, propertyPath);
        return jsonElement == null ? null : jsonElement.getAsFloat();
    }

    public static JsonArray getPropertyAsJsonArray(JsonObject jsonObject, Constants... propertyPath) {
        final JsonElement jsonElement = getProperty(jsonObject, propertyPath);
        return jsonElement == null ? null : jsonElement.getAsJsonArray();
    }

    public static JsonObject getPropertyAsJsonObject(JsonObject jsonObject, Constants... propertyPath) {
        final JsonElement jsonElement = getProperty(jsonObject, propertyPath);
        return jsonElement == null ? null : jsonElement.getAsJsonObject();
    }

    /**
     * Constants for property keys of the Messenger Platform JSON formats.
     *
     * @author Max Grabenhorst
     * @since 0.6.0
     */
    public enum Constants {

        PROP_OBJECT("object"),
        PROP_ENTRY("entry"),
        PROP_MESSAGING("messaging"),
        PROP_SENDER("sender"),
        PROP_RECIPIENT("recipient"),
        PROP_ID("id"),
        PROP_TIMESTAMP("timestamp"),
        PROP_OPTIN("optin"),
        PROP_MESSAGE("message"),
        PROP_MID("mid"),
        PROP_IS_ECHO("is_echo"),
        PROP_QUICK_REPLY("quick_reply"),
        PROP_TEXT("text"),
        PROP_ATTACHMENTS("attachments"),
        PROP_PAYLOAD("payload"),
        PROP_TYPE("type"),
        PROP_URL("url"),
        PROP_COORDINATES("coordinates"),
        PROP_LAT("lat"),
        PROP_LONG("long"),
        PROP_REF("ref"),
        PROP_APP_ID("app_id"),
        PROP_METADATA("metadata"),
        PROP_POSTBACK("postback"),
        PROP_ACCOUNT_LINKING("account_linking"),
        PROP_STATUS("status"),
        PROP_AUTHORIZATION_CODE("authorization_code"),
        PROP_READ("read"),
        PROP_WATERMARK("watermark"),
        PROP_DELIVERY("delivery"),
        PROP_MIDS("mids"),
        PROP_RECIPIENT_ID("recipient_id"),
        PROP_MESSAGE_ID("message_id"),
        PROP_ATTACHMENT_ID("attachment_id"),
        PROP_ERROR("error"),
        PROP_CODE("code"),
        PROP_FB_TRACE_ID("fbtrace_id"),
        PROP_RESULT("result"),
        PROP_FIRST_NAME("first_name"),
        PROP_LAST_NAME("last_name"),
        PROP_PROFILE_PIC("profile_pic"),
        PROP_LOCALE("locale"),
        PROP_TIMEZONE("timezone"),
        PROP_GENDER("gender");

        private final String value;

        Constants(String value) {
            this.value = value;
        }

        String getValue() {
            return value;
        }
    }
}