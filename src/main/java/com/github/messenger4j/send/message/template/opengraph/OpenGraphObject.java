package com.github.messenger4j.send.message.template.opengraph;

import com.github.messenger4j.internal.Lists;
import com.github.messenger4j.send.message.template.button.Button;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/**
 * @author Max Grabenhorst
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
public final class OpenGraphObject {

    private final URL url;
    private final Optional<List<Button>> buttons;

    public static OpenGraphObject create(@NonNull URL url, @NonNull Optional<List<Button>> buttons) {
        return new OpenGraphObject(url, buttons);
    }

    private OpenGraphObject(URL url, Optional<List<Button>> buttons) {
        this.url = url;
        this.buttons = buttons.map(Lists::immutableList);
    }

    public URL url() {
        return url;
    }

    public Optional<List<Button>> buttons() {
        return buttons;
    }
}
