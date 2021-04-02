package es.deusto.deustock.jdo;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;

public class JDOTest {


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
	


	
//	/**
//	 * Test User update
//	 */
//	@Test
//	public void testUserUpdate() {
//        User userUpdate = UserDAO.getInstance().getUser("username2");
//        userUpdate.setFullName("FULLNAME_UPDATED");
//        UserDAO.getInstance().updateUser(userUpdate);
//        System.out.println(UserDAO.getInstance().getUser("username2"));
//	}
//
//	
//	/**
//	 * Tests user deletion
//	*/
//	@Test
//    public void testUserDeletion() {
//        User user1 = UserDAO.getInstance().getUser("username");
//        UserDAO.getInstance().deleteUser(user1);
//        System.out.println("Deleted User from DB:" + user1.getUsername());
//    }


}