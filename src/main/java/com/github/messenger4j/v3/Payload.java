package com.github.messenger4j.v3;

import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.Recipient;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public abstract class Payload {

    private final Recipient recipient;
    private final Optional<NotificationType> notificationType;

    Payload(Recipient recipient, Optional<NotificationType> notificationType) {
        this.recipient = recipient;
        this.notificationType = notificationType;
    }

    public Recipient recipient() {
        return recipient;
    }

    public Optional<NotificationType> notificationType() {
        return notificationType;
    }
}
