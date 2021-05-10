package es.deusto.deustock.resources.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import es.deusto.deustock.services.auth.AuthService;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import es.deusto.deustock.services.auth.exceptions.RegisterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.UserDAO;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;

import java.sql.SQLException;


/**
 * @author landersanmillan
 */
@Tag("server-resource")
class AuthResourceTest {

	private UserDAO mockUserDAO;
	private AuthService mockAuthService;
    
    @BeforeEach
    public void setUp() {
    	mockUserDAO = mock(UserDAO.class);
        mockAuthService = mock(AuthService.class);
    }

    public void setMocksToResource(AuthResource userResource){
    	userResource.setUserDAO(mockUserDAO);
    }
    
    @Test
    @DisplayName("Test login returns 200")
    public void testLoginReturns200() {
    	//Given
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
        when(mockAuthService.login(anyString(),anyString())).thenReturn(userDTO);

        AuthResource userResource = new AuthResource();
        userResource.setAuthService(mockAuthService);

    	//When
        Response response = userResource.login("Test", "Pass");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test login throws WebApplicationException with AuthService exception")
    public void testLoginThrowsWebApplicationExceptionWithServiceException() {
        //Given
        when(mockAuthService.login(anyString(),anyString())).thenThrow(
                new LoginException("Exception")
        );

        AuthResource userResource = new AuthResource();
        userResource.setAuthService(mockAuthService);

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> userResource.login("Test", "Pass")
        );

    }
    
    @Test
    @DisplayName("Test register returns 200")
    public void testRegisterReturns200() {
        //Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("userDTO");
        userDTO.setPassword("password");

        doNothing().when(mockAuthService).register(any());

        AuthResource userResource = new AuthResource();
        userResource.setAuthService(mockAuthService);

        //When
        Response response =  userResource.register(userDTO);

        //Then
        assertEquals(200, response.getStatus());

    }
    
    @Test
    @DisplayName("Test register already existing user throws WebApplicationException")
    public void testRegisterThrowsWebApplicationExceptionWithServiceException(){
        //Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("userDTO");
        userDTO.setPassword("password");

        doThrow(new RegisterException("AuthException")).when(mockAuthService).register(any());

        AuthResource userResource = new AuthResource();
        userResource.setAuthService(mockAuthService);

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> userResource.register(userDTO)
        );
    }
    
    @Test
    @DisplayName("Test delete user returns 200")
    public void testDeleteUserReturns200() throws SQLException {
    	//Given
    	User user = new User("Test", "Pass");

    	//When
		when(mockUserDAO.get(anyString())).thenReturn(user);
    	doNothing().when(mockUserDAO).delete(user);
  	    
    	AuthResource userResource = new AuthResource();
        setMocksToResource(userResource);
        Response response = userResource.delete("Test", "Pass");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test delete with wrong password returns 401")
    public void testDeleteUserWithWrongPassReturns401() throws SQLException {
    	//Given
    	User user = new User("Test", "Pass");
    	
    	//When
		when(mockUserDAO.get(anyString())).thenReturn(user);
    	doNothing().when(mockUserDAO).delete(user);
  	    
    	AuthResource userResource = new AuthResource();
        setMocksToResource(userResource);
        Response response = userResource.delete("Test", "WrongPassword");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test delete non existent user returns 401")
    public void testDeleteNonExistentUserReturns401() throws SQLException {
    	//Given
    	
    	//When
		when(mockUserDAO.get(anyString())).thenReturn(null);
  	    
    	AuthResource userResource = new AuthResource();
        setMocksToResource(userResource);
        Response response = userResource.delete("Test", "Pass");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
}
