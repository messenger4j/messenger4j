package com.github.messenger4j.receive.events;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class TextMessageEventTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(TextMessageEvent.class)
                .usingGetClass()
                .verify();
    }
}