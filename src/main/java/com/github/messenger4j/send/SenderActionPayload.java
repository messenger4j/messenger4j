package com.github.messenger4j.send;

import com.github.messenger4j.send.recipient.IdRecipient;
import com.github.messenger4j.send.recipient.Recipient;
import com.github.messenger4j.send.senderaction.SenderAction;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class SenderActionPayload extends Payload {

    private final SenderAction senderAction;

    public static SenderActionPayload create(@NonNull String recipientId, @NonNull SenderAction senderAction) {
        return create(IdRecipient.create(recipientId), senderAction);
    }

    public static SenderActionPayload create(@NonNull Recipient recipient, @NonNull SenderAction senderAction) {
        return new SenderActionPayload(recipient, senderAction);
    }

    private SenderActionPayload(Recipient recipient, SenderAction senderAction) {
        super(recipient);
        this.senderAction = senderAction;
    }

    public SenderAction senderAction() {
        return senderAction;
    }
}
