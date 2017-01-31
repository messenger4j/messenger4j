package com.github.messenger4j.user;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * @author Max Grabenhorst
 * @since 0.8.0
 */
public class UserProfileTest {

    @Test
    public void shouldHaveACorrectEqualsImplementation() {
        EqualsVerifier.forClass(UserProfile.class)
                .usingGetClass()
                .verify();
    }
}