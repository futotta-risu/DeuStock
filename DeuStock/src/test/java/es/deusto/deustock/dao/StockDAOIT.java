package es.deusto.deustock.dao;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.FileInputStream;


import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Test;


/**
 * Testea los metodos de acceso a datos de stock en la BD
 * 
 * @author landersanmillan
 */
public class StockDAOIT extends DBTestCase{

	public StockDAOIT(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://127.0.0.1:3306/deustockdb?verifyServerCertificate=false&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "deustock_user1");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "12345");
    }

	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/stockTestingDatasets/deustock.xml"));
	}
	protected IDataSet getStockAddedDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/stockTestingDatasets/stockAdded.xml"));
	}
	protected IDataSet getStockDeletedDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/stockTestingDatasets/stockDeleted.xml"));
	}
	protected IDataSet getStockUpdatedDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("db/stockTestingDatasets/stockUpdated.xml"));
	}
	
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }
 
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
	
    /**
	 * Tests Stock creation
     * @throws Exception 
	*/
	@Test
    public void testStockCreation() throws Exception {

		DeuStock stock3 = new DeuStock(new StockQueryData("acronymTest3", StockQueryData.Interval.DAILY))
							.setDescription("descriptionTest3")
							.setFullName("fullNameTest3");

	    IDataSet expectedDataSet = getStockAddedDataSet();
	    ITable expectedTable = expectedDataSet.getTable("DEUSTOCK");
	    assertDoesNotThrow( () -> StockDAO.getInstance().store(stock3));
	    
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("DEUSTOCK");
	    assertEquals(expectedTable, actualTable);
	}
	
	/**
	 * Tests Stock queries
	*/
	@Test
    public void testStockQueryreturnNotNull() {
		DeuStock stockResult = StockDAO.getInstance().get("acronymTest1");
		assertNotNull(stockResult);
	}
	@Test
    public void testStockQueryreturnNull() {
		DeuStock stockResult = StockDAO.getInstance().get("acronymNotExist");
		assertNull(stockResult);
	}
	
	/**
	 * Tests Stock deletion
	 * @throws Exception 
	*/
	@Test
    public void testStockDeletion() throws Exception {
	    IDataSet expectedDataSet = getStockDeletedDataSet();
	    ITable expectedTable = expectedDataSet.getTable("DEUSTOCK");
	    DeuStock stock = StockDAO.getInstance().get("acronymTest2");
	    assertDoesNotThrow( () -> StockDAO.getInstance().delete(stock));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("DEUSTOCK");
	    assertEquals(expectedTable, actualTable);
    }
	
	
	/**
	 * Test Stock update
	 * @throws Exception 
	 */
	@Test
	public void testStockUpdate() throws Exception {
		DeuStock stockUpdated = new DeuStock(new StockQueryData("acronymTest2", StockQueryData.Interval.DAILY))
				.setDescription("descriptionTest2")
				.setFullName("fullNameUpdated2");
	    IDataSet expectedDataSet = getStockUpdatedDataSet();
	    ITable expectedTable = expectedDataSet.getTable("DEUSTOCK");
	    assertDoesNotThrow( () ->StockDAO.getInstance().update(stockUpdated));
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    ITable actualTable = databaseDataSet.getTable("DEUSTOCK");
	    assertEquals(expectedTable, actualTable);
	}

	


}
