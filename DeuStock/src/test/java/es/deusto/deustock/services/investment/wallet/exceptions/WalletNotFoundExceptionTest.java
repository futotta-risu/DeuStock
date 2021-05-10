package es.deusto.deustock.services.investment.wallet.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletNotFoundExceptionTest {
    @Test
    void testConstructorNotNull(){
        // Given

        // When
        WalletNotFoundException exception = new WalletNotFoundException("Test Exception");

        // Then
        assertNotNull(exception);
    }

}