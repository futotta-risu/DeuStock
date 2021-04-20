package es.deusto.deustock.dao;


import java.util.Date;
import java.util.List;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.log.DeuLogger;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.data.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Testea los metodos de acceso a datos de usuario en la BD
 * 
 * @author landersanmillan
 */
@Tag("dbmanager")
public class UserDAOTest {

	/**
	 * Tests User creation
	*/
	@Test
    public void testUserCreation() {	
        
		User user1 = new User("UserDAOTest1", "password")
        				 .setFullName("fullName")
        				 .setBirthDate(new Date(1234567890))
        				 .setCountry("country")
        				 .setDescription("description");
		
        User user2 = new User("UserDAOTest2", "password2")
				 .setFullName("fullName2")
				 .setBirthDate(new Date(1234567890))
				 .setCountry("country2")
				 .setDescription("description2");
        
        UserDAO.getInstance().storeUser(user1);
        UserDAO.getInstance().storeUser(user2);
	}

	
	/**
	 * Tests User queries
	*/
	@Test
    public void testUserQuery() {
		User user1 = new User("UserDAOTest1", "password")
				.setFullName("fullName")
				.setBirthDate(new Date(1234567890))
				.setCountry("country")
				.setDescription("description");
		UserDAO.getInstance().storeUser(user1);
		User user = UserDAO.getInstance().getUser("UserDAOTest1");
		assertNotNull(user);

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
