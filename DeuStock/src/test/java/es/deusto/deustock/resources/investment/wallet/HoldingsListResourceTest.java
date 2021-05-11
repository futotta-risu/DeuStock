package es.deusto.deustock.resources.investment.wallet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.resources.investment.wallet.HoldingsListResources;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class HoldingsListResourceTest{
	
    private WalletService walletService;
    private StockService stockService;
    private OperationService operationService;

    @BeforeEach
    void setUp() {
        walletService = mock(WalletService.class);
        operationService = mock(OperationService.class);
        stockService = mock(StockService.class);
    }
    
    void setMocksToResource(HoldingsListResources holdingsListResource){
    	holdingsListResource.setWalletService(walletService);
    	holdingsListResource.setOperationService(operationService);
    	holdingsListResource.setStockService(stockService);
    }

    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    void testHoldingListReturns200() throws WalletException, StockException, OperationException {
    	//Given
        DeuStock stock = new DeuStock("BB").setPrice(20.0);

        when(walletService.getHoldings(anyString())).thenReturn(new LinkedList<>());
        when(stockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(operationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        when(operationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenReturn(20.0);

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        //When

        Response response = holdingsListResource.getHoldings("TestString");
          	
        //Then
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    void testHoldingListReturns200OnList() throws WalletException, StockException, OperationException {
        //Given
        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        stockHistoryDTO.setSymbol("BB");
        stockHistoryDTO.setActualPrice(20.0);
        stockHistoryDTO.setActualValue(20.0);
        stockHistoryDTO.setAmount(20.0);
        stockHistoryDTO.setId(20);
        stockHistoryDTO.setOpenPrice(200.0);
        stockHistoryDTO.setOpenValue(200.0);
        stockHistoryDTO.setOperation(OperationType.LONG);

        List<StockHistoryDTO> holdings = new ArrayList<>();
        holdings.add(stockHistoryDTO);

        when(walletService.getHoldings(anyString())).thenReturn(holdings);
        when(stockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(operationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        when(operationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenReturn(20.0);

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        //When

        Response response = holdingsListResource.getHoldings("TestString");

        //Then
        assertEquals(200, response.getStatus());

    }

    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    void testHoldingListThrowsExceptionOnWalletException() throws WalletException, StockException, OperationException {
        //Given
        DeuStock stock = new DeuStock("BB").setPrice(20.0);

        when(walletService.getHoldings(anyString())).thenThrow(new WalletException("Exception"));
        when(stockService.getStockWithPrice(anyString())).thenReturn(stock);
        when(operationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        when(operationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenReturn(20.0);

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> holdingsListResource.getHoldings("TestString")
        );

    }

    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    void testHoldingListThrowsExceptionOnStockException() throws WalletException, StockException, OperationException {
        //Given
        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistoryDTO stockHistoryDTO = new StockHistoryDTO();
        stockHistoryDTO.setSymbol("BB");
        stockHistoryDTO.setActualPrice(20.0);
        stockHistoryDTO.setActualValue(20.0);
        stockHistoryDTO.setAmount(20.0);
        stockHistoryDTO.setId(20);
        stockHistoryDTO.setOpenPrice(200.0);
        stockHistoryDTO.setOpenValue(200.0);
        stockHistoryDTO.setOperation(OperationType.LONG);

        List<StockHistoryDTO> holdings = new ArrayList<>();
        holdings.add(stockHistoryDTO);

        when(walletService.getHoldings(anyString())).thenThrow(new WalletException("Exception"));
        when(stockService.getStockWithPrice(anyString())).thenThrow(new StockException("Exception"));
        when(operationService.getClosePrice(any(),anyDouble(),anyDouble(),anyDouble())).thenReturn(20.0);
        when(operationService.getOpenPrice(any(),anyDouble(),anyDouble())).thenReturn(20.0);

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> holdingsListResource.getHoldings("TestString")
        );

    }

    @Test
    void testResetHoldingsReturns200() throws WalletException {
        // Given
        doNothing().when(walletService).resetHoldings(anyString());

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        // When
        Response response = holdingsListResource.resetHoldings("TestString");

        //Then
        assertEquals(200, response.getStatus());
    }

    @Test
    void testResetHoldingsThrowsExceptionOnWalletException() throws WalletException {
        // Given
        doThrow(new WalletException("Exception")).when(walletService).resetHoldings(anyString());

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        // When
        
        //Then
        assertThrows(
                WebApplicationException.class,
                () -> holdingsListResource.resetHoldings("TestString")
        );
    }

}
