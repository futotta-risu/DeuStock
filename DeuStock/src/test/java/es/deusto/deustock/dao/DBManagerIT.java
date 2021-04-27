package es.deusto.deustock.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.data.User;

public class DBManagerIT extends DBTestCase{

	
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
	protected IDataSet getUserUpdatedDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/userTestingDatasets/userUpdated.xml"));
	}
	
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }
 
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
    
    @Test
    public void testGetObjectsReturnsNotNull() {
    	List<Object> objects = DBManager.getInstance().getObjects(Object.class);
    	assertNotNull(objects);
    	assertEquals(2, objects.size());  	
    }
    
    @Test
    public void testGetObjectReturnsNotNull() {
    	Object object = DBManager.getInstance().getObject(User.class, "USERNAME_ID = 1");
    	assertNotNull(object);
    }
    
    @Test
    public void testGetObjectReturnsNull() {
    	assertThrows(Exception.class, () -> DBManager.getInstance().getObject(User.class, "USERNAME_ID = a"));
    }
    
    
    @Test
    public void testUpdateObject() throws Exception {
		Object userUpdated = new User("usernameTest1", "passTest1")
				 .setFullName("fullNameUpdated1")
				 .setBirthDate(new Date(1234567890))
				 .setCountry("SPAIN")
				 .setDescription("descriptionTest1");
	    IDataSet expectedDataSet = getUserUpdatedDataSet();
	    ITable expectedTable = expectedDataSet.getTable("USER");
	    assertDoesNotThrow( () -> DBManager.getInstance().updateObject(userUpdated));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("USER");
	    assertEquals(expectedTable, actualTable);
    }
    
    @Test
    public void testUpdateObjectThatDoesNotExist() throws Exception {
		Object userNotExist = new User("usernameNotExist", "passTest1")
				 .setFullName("fullNameUpdated1")
				 .setBirthDate(new Date(1234567890))
				 .setCountry("SPAIN")
				 .setDescription("descriptionTest1");
	    IDataSet expectedDataSet = getDataSet();
	    ITable expectedTable = expectedDataSet.getTable("USER");
	    assertThrows(Exception.class, () -> DBManager.getInstance().updateObject(userNotExist));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("USER");
	    assertEquals(expectedTable, actualTable);
    }
    
    @Test
    public void testDeleteObjectThatDoesNotExist() throws Exception {
	    IDataSet expectedDataSet = getDataSet();
	    ITable expectedTable = expectedDataSet.getTable("USER");
	    assertThrows(Exception.class, () -> DBManager.getInstance().deleteObject(User.class, "USERNAME = usernameNotExist"));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("USER");
	    assertEquals(expectedTable, actualTable);
    }
	
	

}
