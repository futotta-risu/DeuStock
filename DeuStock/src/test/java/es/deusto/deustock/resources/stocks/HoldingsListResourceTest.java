package es.deusto.deustock.resources.stocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;



import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;



import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.simulation.investment.OperationFactory;
import es.deusto.deustock.simulation.investment.WalletService;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.simulation.investment.operations.OperationType;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class HoldingsListResourceTest{
	
    private UserDAO mockUserDAO;
    private WalletDAO mockWalletDAO;
    private StockHistoryDAO mockStockHistoryDAO = StockHistoryDAO.getInstance();
    private StockHistoryDTO mockStockHistoryDTO;
    private WalletService mockWalletService;
    private StockDataAPIGateway mockStockGateway;
    private OperationFactory mockOperationFactory;
    private Operation mockOpenOperation;
    private Operation mockCloseOperation;



    @BeforeEach
    public void setUp() throws Exception {
    	mockUserDAO = mock(UserDAO.class);
    	mockWalletDAO = mock(WalletDAO.class);
    	mockStockHistoryDAO = mock(StockHistoryDAO.class);
    	mockStockHistoryDTO = mock(StockHistoryDTO.class);
    	mockWalletService = mock(WalletService.class);
    	mockStockGateway = mock(StockDataAPIGateway.class);
    	mockOperationFactory = mock(OperationFactory.class);
    	mockOpenOperation = mock(Operation.class);
    	mockCloseOperation = mock(Operation.class);

    }
    
    public void setMocksToResource(HoldingsListResources holdingsListResource){
    	holdingsListResource.setUserDAO(mockUserDAO);
    	holdingsListResource.setWalletDAO(mockWalletDAO);
    	holdingsListResource.setStockHistoryDAO(mockStockHistoryDAO);
    	holdingsListResource.setStockHistoryDTO(mockStockHistoryDTO);
    	holdingsListResource.setWalletService(mockWalletService);
    	holdingsListResource.setStockGateway(mockStockGateway);
    	holdingsListResource.setOperationFactory(mockOperationFactory);
    	holdingsListResource.setOpenOperation(mockOpenOperation);
    	holdingsListResource.setCloseOperation(mockCloseOperation);
    }

    @Test
    @DisplayName("Test get holdings list returns Illegal Argument Exception")
    public void testGetHoldingsReturnsIllegalArgumentException(){
    	//Given
    	IllegalArgumentException iae = new IllegalArgumentException("Username does not exit");

        //When
        when(mockUserDAO.getUser(anyString())).thenReturn(null);
          	
        //Then
        try {
        	HoldingsListResources holdingsListResource = new HoldingsListResources();
            setMocksToResource(holdingsListResource);
            Response response = holdingsListResource.getHoldings("Test");
		} catch (Exception e) {
			assertEquals(iae.getMessage(), e.getMessage());
		}
    }
    
    @Test
    @DisplayName("Test reset holdings returns 401")
    public void testResetHoldingsReturns401(){
    	//Given

        //When
        when(mockUserDAO.getUser(anyString())).thenReturn(null);
    	
        HoldingsListResources holdingsListResource = new HoldingsListResources();
        setMocksToResource(holdingsListResource);
        Response response = holdingsListResource.resetHoldings("Test");
          	
        //Then
        assertEquals(401, response.getStatus());
    }
    
//    @Test
//    @DisplayName("Test reset holdings returns 200")
//    public void testResetHoldingsReturns200(){
//    	//Given
//    	User user = new User("Test", "Pass");
//    	Wallet wallet = new Wallet();
//    	wallet.setMoney(9999);
//    	List<StockHistory> holdings = new ArrayList<StockHistory>();
//    	holdings.add(new StockHistory(new Wallet(), new DeuStock("Test"), 20.0, 10.0, OperationType.LONG));
//
//        //When
//        when(mockUserDAO.getUser(anyString())).thenReturn(user);
//        when(mockWalletDAO.getWallet(anyString())).thenReturn(wallet);
//        when(mockStockHistoryDAO.getStockHistory(anyString())).thenReturn(holdings);
//    	doNothing().when(mockStockHistoryDAO).update(any());
//    	doNothing().when(mockWalletDAO).update(any());
//
//        HoldingsListResources holdingsListResource = new HoldingsListResources();
//        setMocksToResource(holdingsListResource);
//        //Response response = holdingsListResource.resetHoldings("Test");
//          	
//        //Then
//        assertDoesNotThrow(() -> holdingsListResource.resetHoldings("Test"));
//        assertEquals(200, response.getStatus());
//    }
    
//    @Test
//    @DisplayName("Test get holdings list returns status 200")
//    public void testGetHoldingsListReturns200() throws StockNotFoundException{
//    	//Given
//    	User user = new User("Test", "Pass");
//    	List<StockHistory> holdings = new ArrayList<StockHistory>();
//    	holdings.add(new StockHistory(new Wallet(), new DeuStock("Test"), 20.0, 10.0, OperationType.LONG));
//    	List<StockHistoryDTO> holdingsDTO = new ArrayList<StockHistoryDTO>();
//    	mockStockHistoryDTO = new StockHistoryDTO().setOpenPrice(20.0).setAmount(10.0).setOperation(OperationType.LONG);
//    	holdingsDTO.add(mockStockHistoryDTO);
//    	
//        //When
//        when(mockUserDAO.getUser(anyString())).thenReturn(user);
//        
//        //doNothing().when(mockWalletService).setWallet(anyString());
//        
//        when(mockStockHistoryDAO.getStockHistory(anyString())).thenReturn(holdings);
//        when(mockStockHistoryDAO.getDTO(holdings)).thenReturn(holdingsDTO);
//        
//        when(mockStockHistoryDTO.getSymbol()).thenReturn("Test");
//        when(mockStockHistoryDTO.getOpenPrice()).thenReturn(20.0);
//        when(mockStockHistoryDTO.getAmount()).thenReturn(10.0);
//        when(mockStockHistoryDTO.getOperation()).thenReturn(OperationType.LONG);
//
//        when(mockOperationFactory.create(any(), any(), any())).thenReturn(mockOpenOperation);
//        
//        when(mockOpenOperation.getOpenPrice()).thenReturn(5.0);
//        when(mockOpenOperation.getClosePrice()).thenReturn(7.0);
//                
//    	HoldingsListResources holdingsListResource = new HoldingsListResources();
//        setMocksToResource(holdingsListResource);
//        Response response = holdingsListResource.getHoldings("Test");
//    	
//        //Then
//        assertEquals(200, response.getStatus());
//    }

}
