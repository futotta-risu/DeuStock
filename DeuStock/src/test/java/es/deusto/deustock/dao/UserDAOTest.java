package es.deusto.deustock.dao;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

/**
 * @author Erik B. Terres
 */
class UserDAOTest {
    private IDBManager dbManager;
    private UserDAO userDAO;

    @BeforeEach
    void setUp(){
        dbManager = mock(DBManager.class);
        userDAO = UserDAO.getInstance();
        userDAO.setDBManager(dbManager);
    }

    @Test
    @DisplayName("Test get User on existent user")
    void testGetOnExistentUser() throws SQLException {
        // Given
        User user = new User("TestUser" , "TestPass");
        when(dbManager.get(eq(User.class), anyString(),any())).thenReturn(user);

        // When
        final User result = userDAO.get("Test");

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Test get User on non existent user")
    void testGetOnNonExistentUser() throws SQLException {
        // Given
        when(dbManager.get(eq(User.class), anyString(),any())).thenReturn(null);

        // When
        final User result = userDAO.get("Test");

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test store function does not throw error")
    void testStore() throws SQLException {
        // Given
        User user = new User("TestUser" , "TestPass");
        doNothing().when(dbManager).store(any());

        // When

        // Then
        assertDoesNotThrow( () -> userDAO.store(user));
    }

    @Test
    @DisplayName("Test update function does not throw error")
    void testUpdate() throws SQLException {
        // Given
        User user = new User("TestUser" , "TestPass");
        doNothing().when(dbManager).update(any());

        // When

        // Then
        assertDoesNotThrow( () -> userDAO.update(user));
    }

    @Test
    @DisplayName("Test delete function does not throw error")
    void testDelete() throws SQLException {
        // Given
        doNothing().when(dbManager).delete(eq(User.class), anyString(),any());

        // When

        // Then
        assertDoesNotThrow( () -> userDAO.delete("TestUser"));
    }

    @Test
    @DisplayName("Test get Users on existent user")
    void testGeUsers(){
        // Given
        List<Object> users = new LinkedList<>();
        User user0 = new User("TestUser" , "TestPass");
        User user1 = new User("TestUser2" , "TestPass2");
        users.add(user0);
        users.add(user1);

        when(dbManager.getAll(eq(User.class))).thenReturn(users);

        // When
        final List<User> result = userDAO.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(user0, result.get(0));
        assertEquals(user1, result.get(1));
    }
    
    @Test
    @DisplayName("Test create a User from UserDTO")
    void testCreateUserFromUserDTO() {
    	UserDTO userDTO = new UserDTO();
    	userDTO.setCountry("countryTest1")
    		   .setDescription("descriptionTest1")
    		   .setFullName("fullnameTest1")
    		   .setPassword("passwordTest1")
    		   .setUsername("usernameTest1");
    	
    	User user = userDAO.create(userDTO);
    	
    	assertEquals("countryTest1", user.getCountry());
    	assertEquals("descriptionTest1", user.getDescription());
    	assertEquals("fullnameTest1", user.getFullName());
    	assertEquals("usernameTest1", user.getUsername());	
    }
    
    @Test
    @DisplayName("Test create a UserDTO from User")
    void testCreateUserDTOFromUser() {
    	User user = new User("usernameTest1", "passwordTest1");
    	user.setCountry("countryTest1")
    		   .setDescription("descriptionTest1")
    		   .setFullName("fullnameTest1");

    	
    	UserDTO userDTO = userDAO.getDTO(user);
    	
    	assertEquals("countryTest1", userDTO.getCountry());
    	assertEquals("descriptionTest1", userDTO.getDescription());
    	assertEquals("fullnameTest1", userDTO.getFullName());
    	assertEquals("usernameTest1", userDTO.getUsername());	
    	assertEquals("", userDTO.getPassword());	

    }
    
    @Test
    @DisplayName("Test delete a User from DB given User Object does not throw error")
    void testDeleteGivenUser() {
        doNothing().when(dbManager).delete(eq(User.class));

        assertDoesNotThrow( () -> userDAO.delete(new User("test", "pass")));
    }

}
