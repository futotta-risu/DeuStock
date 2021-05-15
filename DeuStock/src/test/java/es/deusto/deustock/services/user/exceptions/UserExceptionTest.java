package es.deusto.deustock.services.user.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserExceptionTest {

    @Test
    void testConstructorIsNotNull(){
        // Given

        // When
        UserException e = new UserException("Exception");

        // Then
        assertNotNull(e);
    }
}