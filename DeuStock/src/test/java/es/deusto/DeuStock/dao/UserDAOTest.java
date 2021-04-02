package es.deusto.DeuStock.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import es.deusto.DeuStock.app.dao.UserDAO;
import es.deusto.DeuStock.app.data.User;

public class UserDAOTest {


	/**
	 * Tests User creation
	*/
	@Test
    public void testUserCreation() {
		
        User user1 = new User("username", "password", "fullName", new Date(1234567890), "country", "description");
        User user2 = new User("username2", "password2", "fullName2", new Date(1234567890), "country2", "description2");
        UserDAO.getInstance().storeUser(user1);
        UserDAO.getInstance().storeUser(user2);
	}

	
	/**
	 * Tests User queries
	*/
	@SuppressWarnings("unchecked")
	@Test
    public void testUserQuery() {
		User user = UserDAO.getInstance().getUser("username2");
		List<User> users = UserDAO.getInstance().getUsers();
		System.out.println(user);
		System.out.println(users);
	}
	
	@Test
	public void testUserPassword() {
		assertTrue(UserDAO.getInstance().checkPassword("username", "password"));
	}

	
//	/**
//	 * Test User update
//	 */
//	@Test
//	public void testUserUpdate() {
//        User userUpdate = UserDAO.getUser("username2");
//        userUpdate.setFullName("FULLNAME_UPDATED");
//        UserDAO.updateUser(userUpdate);
//        System.out.println(UserDAO.getUser("username2"));
//	}
//
//	
	/**
	 * Tests user deletion
	*/
	@Test
    public void testUserDeletion() {
        UserDAO.getInstance().deleteUser("username");
        System.out.println("Deleted User from DB: username");
    }


}
