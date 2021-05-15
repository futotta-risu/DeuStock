package es.deusto.deustock.services.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.user.exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("service")
class UserServiceTest {
    private UserDAO mockUserDAO;

    @BeforeEach
    public void setUp(){
        mockUserDAO = mock(UserDAO.class);
    }

    @Test
    void testGetUserByUsernameReturnsUserDTO() throws SQLException, UserException {
        // Given
        User user = new User("TestUsername","TestPass");
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("TestUsername");
        userDTO.setPassword("TestPass");

        when(mockUserDAO.get(any())).thenReturn(user);
        when(mockUserDAO.getDTO(any())).thenReturn(userDTO);

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);

        // When
        UserDTO result = service.getUserByUsername("TestUsername");

        // Then
        assertNotNull(result);
    }

    @Test
    void testGetUserByUsernameThrowsExceptionOnNullUser() throws SQLException {
        // Given
        when(mockUserDAO.get(any())).thenReturn(null);

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertThrows(UserException.class, () -> service.getUserByUsername("TEstUsername"));
    }

    @Test
    void testGetUserByUsernameThrowsExceptionOnSQLException() throws SQLException {
        // Given
        when(mockUserDAO.get(any())).thenThrow(new SQLException("Exception"));

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertThrows(UserException.class, () -> service.getUserByUsername("TEstUsername"));
    }

    @Test
    void testUpdateUserDoesNotThrowException() throws SQLException {
        // Given
        User user = new User("TestUsername","TestPass");

        when(mockUserDAO.get(any())).thenReturn(user);
        doNothing().when(mockUserDAO).update(any());

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertDoesNotThrow(() -> service.updateUser(new UserDTO()));
    }

    @Test
    void testUpdateUserThrowsExceptionOnNullUser() throws SQLException {
        // Given
        when(mockUserDAO.get(any())).thenReturn(null);

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);
        // When

        // Then
        assertThrows(UserException.class, () -> service.updateUser(new UserDTO()));
    }

    @Test
    void testUpdateUserThrowsExceptionOnSQLException() throws SQLException {
        // Given
        when(mockUserDAO.get(any())).thenThrow(new SQLException("Exception"));

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);
        // When

        // Then
        assertThrows(UserException.class, () -> service.updateUser(new UserDTO()));
    }

    @Test
    void testDeleteUserDoesNotThrowException() throws SQLException {
        // Given
        User user = new User("TestUsername","TestPass");

        when(mockUserDAO.get(any())).thenReturn(user);
        doNothing().when(mockUserDAO).delete(anyString());

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertDoesNotThrow(() -> service.deleteUser("TestUsername"));
    }

    @Test
    void testDeleteUserThrowsExceptionOnNullUser() throws SQLException {
        // Given
        when(mockUserDAO.get(any())).thenReturn(null);

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);
        // When

        // Then
        assertThrows(UserException.class, () -> service.deleteUser("TestUsername"));
    }

    @Test
    void testDeleteUserThrowsExceptionOnSQLException() throws SQLException {
        when(mockUserDAO.get(any())).thenThrow(new SQLException("Exception"));

        UserService service = new UserService();
        service.setUserDAO(mockUserDAO);
        // When

        // Then
        assertThrows(UserException.class, () -> service.deleteUser("TestUsername"));
    }

}
