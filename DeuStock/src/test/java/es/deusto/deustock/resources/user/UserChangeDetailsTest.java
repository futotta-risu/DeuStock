package es.deusto.deustock.resources.user;

import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserChangeDetailsTest {
    private UserService mockUserService;

    @BeforeEach
    void setUp(){
        mockUserService = mock(UserService.class);
    }

    @Test
    void testChangeDetailsReturns200OnCorrectData() throws UserException, WebApplicationException {
        //Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("TestUsername");
        userDTO.setPassword("TestPass");

        doNothing().when(mockUserService).updateUser(any());
        UserChangeDetails resource = new UserChangeDetails();
        resource.setUserService(mockUserService);

        //When
        Response response = resource.changeDetails(userDTO);

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
        UserChangeDetails resource = new UserChangeDetails();
        resource.setUserService(mockUserService);

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> resource.changeDetails(userDTO)
        );
    }
}