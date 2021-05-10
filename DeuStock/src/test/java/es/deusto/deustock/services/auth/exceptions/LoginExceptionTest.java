package es.deusto.deustock.services.auth.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginExceptionTest {

    @Test
    void testConstructorNotNull(){
        // Given

        // When
        LoginException exception = new LoginException("Test Exception");

        // Then
        assertNotNull(exception);
    }
}