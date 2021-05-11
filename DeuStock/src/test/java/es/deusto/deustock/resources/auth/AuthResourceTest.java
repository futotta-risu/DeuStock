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

import es.deusto.deustock.data.dto.UserDTO;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class AuthResourceTest {

	private AuthService mockAuthService;
    
    @BeforeEach
    public void setUp() {
        mockAuthService = mock(AuthService.class);
    }

    
    @Test
    @DisplayName("Test login returns 200")
    void testLoginReturns200() {
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
    void testLoginThrowsWebApplicationExceptionWithServiceException() {
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
    void testRegisterReturns200() {
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
    void testRegisterThrowsWebApplicationExceptionWithServiceException(){
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
    
}
