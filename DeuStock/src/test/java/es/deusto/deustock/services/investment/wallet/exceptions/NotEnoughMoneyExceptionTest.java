package es.deusto.deustock.services.investment.wallet.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotEnoughMoneyExceptionTest {
    @Test
    void testConstructor(){
        // Given

        // When
        NotEnoughMoneyException exception = new NotEnoughMoneyException("Test Exception");

        // Then
        assertNotNull(exception);
    }
}