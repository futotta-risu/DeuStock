package es.deusto.deustock.resources.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

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
class UserDetailTest {
	private UserDAO mockUserDAO;
    
    @BeforeEach
    public void setUp() {
    	mockUserDAO = mock(UserDAO.class);
    }

    public void setMocksToResource(UserDetail userDetailResource){
    	userDetailResource.setUserDAO(mockUserDAO);
    }
    
    @Test
    @DisplayName("Test getUsername returns 200")
    public void testGetUsernameReturns200() throws SQLException {
    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
    	
    	//When
		when(mockUserDAO.get(anyString())).thenReturn(user);
    	when(mockUserDAO.getDTO(any())).thenReturn(userDTO);
  	    
    	UserDetail userDetailResource = new UserDetail();
        setMocksToResource(userDetailResource);
        Response response = userDetailResource.getUsername("AnyString");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test getUsername returns 401")
    public void testGetUsernameReturns401() throws SQLException {
    	//Given
    	
    	//When
		when(mockUserDAO.get(anyString())).thenReturn(null);
  	    
    	UserDetail userDetailResource = new UserDetail();
        setMocksToResource(userDetailResource);
        Response response = userDetailResource.getUsername("AnyString");
  	    
        //Then
		assertEquals(401, response.getStatus());
    }



}
