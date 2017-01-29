package com.github.messenger4j.setup;

import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
 */
public class CallToActionTest {

    //TODO: check IllegalArgumentException: Cannot inject classes into the bootstrap class loader
    //@Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(CallToAction.class)
                .usingGetClass()
                .verify();
    }
}