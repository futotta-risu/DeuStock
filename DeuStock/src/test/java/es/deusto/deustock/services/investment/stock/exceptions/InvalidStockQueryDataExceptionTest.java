package es.deusto.deustock.services.investment.stock.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidStockQueryDataExceptionTest {

    @Test
    void testConstructor(){
        // Given

        // When
        InvalidStockQueryDataException exception = new InvalidStockQueryDataException("Test Exception");

        // Then
        assertNotNull(exception);
    }

}