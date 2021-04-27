package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Erik B. Terres
 */
public class UserDAOTest {
    private IDBManager dbManager;
    private UserDAO userDAO;

    @BeforeEach
    public void setUp(){
        dbManager = mock(DBManager.class);
        userDAO = UserDAO.getInstance();
        userDAO.setDBManager(dbManager);
    }

    @Test
    @DisplayName("Test get User on existent user")
    public void testGetOnExistentUser(){
        // Given
        User user = new User("TestUser" , "TestPass");
        when(dbManager.getObject(eq(User.class), anyString())).thenReturn(user);

        // When
        final User result = userDAO.getUser("Test");

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test get User on non existent user")
    public void testGetOnNonExistentUser(){
        // Given
        when(dbManager.getObject(eq(User.class), anyString())).thenReturn(null);

        // When
        final User result = userDAO.getUser("Test");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test store function does not throw error")
    public void testStore(){
        // Given
        User user = new User("TestUser" , "TestPass");
        doNothing().when(dbManager).storeObject(any());

        // When

        // Then
        assertDoesNotThrow( () -> userDAO.storeUser(user));
    }

    @Test
    @DisplayName("Test update function does not throw error")
    public void testUpdate(){
        // Given
        User user = new User("TestUser" , "TestPass");
        doNothing().when(dbManager).updateObject(any());

        // When

        // Then
        assertDoesNotThrow( () -> userDAO.updateUser(user));
    }

    @Test
    @DisplayName("Test delete function does not throw error")
    public void testDelete(){
        // Given
        doNothing().when(dbManager).deleteObject(eq(User.class), anyString());

        // When

        // Then
        assertDoesNotThrow( () -> userDAO.deleteUser("TestUser"));
    }

    @Test
    @DisplayName("Test get Users on existent user")
    public void testGeUsers(){
        // Given
        List<Object> users = new LinkedList<>();
        User user0 = new User("TestUser" , "TestPass");
        User user1 = new User("TestUser2" , "TestPass2");
        users.add(user0);
        users.add(user1);

        when(dbManager.getObjects(eq(User.class))).thenReturn(users);

        // When
        final List<User> result = userDAO.getUsers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user0, result.get(0));
        assertEquals(user1, result.get(1));
    }

}
