package es.deusto.deustock.resources.investment.wallet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static org.mockito.ArgumentMatchers.anyString;

import es.deusto.deustock.resources.investment.wallet.BalanceResource;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.Wallet;

import java.security.Principal;
import java.sql.SQLException;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class BalanceResourceTest {
	
	private WalletService walletService;
    private BalanceResource resource;

    @BeforeEach
    public void setUp() {
        resource = new BalanceResource();
        walletService = mock(WalletService.class);
        resource.setWalletService(walletService);
    }

    
    @Test
    @DisplayName("Test get balance with null user throws 401")
    void testGetBalanceWithNullUserReturns401() throws SQLException, WalletException {
    	//Given

        when(walletService.getBalance(anyString())).thenThrow(new WalletException("Exception"));


        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Principal mockPrincipal = mock(Principal.class);
        when(mockSecurityContext.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("TestUsername");

        //When

        //Then
		assertThrows(
		        WebApplicationException.class,
                () -> resource.getBalance(mockSecurityContext)
        );
    }
    
    @Test
    @DisplayName("Test get balance returns 200")
    void testGetBalanceWReturns200() throws SQLException, WalletException {
    	//Given
		User user = new User("Test", "Pass");
		user.setWallet(new Wallet());

        when(walletService.getBalance(anyString())).thenReturn(20.0);

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Principal mockPrincipal = mock(Principal.class);
        when(mockSecurityContext.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("TestUsername");

        //When
        Response response = resource.getBalance(mockSecurityContext);
  	    
        //Then
		assertEquals(200, response.getStatus());
    }

}
