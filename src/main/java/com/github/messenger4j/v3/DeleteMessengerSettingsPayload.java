package com.github.messenger4j.v3;

import com.google.gson.annotations.SerializedName;
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
public final class DeleteMessengerSettingsPayload {

    @SerializedName("fields")
    private final List<MessengerSettingProperty> messengerSettingProperties;

    public static DeleteMessengerSettingsPayload of(@NonNull List<MessengerSettingProperty> properties) {
        return new DeleteMessengerSettingsPayload(properties);
    }

    private DeleteMessengerSettingsPayload(List<MessengerSettingProperty> messengerSettingProperties) {
        this.messengerSettingProperties = Collections.unmodifiableList(messengerSettingProperties);
    }

    public List<MessengerSettingProperty> messengerSettingProperties() {
        return messengerSettingProperties;
    }
}
