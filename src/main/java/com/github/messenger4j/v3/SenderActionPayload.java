package com.github.messenger4j.v3;

import static java.util.Optional.empty;

import com.github.messenger4j.send.IdRecipient;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import java.util.Optional;
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
        return create(IdRecipient.create(recipientId), senderAction, empty());
    }

    public static SenderActionPayload create(@NonNull Recipient recipient, @NonNull SenderAction senderAction) {
        return create(recipient, senderAction, empty());
    }

    public static SenderActionPayload create(@NonNull Recipient recipient, @NonNull SenderAction senderAction,
                                             @NonNull Optional<NotificationType> notificationType) {
        return new SenderActionPayload(recipient, senderAction, notificationType);
    }

    private SenderActionPayload(Recipient recipient, SenderAction senderAction, Optional<NotificationType> notificationType) {
        super(recipient, notificationType);
        this.senderAction = senderAction;
    }

    public SenderAction senderAction() {
        return senderAction;
    }
}
