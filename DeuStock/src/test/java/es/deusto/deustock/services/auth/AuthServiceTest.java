package es.deusto.deustock.services.auth;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.auth.exceptions.AuthException;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("service")
class AuthServiceTest {

    private UserDAO mockUserDAO;

    @BeforeEach
    void setUp(){
        mockUserDAO = mock(UserDAO.class);
    }

    @Test
    void testLoginReturnsUserDTOWithCorrectData() throws SQLException {
        User u = new User("TestUser", "TestPass");
        UserDTO uDTO = new UserDTO();
        uDTO.setUsername("TestUser");
        uDTO.setPassword("TestPass");

        when(mockUserDAO.get(any())).thenReturn(u);
        when(mockUserDAO.has(any())).thenReturn(true);
        when(mockUserDAO.getDTO(any())).thenReturn(uDTO);

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        UserDTO userDTO = service.login("TestUser", "TestPass");
        assertEquals("TestUser" , userDTO.getUsername());
    }

    @Test
    void testLoginThrowsLoginExceptionWithUnknownUser() throws SQLException {
        User u = new User("TestUser", "TestPass");
        when(mockUserDAO.get(any())).thenThrow(new SQLException("No user"));

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertThrows(LoginException.class, () -> service.login("TestUser", "TestPass") );
    }


    @Test
    void testLoginThrowsLoginExceptionWithIncorrectPassword() throws SQLException {
        User u = new User("TestUser", "TestPass");
        when(mockUserDAO.has(any())).thenReturn(true);
        when(mockUserDAO.get(any())).thenReturn(u);

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertThrows(LoginException.class, () -> service.login("TestUser", "TestPass2") );
    }

    @Test
    void testLoginThrowsErrorWithNoUser(){
        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);
        assertThrows(LoginException.class, () -> service.login(" ", "TestPass2") );
    }

    @Test
    void testLoginThrowsErrorSQLException() throws SQLException {
        User u = new User("TestUser", "TestPass");
        when(mockUserDAO.has(any())).thenThrow(new SQLException("Exception"));
        when(mockUserDAO.get(any())).thenReturn(u);

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertThrows(LoginException.class, () -> service.login("TestUser", "TestPass2") );
    }

    @Test
    void testRegisterDoesNotThrowWithCorrectData() throws SQLException {
        User user = new User("TestUsername", "TestPass");
        UserDTO u = new UserDTO();
        u.setUsername("TestUsername");
        u.setPassword("TestPass");

        doNothing().when(mockUserDAO).store(any());
        when(mockUserDAO.has(any())).thenReturn(false);
        when(mockUserDAO.create(any())).thenReturn(user);

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertDoesNotThrow(() -> service.register(u) );
    }

    @Test
    void testRegisterThrowsErrorWithRepeatedUser() throws SQLException {
        UserDTO u = new UserDTO();
        u.setUsername("TestUsername");
        u.setPassword("TestPass");

        doNothing().when(mockUserDAO).store(any());
        when(mockUserDAO.has(any())).thenReturn(true);

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertThrows(AuthException.class, () -> service.register(u) );
    }

    @Test
    void testRegisterThrowsErrorWithSQLExceptions() throws SQLException {
        UserDTO u = new UserDTO();
        u.setUsername("TestUsername");
        u.setPassword("TestPass");

        doNothing().when(mockUserDAO).store(any());
        when(mockUserDAO.has(any())).thenThrow(new SQLException("SQLException"));

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertThrows(AuthException.class, () -> service.register(u) );
    }

    @Test
    void testRegisterThrowsErrorWithNoUser() throws SQLException {
        User user = new User("", "TestPass");
        UserDTO u = new UserDTO();
        u.setUsername("");
        u.setPassword("TestPass");

        doNothing().when(mockUserDAO).store(any());
        when(mockUserDAO.has(any())).thenReturn(false);
        when(mockUserDAO.create(any())).thenReturn(user);

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertThrows(AuthException.class, () -> service.register(u) );
    }

}
