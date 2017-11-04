package com.github.messenger4j.send.message.quickreply;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class LocationQuickReply extends QuickReply {

    public static LocationQuickReply create() {
        return new LocationQuickReply();
    }

    private LocationQuickReply() {
        super(ContentType.LOCATION);
    }
}
