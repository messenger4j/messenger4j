package com.github.messenger4j.send.buttons;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class ShareButtonTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(ShareButton.class)
                .usingGetClass()
                .verify();
    }
}