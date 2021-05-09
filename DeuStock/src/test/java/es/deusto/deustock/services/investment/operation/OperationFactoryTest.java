package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.services.investment.operation.OperationFactory;
import es.deusto.deustock.services.investment.operation.type.LongOperation;
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.operation.type.ShortOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
class OperationFactoryTest {

    @Test
    void testCreateFailsOnNullStock() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();

        // When

        // Then
        assertThrows(
                NullPointerException.class,
                () -> factory.create(OperationType.LONG, null, 20)
        );
    }

    @Test
    void testCreateFailsOnZeroOrNegativeAmount() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();
        DeuStock stock = new DeuStock("BB").setPrice(20);
        // When

        // Then
        assertThrows(
                IllegalArgumentException.class,
                () -> factory.create(OperationType.LONG, stock, -1)
        );
    }

    @Test
    void createNullThrowsIllegalArgumentException() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();
        DeuStock stock = new DeuStock("BB").setPrice(20);

        // When

        // Then
        assertThrows(
                NullPointerException.class,
                () -> factory.create(null, stock, 20)
        );
    }

    @Test
    void createLONGReturnsInstanceOfLongOperation() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();
        DeuStock stock = new DeuStock("BB").setPrice(20);

        // When
        Operation operation = factory.create(OperationType.LONG, stock, 20);

        // Then
        assertTrue(operation instanceof LongOperation);
    }

    @Test
    void createSHORTReturnsInstanceOfShortOperation() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();
        DeuStock stock = new DeuStock("BB").setPrice(20);

        // When
        Operation operation = factory.create(OperationType.SHORT, stock, 20);

        // Then
        assertTrue(operation instanceof ShortOperation);
    }


}