package com.github.messenger4j.send.templates;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class GenericTemplateTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(GenericTemplate.class)
                .usingGetClass()
                .verify();

        EqualsVerifier.forClass(GenericTemplate.Element.class)
                .usingGetClass()
                .verify();
    }
}