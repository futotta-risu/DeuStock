package es.deusto.deustock.client.gateways.exceptions;

import static org.junit.jupiter.api.Assertions.*;

class ForbiddenExceptionTest {
    void testConstructorDoesNotThrow(){
        assertDoesNotThrow( () -> new ForbiddenException("Exception"));
    }
}