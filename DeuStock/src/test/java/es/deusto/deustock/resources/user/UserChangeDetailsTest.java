package es.deusto.deustock.resources.user;

import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

        //When
        doNothing().when(mockUserService).updateUser(any());
        UserChangeDetails resource = new UserChangeDetails();
        resource.setUserService(mockUserService);

        Response response = resource.changeDetails(userDTO);

        //Then
        assertEquals(200, response.getStatus());
    }

    @Test
    void testChangeDetailsThrowsExceptionOnUserExceptions() throws UserException {
        //Given
        doThrow(new UserException("Exception test")).when(mockUserService).deleteUser(anyString(),anyString());
        UserDeleteResource resource = new UserDeleteResource();
        resource.setUserService(mockUserService);

        //When

        //Then
        assertThrows(
                WebApplicationException.class,
                () -> resource.delete("Test", "Pass")
        );
    }
}