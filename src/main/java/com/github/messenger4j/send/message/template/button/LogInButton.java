package com.github.messenger4j.send.message.template.button;

import java.net.URL;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public final class LogInButton extends Button {

    private final URL url;

    public static LogInButton create(@NonNull URL url) {
        return new LogInButton(url);
    }

    private LogInButton(URL url) {
        super(Type.ACCOUNT_LINK);
        this.url = url;
    }

    public URL url() {
        return url;
    }
}
