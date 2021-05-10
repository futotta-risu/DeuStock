package es.deusto.deustock.services.investment.operation.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationExceptionTest {

    @Test
    void testConstructor(){
        // Given

        // When
        OperationException exception = new OperationException("Test Exception");

        // Then
        assertNotNull(exception);
    }

}