package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.resources.investment.operation.CloseOperationResource;
import es.deusto.deustock.services.investment.operation.OperationFactory;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.operation.type.LongOperation;
import es.deusto.deustock.services.investment.operation.type.Operation;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static es.deusto.deustock.services.investment.operation.type.OperationType.LONG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("server-resource")
class CloseOperationResourceTest {

    private OperationService operationService;

    @BeforeEach
    public void setUp(){
        operationService =  mock(OperationService.class);
    }

    @Test
    void closeOperationReturns200OnValidOperation() throws WebApplicationException {

        // Given
        try {
            doNothing().when(operationService).closeOperation(anyString());
        } catch (OperationException | StockNotFoundException e) {
            fail();
        }

        CloseOperationResource resource = new CloseOperationResource();
        resource.setOperationService(operationService);

        // When
        Response response = resource.closeOperation("TestString");

        // Then
        assertEquals(200, response.getStatus());
    }
}