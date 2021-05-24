package es.deusto.deustock.services.auth;

import es.deusto.deustock.dao.TokenDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.auth.Token;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.auth.exceptions.AuthException;
import es.deusto.deustock.services.auth.exceptions.InvalidTokenException;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import es.deusto.deustock.services.auth.exceptions.RegisterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.xml.registry.RegistryException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("service")
class AuthServiceTest {

    private UserDAO mockUserDAO;
    private TokenDAO mockTokenDAO;

    @BeforeEach
    void setUp(){
        mockUserDAO = mock(UserDAO.class);
        mockTokenDAO = mock(TokenDAO.class);
    }

    @Test
    void testLoginReturnsTokenWithCorrectData() throws SQLException {
        // TODO Fullfill
        User u = new User("TestUser", "TestPass");
        UserDTO uDTO = new UserDTO();
        uDTO.setUsername("TestUser");
        uDTO.setPassword("TestPass");

        when(mockUserDAO.get(any())).thenReturn(u);
        when(mockUserDAO.has(any())).thenReturn(true);
        when(mockUserDAO.getDTO(any())).thenReturn(uDTO);
        doReturn(true).doReturn(false).when(mockTokenDAO).has(any());
        doNothing().when(mockTokenDAO).store(any());

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);
        service.setTokenDAO(mockTokenDAO);

        assertDoesNotThrow( () -> service.login("TestUser", "TestPass"));
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
    void testRegisterThrowsErrorOnNullCreatedUser() throws SQLException {
        UserDTO u = new UserDTO();
        u.setUsername("TestUsername");
        u.setPassword("TestPass");

        when(mockUserDAO.create(any())).thenReturn(null);
        doNothing().when(mockUserDAO).store(any());
        when(mockUserDAO.has(any())).thenReturn(false);

        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);

        assertThrows(AuthException.class, () -> service.register(u) );
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

    @Test
    void testCreateTokenThrowsOnSQLExceptionOnHas() throws SQLException {
        User user = new User("TestUsername", "TestPass");
        doThrow(new SQLException("Exception")).when(mockTokenDAO).has(any());
        doNothing().when(mockTokenDAO).store(any());
        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);
        service.setTokenDAO(mockTokenDAO);

        assertThrows(
                RegisterException.class,
                () -> service.createToken(user)
        );
    }

    @Test
    void testCreateTokenThrowsOnSQLExceptionOnStore() throws SQLException {
        User user = new User("TestUsername", "TestPass");
        doReturn(true).doReturn(false).when(mockTokenDAO).has(any());
        doThrow(new SQLException("Exception")).when(mockTokenDAO).store(any());
        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);
        service.setTokenDAO(mockTokenDAO);

        assertThrows(
                RegisterException.class,
                () -> service.createToken(user)
        );
    }

    @Test
    void testCreateTokenThrowsOnWithFirstTokenExisting() throws SQLException {
        User user = new User("TestUsername", "TestPass");
        doReturn(true).doReturn(false).when(mockTokenDAO).has(any());
        doNothing().when(mockTokenDAO).store(any());
        AuthService service = new AuthService();
        service.setUserDAO(mockUserDAO);
        service.setTokenDAO(mockTokenDAO);

        assertDoesNotThrow( () -> service.createToken(user));
    }

    @Test
    void testValidateTokenReturnsUser() throws SQLException {
        // Given
        User user = new User("TestUsername", "TestPass");
        Token token = new Token("TestToken", user);
        doReturn(true).when(mockTokenDAO).has(any());
        doReturn(token).when(mockTokenDAO).get(any());

        AuthService service = new AuthService();
        service.setTokenDAO(mockTokenDAO);

        // When
        String result = service.validateToken("TestToken");

        // Then
        assertEquals("TestUsername", result);
    }

    @Test
    void testValidateTokenThrowsInvalidTokenExceptionOnSQLException() throws SQLException {
        // Given
        User user = new User("TestUsername", "TestPass");
        Token token = new Token("TestToken", user);
        doThrow(new SQLException("Exception")).when(mockTokenDAO).has(any());
        doReturn(token).when(mockTokenDAO).get(any());

        AuthService service = new AuthService();
        service.setTokenDAO(mockTokenDAO);

        // When

        // Then
        assertThrows(
                InvalidTokenException.class,
                () -> service.validateToken("TestToken")
        );
    }

    @Test
    void testValidateTokenThrowsInvalidTokenExceptionOnNonExistentToken() throws SQLException {
        User user = new User("TestUsername", "TestPass");
        Token token = new Token("TestToken", user);
        doReturn(false).when(mockTokenDAO).has(any());
        doReturn(token).when(mockTokenDAO).get(any());

        AuthService service = new AuthService();
        service.setTokenDAO(mockTokenDAO);

        // When

        // Then
        assertThrows(
                InvalidTokenException.class,
                () -> service.validateToken("TestToken")
        );
    }

}
