package es.deusto.deustock.services.investment.operation;

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
    void testCreateFailsOnZeroOrNegativeAmount() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();
        // When

        // Then
        assertThrows(
                IllegalArgumentException.class,
                () -> factory.create(OperationType.LONG, 20, -1)
        );
    }

    @Test
    void createNullThrowsIllegalArgumentException() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();

        // When

        // Then
        assertThrows(
                NullPointerException.class,
                () -> factory.create(null, 20, 20)
        );
    }

    @Test
    void createLONGReturnsInstanceOfLongOperation() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();

        // When
        Operation operation = factory.create(OperationType.LONG, 20, 20);

        // Then
        assertTrue(operation instanceof LongOperation);
    }

    @Test
    void createSHORTReturnsInstanceOfShortOperation() {
        // Given
        OperationFactory factory = OperationFactory.getInstance();
        // When
        Operation operation = factory.create(OperationType.SHORT, 20, 20);

        // Then
        assertTrue(operation instanceof ShortOperation);
    }


}