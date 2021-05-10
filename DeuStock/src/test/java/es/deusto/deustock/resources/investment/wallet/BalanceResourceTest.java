package es.deusto.deustock.resources.investment.wallet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import static org.mockito.ArgumentMatchers.anyString;

import es.deusto.deustock.resources.investment.wallet.BalanceResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.Wallet;

import java.sql.SQLException;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class BalanceResourceTest {
	
	private UserDAO mockUserDAO;

    @BeforeEach
    public void setUp() {
    	mockUserDAO = mock(UserDAO.class);
    }

    public void setMocksToResource(BalanceResource balanceResource){
    	balanceResource.setUserDAO(mockUserDAO);
    }

    
    @Test
    @DisplayName("Test get balance with null user throws 401")
    void testGetBalanceWithNullUserReturns401() throws SQLException {
    	//Given
		
        //When
  	    when(mockUserDAO.get(anyString())).thenReturn(null);
  	    BalanceResource balanceResource = new BalanceResource();
        setMocksToResource(balanceResource);
        Response response = balanceResource.getBalance("");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test get balance returns 200")
    void testGetBalanceWReturns200() throws SQLException {
    	//Given
		User user = new User("Test", "Pass");
		user.setWallet(new Wallet());
        
		//When
  	    when(mockUserDAO.get(anyString())).thenReturn(user);
  	    BalanceResource balanceResource = new BalanceResource();
        setMocksToResource(balanceResource);
        Response response = balanceResource.getBalance("");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }

}
