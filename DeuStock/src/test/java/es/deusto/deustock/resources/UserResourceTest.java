package es.deusto.deustock.resources;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Tag("server-resource")
class UserResourceTest {
    private UserService mockUserService;
    private UserResource resource;

    @BeforeEach
    void setUp(){
        mockUserService = mock(UserService.class);
        resource = new UserResource();
        resource.setUserService(mockUserService);
    }

    @Test
    void testChangeDetailsReturns200OnCorrectData() throws UserException, WebApplicationException {
        //Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("TestUsername");
        userDTO.setPassword("TestPass");

        doNothing().when(mockUserService).updateUser(any());

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Principal mockPrincipal = mock(Principal.class);
        when(mockSecurityContext.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("TestUsername");

        //When
        Response response = resource.updateUser(userDTO, mockSecurityContext);

        //Then
        assertEquals(200, response.getStatus());
    }

    @Test
    void testChangeDetailsThrowsExceptionOnUserExceptions() throws UserException {
        //Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("TestUsername");
        userDTO.setPassword("TestPass");

        doThrow(new UserException("Exception test")).when(mockUserService).updateUser(any(UserDTO.class));

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Principal mockPrincipal = mock(Principal.class);
        when(mockSecurityContext.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("TestUsername");

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> resource.updateUser(userDTO, mockSecurityContext)
        );
    }

    @Test
    @DisplayName("Test delete user returns 200")
    void testDeleteUserReturns200() throws WebApplicationException, UserException {
        //Given
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Principal mockPrincipal = mock(Principal.class);
        when(mockSecurityContext.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("TestUsername");

        doNothing().when(mockUserService).deleteUser(anyString());

        //When

        Response response = resource.deleteUser( mockSecurityContext);

        //Then
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test delete with wrong password returns 401")
    void testDeleteUserWithWrongPassReturns401() throws UserException {
        //Given
        doThrow(new UserException("Exception test")).when(mockUserService).deleteUser(anyString());

        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Principal mockPrincipal = mock(Principal.class);
        when(mockSecurityContext.getUserPrincipal()).thenReturn(mockPrincipal);
        when(mockPrincipal.getName()).thenReturn("TestUsername");

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> resource.deleteUser( mockSecurityContext)
        );
    }

    @Test
    @DisplayName("Test getUsername returns 200")
    void testGetUsernameReturns200() throws UserException {
        //Given
        User user = new User("Test", "Pass");
        UserDTO userDTO = new UserDTO().setUsername("Test").setPassword("Pass");

        //When
        when(mockUserService.getUserByUsername(anyString())).thenReturn(userDTO);

        Response response = resource.getUser("AnyString");

        //Then
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test getUsername returns 401")
    void testGetUsernameReturns401() throws UserException {
        //Given

        //When
        when(mockUserService.getUserByUsername(anyString())).thenThrow(new UserException("Exception"));


        //Then
        assertThrows(
                WebApplicationException.class,
                () -> resource.getUser("AnyString")
        );
    }
}