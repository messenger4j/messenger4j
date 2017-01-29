package com.github.messenger4j.setup;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
 */
public class SetupResponseTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(SetupResponse.class)
                .usingGetClass()
                .verify();
    }
}