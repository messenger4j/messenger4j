package com.github.messenger4j.receive.events;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class EventTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(Event.class)
                .usingGetClass()
                .verify();

        EqualsVerifier.forClass(Event.Entity.class)
                .usingGetClass()
                .verify();
    }
}