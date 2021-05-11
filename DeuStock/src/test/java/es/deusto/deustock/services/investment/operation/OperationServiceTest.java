package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.services.investment.operation.type.LongOperation;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OperationServiceTest {

    private OperationFactory factory;

    @BeforeEach
    public void setUp(){
        factory = mock(OperationFactory.class);
    }

    @Test
    void testGetOpenPriceDoesNotThrow(){
        // Given
        LongOperation operation = mock(LongOperation.class);
        when(factory.create(any(),anyDouble(),anyDouble())).thenReturn(operation);
        when(operation.getOpenPrice()).thenReturn(2.0);

        OperationService service = new OperationService();
        service.setOperationFactory(factory);

        // When

        // Then
        assertDoesNotThrow( () -> service.getOpenPrice(OperationType.LONG, 20.0, 30.0));
    }

    @Test
    void testGetClosePriceDoesNotThrow(){
        // Given
        LongOperation operation = mock(LongOperation.class);
        when(factory.create(any(),anyDouble(),anyDouble())).thenReturn(operation);
        when(operation.getClosePrice(anyDouble())).thenReturn(2.0);

        OperationService service = new OperationService();
        service.setOperationFactory(factory);

        // When

        // Then
        assertDoesNotThrow( () -> service.getClosePrice(OperationType.LONG, 20.0, 20.0,30.0));
    }

}
