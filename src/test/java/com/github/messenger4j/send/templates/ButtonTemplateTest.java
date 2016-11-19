package com.github.messenger4j.send.templates;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class ButtonTemplateTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(ButtonTemplate.class)
                .usingGetClass()
                .verify();
    }
}