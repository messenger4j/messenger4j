package com.github.messenger4j.send.message;

import static java.util.Optional.empty;

import com.github.messenger4j.send.message.quickreply.QuickReply;
import com.github.messenger4j.send.message.template.Template;
import java.util.List;
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
public final class TemplateMessage extends Message {

    private final Template template;

    public static TemplateMessage create(@NonNull Template template) {
        return create(template, empty(), empty());
    }

    public static TemplateMessage create(@NonNull Template template, @NonNull Optional<List<QuickReply>> quickReplies,
                                         @NonNull Optional<String> metadata) {
        return new TemplateMessage(template, quickReplies, metadata);
    }

    private TemplateMessage(Template template, Optional<List<QuickReply>> quickReplies, Optional<String> metadata) {
        super(quickReplies, metadata);
        this.template = template;
    }

    public Template template() {
        return template;
    }
}
