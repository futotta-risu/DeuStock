package es.deusto.deustock.simulation.investment.operations;

import es.deusto.deustock.data.DeuStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongOperationTest {

    private DeuStock stock;

    @BeforeEach
    public void setUp(){
        stock = new DeuStock("BB").setPrice(20);
    }


    @Test
    void testConstructor(){
        // Given
        Operation operation = new LongOperation(stock, 30);

        // When

        // Then
        assertNotNull(operation);
    }

    @Test
    void getOpenPrice() {
        // Given
        Operation operation = new LongOperation(stock, 30);

        // When
        double result = operation.getOpenPrice();

        // Then
        assertEquals(600, result);
    }

    @Test
    void getClosePrice() {
        // Given
        Operation operation = new LongOperation(stock, 40);

        // When
        double result = operation.getClosePrice();

        // Then
        assertEquals(800, result);
    }

    @Test
    void getType() {
        // Given
        Operation operation = new LongOperation(stock, 20);

        // When
        OperationType type = operation.getType();

        // Then
        assertEquals(OperationType.LONG, type);
    }
}