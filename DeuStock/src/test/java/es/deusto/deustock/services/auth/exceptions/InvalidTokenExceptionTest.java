package es.deusto.deustock.services.auth.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidTokenExceptionTest {

    @Test
    void testConstructorDoesNotThrow(){
        assertDoesNotThrow( () -> new InvalidTokenException("Exception"));
    }

}