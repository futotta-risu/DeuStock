<<<<<<< HEAD:DeuStock/src/test/java/es/deusto/deustock/jdo/JDOTest.java
package es.deusto.deustock.jdo;
=======
package es.deusto.DeuStock.dao;
>>>>>>> origin/Crear-Usuarios:DeuStock/src/test/java/es/deusto/DeuStock/dao/UserDAOTest.java

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
	
<<<<<<< HEAD:DeuStock/src/test/java/es/deusto/deustock/jdo/JDOTest.java

=======
	/**
	 * Tests if User password is correct
	 */
	@Test
	public void testUserPassword() {
		assertTrue(UserDAO.getInstance().checkPassword("username", "password"));
	}
>>>>>>> origin/Crear-Usuarios:DeuStock/src/test/java/es/deusto/DeuStock/dao/UserDAOTest.java

	
//	/**
//	 * Tests User update
//	 */
//	@Test
//	public void testUserUpdate() {
//        User userUpdate = UserDAO.getInstance().getUser("username2");
//        userUpdate.setFullName("FULLNAME_UPDATED");
//        UserDAO.getInstance().updateUser(userUpdate);
//        System.out.println(UserDAO.getInstance().getUser("username2"));
//	}
<<<<<<< HEAD:DeuStock/src/test/java/es/deusto/deustock/jdo/JDOTest.java
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
=======
	
	/**
	 * Tests user deletion
	*/
	@Test
    public void testUserDeletion() {
        UserDAO.getInstance().deleteUser("username");
        System.out.println("Deleted User from DB: username");
    }
>>>>>>> origin/Crear-Usuarios:DeuStock/src/test/java/es/deusto/DeuStock/dao/UserDAOTest.java


}
