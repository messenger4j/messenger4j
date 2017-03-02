package com.github.messenger4j.receive.attachments;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Albert Hoekman
 * @since 0.9.0
 */
public class LocationPayloadTest {
    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(LocationPayload.class)
                .usingGetClass()
                .verify();
    }
}
