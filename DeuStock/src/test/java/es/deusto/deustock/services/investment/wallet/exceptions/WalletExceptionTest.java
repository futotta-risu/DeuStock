package es.deusto.deustock.services.investment.wallet.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletExceptionTest {

    @Test
    void testConstructor(){
        // Given

        // When
        WalletException exception = new WalletException("Test Exception");

        // Then
        assertNotNull(exception);
    }

}