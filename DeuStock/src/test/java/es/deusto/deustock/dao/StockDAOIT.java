package es.deusto.deustock.dao;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;

//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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


/**
 * Testea los metodos de acceso a datos de stock en la BD
 * 
 * @author landersanmillan
 */
public class StockDAOIT extends DBTestCase{
	
	private final String[] COLUMNS = {"DEUSTOCK_ID", "ACRONYM", "DESCRIPTION", "FULLNAME"};

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

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }
 
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.DELETE_ALL;
    }
    
	private ITable getFilteredTable(String[] columns) throws SQLException, Exception {
	    IDataSet databaseDataSet = getConnection().createDataSet();
	    return DefaultColumnFilter.includedColumnsTable(databaseDataSet.getTable("DEUSTOCK"), columns);
	}
	
   
    @Before
    public void setUp() throws DatabaseUnitException, SQLException, Exception {
    	this.getDatabaseTester().getSetUpOperation().execute(this.getConnection(), this.getDataSet());
    }
    
    @After
    public void tearDown() throws DatabaseUnitException, SQLException, Exception {
    	this.getDatabaseTester().getTearDownOperation().execute(this.getConnection(), this.getDataSet());
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
		    
		StockDAO.getInstance().store(stock3);
		
		ITable filteredActualTable = getFilteredTable(COLUMNS);
	    
	    assertEquals(3, filteredActualTable.getRowCount());
	    assertNotNull(StockDAO.getInstance().get("acronymTest3"));
	}
	
	/**
	 * Tests Stock queries
	*/
	@Test
    public void testStockQueryreturnNotNull() {
		assertNotNull(StockDAO.getInstance().get("acronymTest1"));
	}
	@Test
    public void testStockQueryreturnNull() {
		assertNull(StockDAO.getInstance().get("acronymNotExist"));
	}
	
	/**
	 * Tests Stock deletion
	 * @throws Exception 
	*/
	@Test
    public void testStockDeletion() throws Exception {
		DeuStock stockToDelete1 = StockDAO.getInstance().get("acronymTest1"); 
	    StockDAO.getInstance().delete(stockToDelete1);
	    
	    DeuStock stockToDelete2 = StockDAO.getInstance().get("acronymTest2"); 
	    StockDAO.getInstance().delete(stockToDelete2);
	    
	    ITable filteredActualTable = getFilteredTable(COLUMNS);
	    
		assertEquals(0, filteredActualTable.getRowCount());
	    assertNull(StockDAO.getInstance().get("acronymTest1"));
	    assertNull(StockDAO.getInstance().get("acronymTest2"));
    }
	
	
	/**
	 * Test Stock update
	 * @throws Exception 
	 */
	@Test
	public void testStockUpdate() throws Exception {
		DeuStock stockToUpdate = StockDAO.getInstance().get("acronymTest2");
		stockToUpdate.setFullName("fullNameUpdated2");
	    
		StockDAO.getInstance().update(stockToUpdate);
	    ITable filteredActualTable = getFilteredTable(COLUMNS);
	    
		DeuStock stockUpdated = StockDAO.getInstance().get("acronymTest2");

	    assertEquals(2, filteredActualTable.getRowCount());
	    assertNotNull(stockUpdated);
	    assertEquals(stockUpdated.getFullName(), "fullNameUpdated2");    
	}

}
