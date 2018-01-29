package com.github.messenger4j.send;

import com.github.messenger4j.send.recipient.Recipient;
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

    Payload(Recipient recipient) {
        this.recipient = recipient;
    }

    public Recipient recipient() {
        return recipient;
    }
}
