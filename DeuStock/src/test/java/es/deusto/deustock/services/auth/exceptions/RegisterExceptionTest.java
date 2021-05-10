package es.deusto.deustock.services.auth.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterExceptionTest {

    @Test
    void testConstructorNotNull(){
        // Given

        // When
        RegisterException exception = new RegisterException("Test Exception");

        // Then
        assertNotNull(exception);
    }

}