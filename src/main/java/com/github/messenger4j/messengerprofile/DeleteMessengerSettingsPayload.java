package com.github.messenger4j.messengerprofile;

import com.github.messenger4j.internal.Lists;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.EqualsAndHashCode;
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

    public static DeleteMessengerSettingsPayload create(List<MessengerSettingProperty> properties) {
        return new DeleteMessengerSettingsPayload(properties);
    }

    private DeleteMessengerSettingsPayload(List<MessengerSettingProperty> messengerSettingProperties) {
        this.messengerSettingProperties = Lists.immutableList(messengerSettingProperties);
    }

    public List<MessengerSettingProperty> messengerSettingProperties() {
        return messengerSettingProperties;
    }
}
