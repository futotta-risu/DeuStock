package es.deusto.deustock.resources.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.UserDAO;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;


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
    public void testLoginReturns200(){
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
    	
    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(user);
    	when(mockUserDAO.getDTO(any())).thenReturn(userDTO);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.login("Test", "Pass");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test login returns 401")
    public void testLoginWithNonExistentUserReturns401(){
    	//Given
    	
    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(null);
    	when(mockUserDAO.getDTO(any())).thenReturn(null);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.login("Test", "Pass");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    

    @Test
    @DisplayName("Test login wrong pass returns 401")
    public void testLoginWrongPassReturns401(){
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
    	
    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(user);
    	when(mockUserDAO.getDTO(any())).thenReturn(userDTO);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.login("Test", "WrongPassword");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test register returns 200")
    public void testRegisterReturns200(){
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
	
    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(null);
    	when(mockUserDAO.create(any())).thenReturn(user);
    	doNothing().when(mockUserDAO).storeUser(user);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.register(userDTO);
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test register already existing user returns 401")
    public void testRegisterExistingUserReturns401(){
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");

    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(user);
 	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.register(userDTO);
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test delete user returns 200")
    public void testDeleteUserReturns200(){
    	//Given
    	User user = new User("Test", "Pass");
    	
    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(user);
    	doNothing().when(mockUserDAO).deleteUser(user);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.delete("Test", "Pass");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test delete with wrong password returns 401")
    public void testDeleteUserWithWrongPassReturns401(){
    	//Given
    	User user = new User("Test", "Pass");
    	
    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(user);
    	doNothing().when(mockUserDAO).deleteUser(user);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.delete("Test", "WrongPassword");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test delete non existent user returns 401")
    public void testDeleteNonExistentUserReturns401(){
    	//Given
    	
    	//When
		when(mockUserDAO.getUser(anyString())).thenReturn(null);
  	    
    	UserResource userResource = new UserResource();
        setMocksToResource(userResource);
        Response response = userResource.delete("Test", "Pass");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }
    
}
