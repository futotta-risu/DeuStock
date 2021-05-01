package es.deusto.deustock.resources.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import es.deusto.deustock.services.auth.AuthService;
import es.deusto.deustock.services.auth.exceptions.LoginException;
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
class UserResourceTest {

	private UserDAO mockUserDAO;
    
    @BeforeEach
    public void setUp() {
    	mockUserDAO = mock(UserDAO.class);
    }

    public void setMocksToResource(UserResource userResource){
    	userResource.setUserDAO(mockUserDAO);
    }
    
    @Test
    @DisplayName("Test login returns 200")
    public void testLoginReturns200() throws SQLException {
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);
    	//When
		when(mockUserDAO.get(anyString())).thenReturn(user);
        when(mockUserDAO.has(any())).thenReturn(true);
    	when(mockUserDAO.getDTO(any())).thenReturn(userDTO);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        userResource.setAuthService(service);
        Response response = userResource.login("Test", "Pass");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test login returns 401")
    public void testLoginWithNonExistentUserReturns401() throws SQLException {
    	//Given
        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

    	//When
		when(mockUserDAO.get(anyString())).thenThrow(new LoginException("Login error"));
    	when(mockUserDAO.getDTO(any())).thenReturn(null);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        userResource.setAuthService(service);
  	    
        //Then
        assertThrows(
                WebApplicationException.class,
                () -> userResource.login("Test", "Pass")
        );
    }
    

    @Test
    @DisplayName("Test login wrong pass returns 401")
    public void testLoginWrongPassReturns401() throws SQLException {
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

    	//When
		when(mockUserDAO.get(anyString())).thenReturn(user);
    	when(mockUserDAO.getDTO(any())).thenReturn(userDTO);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        userResource.setAuthService(service);

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> userResource.login("Test", "WrongPassword")
        );

    }
    
    @Test
    @DisplayName("Test register returns 200")
    public void testRegisterReturns200() throws SQLException {
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

    	//When
		when(mockUserDAO.get(anyString())).thenThrow(new SQLException("User not in DB"));
    	when(mockUserDAO.create(any())).thenReturn(user);
    	doNothing().when(mockUserDAO).store(user);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        userResource.setAuthService(service);
        Response response = userResource.register(userDTO);
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test register already existing user returns 401")
    public void testRegisterExistingUserReturns401() throws SQLException {
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

    	//When
		when(mockUserDAO.get(any())).thenReturn(user);
        when(mockUserDAO.has(any())).thenReturn(true);

    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        userResource.setAuthService(service);
        
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
  	    
    	UserResource userResource = new UserResource();
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
  	    
    	UserResource userResource = new UserResource();
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
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.delete("Test", "Pass");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
}
