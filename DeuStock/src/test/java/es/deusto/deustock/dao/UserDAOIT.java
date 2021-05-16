package es.deusto.deustock.dao;

//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Date;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.deustock.data.User;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Testea los metodos de acceso a datos de usuario en la BD
 * 
 * @author landersanmillan
 */
public class UserDAOIT{
	
	private UserDAO userDAO;

	@BeforeEach
	public void setUp(){
		userDAO = UserDAO.getInstance();
	}

	/**
	 * Tests User creation
	 * @throws Exception 
	*/
	@Test
    public void testUserCreation() throws Exception {	
		User user3 = new User("usernameTest3", "passTest3")
				 .setFullName("fullNameTest3")
				 .setBirthDate(new Date(1234567890))
				 .setCountry("SPAIN")
				 .setDescription("descriptionTest3");
		
		UserDAO.getInstance().store(user3);

		assertNotNull(UserDAO.getInstance().get("usernameTest3"));
	}

	
	/**
	 * Tests user deletion
	 * @throws Exception 
	*/
	@Test
    public void testUserDeletion() throws Exception {
		User user3 = new User("userToDelete", "passTest3")
				.setFullName("fullNameTest3")
				.setBirthDate(new Date(1234567890))
				.setCountry("SPAIN")
				.setDescription("descriptionTest3");

		UserDAO.getInstance().store(user3);

		assertDoesNotThrow( () -> UserDAO.getInstance().delete("userToDelete") );
    }
	
	/**
	 * Tests user update
	 * @throws Exception 
	*/
	@Test
	public void testUserUpdate() throws Exception {
		User user3 = new User("userToUpdate", "passTest3")
				.setFullName("fullNameTest3")
				.setBirthDate(new Date(1234567890))
				.setCountry("SPAIN")
				.setDescription("descriptionTest3");

		UserDAO.getInstance().store(user3);
		user3 = UserDAO.getInstance().get("userToUpdate");
		user3.setCountry("FRANCE");
		User finalUser = user3;
		assertDoesNotThrow( () -> UserDAO.getInstance().update(finalUser) );

		assertEquals("FRANCE", UserDAO.getInstance().get("userToUpdate").getCountry() );
	}
	


}
