package es.deusto.deustock.services.investment.stock.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockExceptionTest {

    @Test
    void testConstructorNotNull(){
        // Given

        // When
        StockException exception = new StockException("Stock Exception");

        // Then
        assertNotNull(exception);
    }

}