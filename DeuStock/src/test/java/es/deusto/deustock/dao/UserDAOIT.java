package es.deusto.deustock.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.FileInputStream;
import java.util.Date;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.data.User;


/**
 * Testea los metodos de acceso a datos de usuario en la BD
 * 
 * @author landersanmillan
 */
public class UserDAOIT extends DBTestCase{
  
	public UserDAOIT(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://127.0.0.1:3306/deustockdb?verifyServerCertificate=false&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "deustock_user1");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "12345");
    }

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/userTestingDatasets/user.xml"));
	}
	protected IDataSet getUserAddedDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/userTestingDatasets/userAdded.xml"));
	}
	protected IDataSet getUserDeletedDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/userTestingDatasets/userDeleted.xml"));
	}
	protected IDataSet getUserUpdatedDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/userTestingDatasets/userUpdated.xml"));
	}
	
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }
 
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
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
	    IDataSet expectedDataSet = getUserAddedDataSet();
	    ITable expectedTable = expectedDataSet.getTable("USER");
	    assertDoesNotThrow( () -> UserDAO.getInstance().storeUser(user3));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("USER");
	    assertEquals(expectedTable, actualTable);
	}
	
	/**
	 * Tests User queries
	*/
	@Test
    public void testUserQueryReturnsNotNull() {
		User user = UserDAO.getInstance().getUser("usernameTest1");
		assertNotNull(user);
	}
	@Test
    public void testUserQueryReturnsNull() {
		User user = UserDAO.getInstance().getUser("usernameNotExist");
		assertNull(user);
	}
	
	
	/**
	 * Tests user deletion
	 * @throws Exception 
	*/
	@Test
    public void testUserDeletion() throws Exception {
	    IDataSet expectedDataSet = getUserDeletedDataSet();
	    ITable expectedTable = expectedDataSet.getTable("USER");
	    assertDoesNotThrow( () -> UserDAO.getInstance().deleteUser("usernameTest2"));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("USER");
	    assertEquals(expectedTable, actualTable);
    }
	
	/**
	 * Tests user update
	 * @throws Exception 
	*/
	@Test
	public void testUserUpdate() throws Exception {
		User userUpdated = new User("usernameTest1", "passTest1")
				 .setFullName("fullNameUpdated1")
				 .setBirthDate(new Date(1234567890))
				 .setCountry("SPAIN")
				 .setDescription("descriptionTest1");
	    IDataSet expectedDataSet = getUserUpdatedDataSet();
	    ITable expectedTable = expectedDataSet.getTable("USER");
	    assertDoesNotThrow( () ->UserDAO.getInstance().updateUser(userUpdated));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("USER");
	    assertEquals(expectedTable, actualTable);
	}

}
