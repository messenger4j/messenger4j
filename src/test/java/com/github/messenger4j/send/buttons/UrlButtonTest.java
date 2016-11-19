package com.github.messenger4j.send.buttons;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class UrlButtonTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(UrlButton.class)
                .usingGetClass()
                .verify();
    }
}