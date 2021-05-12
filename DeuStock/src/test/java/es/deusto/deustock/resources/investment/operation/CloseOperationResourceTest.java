package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;

import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static es.deusto.deustock.services.investment.operation.type.OperationType.LONG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("server-resource")
class CloseOperationResourceTest {

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
    void closeOperationReturns200OnValidOperation() throws WebApplicationException, WalletException, StockException, OperationException {
        // Given
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");
        StockHistory stockHistory = new StockHistory(
                wallet, stockNoPrice, 10, 15, LONG);

        when(mockWalletService.getStockHistory(anyString())).thenReturn(stockHistory);
        when(mockStockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(mockOperationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        doNothing().when(mockWalletService).updateMoneyByWalletID(anyString(),anyDouble());
        doNothing().when(mockWalletService).closeStockHistory(anyString());

        CloseOperationResource resource = new CloseOperationResource();
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);
        resource.setOperationService(mockOperationService);

        // When
        Response response = resource.closeOperation("TestString");

        // Then
        assertEquals(200, response.getStatus());
    }

    @Test
    void closeOperationReturns401OnClosedHistory() throws WebApplicationException, WalletException, OperationException, StockException {
        // Given
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");
        StockHistory stockHistory = new StockHistory(
                wallet, stockNoPrice, 10, 15, LONG);
        stockHistory.setClosed(true);

        when(mockWalletService.getStockHistory(anyString())).thenReturn(stockHistory);
        when(mockStockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(mockOperationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        doNothing().when(mockWalletService).updateMoneyByWalletID(anyString(),anyDouble());
        doNothing().when(mockWalletService).closeStockHistory(anyString());

        CloseOperationResource resource = new CloseOperationResource();
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);
        resource.setOperationService(mockOperationService);

        // When

        // Then
        assertThrows(WebApplicationException.class, ()-> resource.closeOperation("TestString"));
    }


    @Test
    void closeOperationThrowsOnWalletException() throws WebApplicationException, WalletException, OperationException, StockException {
        // Given
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");
        StockHistory stockHistory = new StockHistory(
                wallet, stockNoPrice, 10, 15, LONG);
        stockHistory.setClosed(true);

        when(mockWalletService.getStockHistory(anyString())).thenThrow(new WalletException("Exception"));
        when(mockStockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(mockOperationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        doNothing().when(mockWalletService).updateMoneyByWalletID(anyString(),anyDouble());
        doNothing().when(mockWalletService).closeStockHistory(anyString());

        CloseOperationResource resource = new CloseOperationResource();
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);
        resource.setOperationService(mockOperationService);

        // When

        // Then
        assertThrows(WebApplicationException.class, ()-> resource.closeOperation("TestString"));
    }

    @Test
    void closeOperationThrowsOnStockException() throws WebApplicationException, WalletException, OperationException, StockException {
        // Given
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(20);
        DeuStock stockNoPrice = new DeuStock("BB");
        StockHistory stockHistory = new StockHistory(
                wallet, stockNoPrice, 10, 15, LONG);
        stockHistory.setClosed(true);


        when(mockWalletService.getStockHistory(anyString())).thenReturn(stockHistory);
        when(mockStockService.getStockWithPrice(anyString())).thenThrow(new StockException("Exception"));
        when(mockOperationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        doNothing().when(mockWalletService).updateMoneyByWalletID(anyString(),anyDouble());
        doNothing().when(mockWalletService).closeStockHistory(anyString());

        CloseOperationResource resource = new CloseOperationResource();
        resource.setStockService(mockStockService);
        resource.setWalletService(mockWalletService);
        resource.setOperationService(mockOperationService);

        // When

        // Then
        assertThrows(WebApplicationException.class, ()-> resource.closeOperation("TestString"));
    }
}