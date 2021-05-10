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
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
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

    private OperationService mockOperationService;
    private WalletService mockWalletService;
    private StockService mockStockService;

    @BeforeEach
    void setUp(){
        mockOperationService =  mock(OperationService.class);
        mockWalletService =  mock(WalletService.class);
        mockStockService =  mock(StockService.class);
    }

    @Test
    void openOperationReturns200OnValidData() throws WebApplicationException, StockException, OperationException, WalletException {
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(mockStockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(mockOperationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenReturn(20.0);
        doNothing().when(mockWalletService).updateMoney(anyString(),anyDouble());
        doNothing().when(mockWalletService).addToHoldings(anyString(),any(),anyDouble(),any());

        OpenOperationResource resource = new OpenOperationResource();
        resource.setOperationService(mockOperationService);
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);

        // When
        Response response = resource.openOperation("LONG", "BB", "TestUsername", 20);

        // Then

        assertEquals(200, response.getStatus());

    }

    @Test
    void openOperationThrowsExceptionOnOperationException() throws WebApplicationException, StockException, OperationException, WalletException {
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(mockStockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(mockOperationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenThrow(new OperationException("Unknown Exception"));
        doNothing().when(mockWalletService).updateMoney(anyString(),anyDouble());
        doNothing().when(mockWalletService).addToHoldings(anyString(),any(),anyDouble(),any());

        OpenOperationResource resource = new OpenOperationResource();
        resource.setOperationService(mockOperationService);
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);

        // When

        // Then

        assertThrows(WebApplicationException.class,
                () -> resource.openOperation(
                        "LONG", "BB", "TestUsername", 20
                )
        );

    }

    @Test
    void openOperationThrowsExceptionOnWalletException() throws WebApplicationException, StockException, OperationException, WalletException {
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(mockStockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(mockOperationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenThrow(new OperationException("Unknown Exception"));
        doThrow(new WalletException("Wallet exception")).when(mockWalletService).updateMoney(anyString(),anyDouble());
        doNothing().when(mockWalletService).addToHoldings(anyString(),any(),anyDouble(),any());

        OpenOperationResource resource = new OpenOperationResource();
        resource.setOperationService(mockOperationService);
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);

        // When

        // Then

        assertThrows(WebApplicationException.class,
                () -> resource.openOperation(
                        "LONG", "BB", "TestUsername", 20
                )
        );

    }

    @Test
    void openOperationThrowsExceptionOnStockException() throws WebApplicationException, StockException, OperationException, WalletException {
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(mockStockService.getStockWithPrice(anyString())).thenThrow(new StockException("Unknown Stock"));
        when(mockOperationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenReturn(20.0);
        doNothing().when(mockWalletService).updateMoney(anyString(),anyDouble());
        doNothing().when(mockWalletService).addToHoldings(anyString(),any(),anyDouble(),any());

        OpenOperationResource resource = new OpenOperationResource();
        resource.setOperationService(mockOperationService);
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);

        // When

        // Then

        assertThrows(WebApplicationException.class,
                () -> resource.openOperation(
                        "LONG", "BB", "TestUsername", 20
                )
        );

    }

}