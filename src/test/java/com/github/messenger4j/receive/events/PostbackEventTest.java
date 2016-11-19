package com.github.messenger4j.receive.events;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class PostbackEventTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(PostbackEvent.class)
                .usingGetClass()
                .verify();
    }
}