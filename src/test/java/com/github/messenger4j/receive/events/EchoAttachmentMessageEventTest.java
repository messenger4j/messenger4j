package com.github.messenger4j.receive.events;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Albert Hoekman
 * @since 0.9.0
 */
public class EchoAttachmentMessageEventTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(EchoAttachmentMessageEvent.class)
                .usingGetClass()
                .verify();
    }
}
