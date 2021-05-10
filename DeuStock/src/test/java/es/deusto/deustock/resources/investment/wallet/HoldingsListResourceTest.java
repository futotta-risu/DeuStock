package es.deusto.deustock.resources.investment.wallet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.mockito.Mockito.doNothing;

import javax.ws.rs.core.Response;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
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

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;

import java.sql.SQLException;
import java.util.LinkedList;

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

}
