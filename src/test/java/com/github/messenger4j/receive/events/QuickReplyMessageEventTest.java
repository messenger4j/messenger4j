package com.github.messenger4j.receive.events;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class QuickReplyMessageEventTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(QuickReplyMessageEvent.class)
                .usingGetClass()
                .verify();

        EqualsVerifier.forClass(QuickReplyMessageEvent.QuickReply.class)
                .usingGetClass()
                .verify();
    }
}