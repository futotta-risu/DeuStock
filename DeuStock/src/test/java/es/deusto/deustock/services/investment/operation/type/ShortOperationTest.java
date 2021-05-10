package es.deusto.deustock.services.investment.operation.type;

import es.deusto.deustock.data.DeuStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortOperationTest {

    private DeuStock stock;

    @Test
    void testConstructor(){
        // Given
        Operation operation = new ShortOperation(20, 30);

        // When

        // Then
        assertNotNull(operation);
    }

    @Test
    void getOpenPrice() {
        // Given
        Operation operation = new ShortOperation(20, 30);

        // When
        double result = operation.getOpenPrice();

        // Then
        assertEquals(0, result);
    }

    @Test
    void getClosePrice() {
        // Given
        Operation operation = new ShortOperation(20, 40);

        // When
        double result = operation.getClosePrice(25);

        // Then
        assertEquals(0, result);
    }

    @Test
    void getType() {
        // Given
        Operation operation = new ShortOperation(20, 20);

        // When
        OperationType type = operation.getType();

        // Then
        assertEquals(OperationType.SHORT, type);
    }
}