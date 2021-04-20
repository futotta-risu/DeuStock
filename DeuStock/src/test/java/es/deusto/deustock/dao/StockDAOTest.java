package es.deusto.deustock.dao;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;


/**
 * Testea los metodos de acceso a datos de stock en la BD
 * 
 * @author landersanmillan
 */
@Tag("dbmanager")
public class StockDAOTest {

	/**
	 * Tests Stock creation
	*/
	@Test
    public void testStockCreation() {
		DeuStock stock = new DeuStock(new StockQueryData("MSFT", StockQueryData.Interval.DAILY));
        StockDAO manager = StockDAO.getInstance();
        assertDoesNotThrow( () -> manager.storeObject(stock));
	}
	
	/**
	 * Tests Stock queries
	*/

	@Test
    public void testStockQuery() {
		DeuStock stock = new DeuStock(new StockQueryData("MSFT", StockQueryData.Interval.DAILY));
		StockDAO manager = StockDAO.getInstance();
		assertDoesNotThrow( () -> manager.storeObject(stock));
		DeuStock stockResult = StockDAO.getInstance().getStock("MSFT");
		assertNotNull(stockResult);
	}
	

	
//	/**
//	 * Test Stock update
//	 */
//	@Test
//	public void testStockUpdate() {
//        Stock stockUpdate = StockDAO.getStock("MSFT");
//        stockUpdate.setDescription("DESCRIPTION_UPDATED");
//        StockDAO.getIntsance().updateStock(stockUpdate);
//        System.out.println(StockDAO.getStock("MSFT");
//	}
//
//	
	/**
	 * Tests Stock deletion
	*/
	@Test
    public void testUserDeletion() {
        StockDAO.getInstance().deleteStock("MSFT");
        System.out.println("Deleted User from DB: MSFT");
    }

}
