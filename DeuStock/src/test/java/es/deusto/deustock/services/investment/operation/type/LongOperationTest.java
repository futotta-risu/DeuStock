package es.deusto.deustock.services.investment.operation.type;

import es.deusto.deustock.data.DeuStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongOperationTest {

    @Test
    void testConstructor(){
        // Given
        Operation operation = new LongOperation(20, 30);

        // When

        // Then
        assertNotNull(operation);
    }

    @Test
    void getOpenPrice() {
        // Given
        Operation operation = new LongOperation(20, 30);

        // When
        double result = operation.getOpenPrice();

        // Then
        assertEquals(600, result);
    }

    @Test
    void getClosePrice() {
        // Given
        Operation operation = new LongOperation(30, 40);

        // When
        double result = operation.getClosePrice(20);

        // Then
        assertEquals(800, result);
    }

    @Test
    void getType() {
        // Given
        Operation operation = new LongOperation(20, 20);

        // When
        OperationType type = operation.getType();

        // Then
        assertEquals(OperationType.LONG, type);
    }
}