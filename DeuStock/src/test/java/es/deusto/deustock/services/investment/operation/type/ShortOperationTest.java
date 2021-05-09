package es.deusto.deustock.services.investment.operation.type;

import es.deusto.deustock.data.DeuStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShortOperationTest {

    private DeuStock stock;

    @BeforeEach
    public void setUp(){
        stock = new DeuStock("BB").setPrice(20);
    }

    @Test
    void testConstructor(){
        // Given
        Operation operation = new ShortOperation(stock, 30);

        // When

        // Then
        assertNotNull(operation);
    }

    @Test
    void getOpenPrice() {
        // Given
        Operation operation = new ShortOperation(stock, 30);

        // When
        double result = operation.getOpenPrice();

        // Then
        assertEquals(0, result);
    }

    @Test
    void getClosePrice() {
        // Given
        Operation operation = new ShortOperation(stock, 40);

        // When
        double result = operation.getClosePrice();

        // Then
        assertEquals(0, result);
    }

    @Test
    void getType() {
        // Given
        Operation operation = new ShortOperation(stock, 20);

        // When
        OperationType type = operation.getType();

        // Then
        assertEquals(OperationType.SHORT, type);
    }
}