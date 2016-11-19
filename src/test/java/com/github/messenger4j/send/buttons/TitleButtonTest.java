package com.github.messenger4j.send.buttons;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class TitleButtonTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(TitleButton.class)
                .usingGetClass()
                .verify();
    }
}