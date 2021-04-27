package es.deusto.deustock.dao;

//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.sql.SQLException;

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
 * @author landersanmillan
 */
public class DBManagerIT extends DBTestCase{

	private final String[] COLUMNS_USER = {"USER_ID", "COUNTRY", "DESCRIPTION", "FULLNAME", "PASSWORD", "USERNAME"};
	public DBManagerIT(String name) {
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
	
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }
 
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }
    
    private ITable getFilteredTable(String table, String[] columns) throws SQLException, Exception {
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    return DefaultColumnFilter.includedColumnsTable(databaseDataSet.getTable(table), columns);
	}
    
    
    @Before
    public void setUp() throws DatabaseUnitException, SQLException, Exception {
    	this.getDatabaseTester().getSetUpOperation().execute(this.getConnection(), this.getDataSet());
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, Exception {
    	this.getDatabaseTester().getTearDownOperation().execute(this.getConnection(), this.getDataSet());
    }
    
   
    @Test
    public void testGetObjectsReturnsNotNull() {
    	assertNotNull(DBManager.getInstance().getObjects(Object.class));
    	assertEquals(2, DBManager.getInstance().getObjects(User.class).size());  	
    }
   
    @Test
    public void testUpdateObject() throws Exception {
    	Object objectToUpdate = UserDAO.getInstance().getUser("usernameTest2");
		((User) objectToUpdate).setFullName("fullNameUpdated2");
		
	    DBManager.getInstance().updateObject(objectToUpdate);
	    ITable filteredActualTable = getFilteredTable("DEUSTOCK", COLUMNS_USER);

    	Object userUpdated = UserDAO.getInstance().getUser("usernameTest2");
		
	    assertEquals(2, filteredActualTable.getRowCount());
	    assertNotNull(userUpdated);
	    assertEquals(((User) userUpdated).getFullName(), "fullNameUpdated2");
    }
	
    
//	  @Test
//	  public void testGetObjectReturnsNotNull() {
//	  	assertNotNull(DBManager.getInstance().getObject(User.class, "USERNAME == 'usernameTest1'"));
//	  }
//	  
//	  @Test
//	  public void testGetObjectReturnsNull() {
//	  	assertNull(DBManager.getInstance().getObject(User.class, "USERNAME == 'usernameNotExist'"));
//	  }    
//    @Test
//    public void testUpdateObjectThatDoesNotExist() throws Exception {
//		Object userNotExist = new User("usernameNotExist", "passTest1")
//				 .setFullName("fullNameUpdated1")
//				 .setBirthDate(new Date(1234567890))
//				 .setCountry("SPAIN")
//				 .setDescription("descriptionTest1");
//	    IDataSet expectedDataSet = getDataSet();
//	    ITable expectedTable = expectedDataSet.getTable("USER");
//	    //assertThrows(Exception.class, () -> DBManager.getInstance().updateObject(userNotExist));
//	    DBManager.getInstance().updateObject(userNotExist);
//	    IDataSet databaseDataSet = getConnection().createDataSet();
//	    ITable actualTable = databaseDataSet.getTable("USER");
//	    assertEquals(expectedTable, actualTable);
//    }
//    
//    @Test
//    public void testDeleteObjectThatDoesNotExist() throws Exception {
//	    IDataSet expectedDataSet = getDataSet();
//	    ITable expectedTable = expectedDataSet.getTable("USER");
//	    //assertThrows(Exception.class, () -> DBManager.getInstance().deleteObject(User.class, "USERNAME = usernameNotExist"));
//	    DBManager.getInstance().deleteObject(User.class, "USERNAME = usernameNotExist");
//	    IDataSet databaseDataSet = getConnection().createDataSet();
//	    ITable actualTable = databaseDataSet.getTable("USER");
//	    assertEquals(expectedTable, actualTable);
//    }
//	
	
}
