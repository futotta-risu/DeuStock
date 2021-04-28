package es.deusto.deustock.resources.stocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.Wallet;


@Tag("server-resource")
class BalanceResourceTest {
	
	private UserDAO mockUserDAO;

    @BeforeEach
    public void setUp() throws Exception {
    	mockUserDAO = mock(UserDAO.class);
    }

    public void setMocksToResource(BalanceResource balanceResource){
    	balanceResource.setUserDAO(mockUserDAO);
    }

    
    @Test
    @DisplayName("Test get balance with null user throws 401")
    public void testGetBalanceWithNullUserReturns401(){
    	//Given
		
        //When
  	    when(mockUserDAO.getUser(anyString())).thenReturn(null);
  	    BalanceResource balanceResource = new BalanceResource();
        setMocksToResource(balanceResource);
        Response response = balanceResource.getBalance("");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test get balance returns 200")
    public void testGetBalanceWReturns200(){
    	//Given
		User user = new User("Test", "Pass");
		user.setWallet(new Wallet());
        
		//When
  	    when(mockUserDAO.getUser(anyString())).thenReturn(user);
  	    BalanceResource balanceResource = new BalanceResource();
        setMocksToResource(balanceResource);
        Response response = balanceResource.getBalance("");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }

}
