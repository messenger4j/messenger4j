package com.github.messenger4j.send;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class TemplateAttachmentTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(TemplateAttachment.class)
                .usingGetClass()
                .verify();
    }
}