package es.deusto.deustock.resources.stocks;

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
import es.deusto.deustock.services.investment.operations.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;

import java.sql.SQLException;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class HoldingsListResourceTest{
	
    private UserDAO mockUserDAO;
    private WalletDAO mockWalletDAO;
    private StockHistoryDAO mockStockHistoryDAO;
    private StockDataAPIGateway mockStockGateway;



    @BeforeEach
    void setUp() throws Exception {
    	mockUserDAO = mock(UserDAO.class);
    	mockWalletDAO = mock(WalletDAO.class);
    	mockStockHistoryDAO = mock(StockHistoryDAO.class);
    	mockStockGateway = mock(StockDataAPIGateway.class);
    }
    
    void setMocksToResource(HoldingsListResources holdingsListResource){
    	holdingsListResource.setUserDAO(mockUserDAO);
    	holdingsListResource.setWalletDAO(mockWalletDAO);
    	holdingsListResource.setStockHistoryDAO(mockStockHistoryDAO);
    	holdingsListResource.setStockGateway(mockStockGateway);
    }

    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    void testGetHoldingsReturnsIllegalArgumentException() throws SQLException {
    	//Given
        HoldingsListResources holdingsListResource = new HoldingsListResources();
        when(mockUserDAO.get(anyString())).thenReturn(null);
        setMocksToResource(holdingsListResource);

        //When

          	
        //Then
        assertThrows(
                IllegalArgumentException.class,
                () -> holdingsListResource.getHoldings("Test")
        );

    }

    @Test
    @DisplayName("Test get holdings list returns status 200")
    void testGetHoldingsListReturns200() throws StockNotFoundException, SQLException {
        //Given
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(20);

        wallet.addHistory(
                new StockHistory(
                        wallet, stock, 20.0, 10.0, OperationType.LONG
                )
        );

        User user = new User("Test", "Pass");
        user.setWallet(wallet);

        when(mockUserDAO.get(anyString())).thenReturn(user);
        when(mockStockGateway.getStockData(any())).thenReturn(stock);


        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        //When
        when(mockUserDAO.get(anyString())).thenReturn(user);;

        setMocksToResource(holdingsListResource);
        Response response = holdingsListResource.getHoldings("Test");

        //Then
        assertEquals(200, response.getStatus());
    }

    
    @Test
    @DisplayName("Test reset holdings returns 401")
    void testResetHoldingsReturns401() throws SQLException {
    	//Given

        //When
        when(mockUserDAO.get(anyString())).thenReturn(null);
    	
        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);
        Response response = holdingsListResource.resetHoldings("Test");
          	
        //Then
        assertEquals(401, response.getStatus());
    }

    @Test
    @DisplayName("Test reset holdings returns 200")
    void testResetHoldingsReturns200() throws StockNotFoundException, SQLException {
    	//Given
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("BB").setPrice(20);

        wallet.addHistory(
            new StockHistory(
                wallet, stock, 20.0, 10.0, OperationType.LONG
            )
        );

        User user = new User("Test", "Pass");
    	user.setWallet(wallet);

        when(mockUserDAO.get(anyString())).thenReturn(user);
        doNothing().when(mockWalletDAO).update(any());
        when(mockStockHistoryDAO.getStockHistory(anyString())).thenReturn(wallet.getHistory());
        doNothing().when(mockStockHistoryDAO).update(any());

        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);

        //When

        Response response = holdingsListResource.resetHoldings("Test");

        //Then
        assertDoesNotThrow(() -> holdingsListResource.resetHoldings("Test"));
        assertEquals(200, response.getStatus());
    }




}
