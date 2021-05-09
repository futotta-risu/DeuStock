package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.resources.investment.operation.OpenOperationResource;
import es.deusto.deustock.services.investment.operation.OperationService;

import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Tag("server-resource")
class OpenOperationResourceTest {

    private OperationService operationService;

    @BeforeEach
    void setUp(){
        operationService =  mock(OperationService.class);
    }

    @Test
    void openOperationReturns200OnValidData() throws WebApplicationException {

        try {
            doNothing().when(operationService).openOperation(any(),anyString(),anyString(),anyDouble());
        } catch (OperationException | SQLException | StockNotFoundException e) {
            fail();
        }

        OpenOperationResource resource = new OpenOperationResource();
        resource.setOperationService(operationService);

        Response response = resource.openOperation(
                "LONG","BB","testUsername",20
        );

        assertEquals(200, response.getStatus());

    }
}