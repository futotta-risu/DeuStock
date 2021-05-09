package es.deusto.deustock.resources.investment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import javax.ws.rs.core.Response;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.resources.investment.wallet.HoldingsListResources;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.wallet.WalletService;
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
    @BeforeEach
    void setUp() {
        walletService = mock(WalletService.class);
    }
    
    void setMocksToResource(HoldingsListResources holdingsListResource){
    	holdingsListResource.setWalletService(walletService);
    }

    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    void testHoldingListReturns200() {
    	//Given
        try {
            when(walletService.getHoldings(anyString())).thenReturn(new LinkedList<>());
        } catch (StockNotFoundException | SQLException e) {
            fail();
        }

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        //When

        Response response = holdingsListResource.getHoldings("TestString");
          	
        //Then
        assertEquals(200, response.getStatus());

    }

}
