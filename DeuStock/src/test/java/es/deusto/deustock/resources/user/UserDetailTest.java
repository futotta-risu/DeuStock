package es.deusto.deustock.resources.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;


/**
 * @author landersanmillan
 */
@Tag("server-resource")
class UserDetailTest {
	private UserService mockUserService;
    
    @BeforeEach
    public void setUp() {
        mockUserService = mock(UserService.class);
    }

    public void setMocksToResource(UserDetail userDetailResource){
    	userDetailResource.setUserService(mockUserService);
    }
    
    @Test
    @DisplayName("Test getUsername returns 200")
    void testGetUsernameReturns200() throws UserException {

    	//Given
    	User user = new User("Test", "Pass");
    	UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");
    	
    	//When
		when(mockUserService.getUserByUsername(anyString())).thenReturn(userDTO);
  	    
    	UserDetail userDetailResource = new UserDetail();
        setMocksToResource(userDetailResource);
        Response response = userDetailResource.getUsername("AnyString");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    
    @Test
    @DisplayName("Test getUsername returns 401")
    void testGetUsernameReturns401() throws UserException {
    	//Given
    	
    	//When
        when(mockUserService.getUserByUsername(anyString())).thenThrow(new UserException("Exception"));
  	    
    	UserDetail userDetailResource = new UserDetail();
        setMocksToResource(userDetailResource);
  	    
        //Then
		assertThrows(
                WebApplicationException.class,
                () -> userDetailResource.getUsername("AnyString")
        );
    }

}
