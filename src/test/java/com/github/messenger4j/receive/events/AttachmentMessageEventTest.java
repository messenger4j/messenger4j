package com.github.messenger4j.receive.events;

import com.github.messenger4j.receive.attachments.Attachment;
import com.github.messenger4j.receive.attachments.BinaryPayload;
import com.github.messenger4j.receive.attachments.Coordinates;
import com.github.messenger4j.receive.attachments.LocationPayload;
import com.github.messenger4j.receive.attachments.UnsupportedPayload;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.6.0
 */
public class AttachmentMessageEventTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(AttachmentMessageEvent.class)
                .usingGetClass()
                .verify();
    }
}