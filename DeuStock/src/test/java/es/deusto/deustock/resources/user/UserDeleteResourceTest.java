package es.deusto.deustock.resources.user;

import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserDeleteResourceTest {

    private UserService mockUserService;

    @BeforeEach
    void setUp(){
        mockUserService = mock(UserService.class);
    }

    @Test
    @DisplayName("Test delete user returns 200")
    void testDeleteUserReturns200() throws WebApplicationException, UserException {
        //Given

        //When
        doNothing().when(mockUserService).deleteUser(anyString(),anyString());
        UserDeleteResource resource = new UserDeleteResource();
        resource.setUserService(mockUserService);

        Response response = resource.delete("Test", "Pass");

        //Then
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test delete with wrong password returns 401")
    void testDeleteUserWithWrongPassReturns401() throws UserException {
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
