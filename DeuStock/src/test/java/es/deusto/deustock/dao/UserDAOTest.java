package es.deusto.deustock.dao;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;

/**
 * Testea los metodos de acceso a datos de usuario en la BD
 * 
 * @author landersanmillan
 */
public class UserDAOTest {


	/**
	 * Tests User creation
	*/
	@Test
    public void testUserCreation() {	
        User user1 = new User("UserDAOTest1", "password", "fullName", new Date(1234567890), "country", "description");
        User user2 = new User("UserDAOTest2", "password2", "fullName2", new Date(1234567890), "country2", "description2");
        UserDAO.getInstance().storeUser(user1);
        UserDAO.getInstance().storeUser(user2);
	}

	
	/**
	 * Tests User queries
	*/
	@Test
    public void testUserQuery() {
		User user = UserDAO.getInstance().getUser("username2");
		List<User> users = UserDAO.getInstance().getUsers();
		System.out.println(user);
		System.out.println(users);
	}
	
	/**
	 * Tests user deletion
	*/
	@Test
    public void testUserDeletion() {
        UserDAO.getInstance().deleteUser("username");
        System.out.println("Deleted User from DB: username");
    }


}
