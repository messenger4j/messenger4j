package com.github.messenger4j.send.message.template.button;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class LogOutButton extends Button {

    public static LogOutButton create() {
        return new LogOutButton();
    }

    private LogOutButton() {
        super(Type.ACCOUNT_UNLINK);
    }

}
