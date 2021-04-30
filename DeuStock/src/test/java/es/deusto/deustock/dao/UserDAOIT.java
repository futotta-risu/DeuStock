package es.deusto.deustock.dao;

//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.Date;

import org.dbunit.DBTestCase;
import org.dbunit.DatabaseUnitException;
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


/**
 * Testea los metodos de acceso a datos de usuario en la BD
 * 
 * @author landersanmillan
 */
public class UserDAOIT extends DBTestCase{
	
	private final String[] COLUMNS = {"USER_ID", "COUNTRY", "DESCRIPTION", "FULLNAME", "PASSWORD", "USERNAME"};
  
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

    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.CLEAN_INSERT;
    }
 
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
    
	private ITable getFilteredTable(String[] columns) throws Exception {
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    return DefaultColumnFilter.includedColumnsTable(databaseDataSet.getTable("USER"), columns);
	}
    
   
    @Before
    public void setUp() throws Exception {
    	this.getDatabaseTester().getSetUpOperation().execute(this.getConnection(), this.getDataSet());
    }
    
    @After
    public void tearDown() throws Exception {
    	this.getDatabaseTester().getTearDownOperation().execute(this.getConnection(), this.getDataSet());
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
		
		UserDAO.getInstance().storeUser(user3);
		
		ITable filteredActualTable = getFilteredTable(COLUMNS);
		
	    assertEquals(3, filteredActualTable.getRowCount());
	    assertNotNull(UserDAO.getInstance().getUser("usernameTest3"));
	}
	
	/**
	 * Tests User queries
	*/
	@Test
    public void testUserQueryReturnsNotNull() throws SQLException {
		assertNotNull(UserDAO.getInstance().getUser("usernameTest1"));
	}
	@Test
    public void testUserQueryReturnsNull() throws SQLException {
		assertNull(UserDAO.getInstance().getUser("usernameNotExist"));
	}
	
	/**
	 * Tests user deletion
	 * @throws Exception 
	*/
	@Test
    public void testUserDeletion() throws Exception {	
		User userToDelete1 = UserDAO.getInstance().getUser("usernameTest1");
		UserDAO.getInstance().deleteUser(userToDelete1);
		
		User userToDelete2 = UserDAO.getInstance().getUser("usernameTest2");
		UserDAO.getInstance().deleteUser(userToDelete2);
		
	    ITable filteredActualTable = getFilteredTable(COLUMNS);

	    assertEquals(0, filteredActualTable.getRowCount());
	    assertNull(UserDAO.getInstance().getUser("usernameTest1"));
	    assertNull(UserDAO.getInstance().getUser("usernameTest2"));
    }
	
	/**
	 * Tests user update
	 * @throws Exception 
	*/
	@Test
	public void testUserUpdate() throws Exception {	
		User userToUpdate = UserDAO.getInstance().getUser("usernameTest1");
		userToUpdate.setFullName("fullNameUpdated1");
		
		UserDAO.getInstance().updateUser(userToUpdate);
	    ITable filteredActualTable = getFilteredTable(COLUMNS);
	    
	    User userUpdated = UserDAO.getInstance().getUser("usernameTest1");
	    
	    assertEquals(2, filteredActualTable.getRowCount());
	    assertNotNull(userUpdated);
	    assertEquals(userUpdated.getFullName(), "fullNameUpdated1");
	}
	


}
