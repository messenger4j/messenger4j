package com.github.messenger4j.send;

import com.google.gson.annotations.SerializedName;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
public enum MessageTag {

    @SerializedName("PAIRING_UPDATE")
    PAIRING_UPDATE,

    @SerializedName("APPLICATION_UPDATE")
    APPLICATION_UPDATE,

    @SerializedName("ACCOUNT_UPDATE")
    ACCOUNT_UPDATE,

    @SerializedName("PAYMENT_UPDATE")
    PAYMENT_UPDATE,

    @SerializedName("PERSONAL_FINANCE_UPDATE")
    PERSONAL_FINANCE_UPDATE,

    @SerializedName("SHIPPING_UPDATE")
    SHIPPING_UPDATE,

    @SerializedName("RESERVATION_UPDATE")
    RESERVATION_UPDATE,

    @SerializedName("ISSUE_RESOLUTION")
    ISSUE_RESOLUTION,

    @SerializedName("APPOINTMENT_UPDATE")
    APPOINTMENT_UPDATE,

    @SerializedName("GAME_EVENT")
    GAME_EVENT,

    @SerializedName("TRANSPORTATION_UPDATE")
    TRANSPORTATION_UPDATE,

    @SerializedName("FEATURE_FUNCTIONALITY_UPDATE")
    FEATURE_FUNCTIONALITY_UPDATE,

    @SerializedName("TICKET_UPDATE")
    TICKET_UPDATE
}
