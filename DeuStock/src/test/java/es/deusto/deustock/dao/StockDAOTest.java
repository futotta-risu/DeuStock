package es.deusto.deustock.dao;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;


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
        assertDoesNotThrow( () -> manager.store(stock));
	}
	
	/**
	 * Tests Stock queries
	*/

	@Test
    public void testStockQuery() {
		DeuStock stock = new DeuStock(new StockQueryData("MSFT", StockQueryData.Interval.DAILY));
		StockDAO manager = StockDAO.getInstance();
		assertDoesNotThrow( () -> manager.store(stock));
		DeuStock stockResult = StockDAO.getInstance().get("MSFT");
		assertNotNull(stockResult);
		assertEquals("MSFT", stockResult.getAcronym());
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
		DeuStock stock = StockDAO.getInstance().get("MSFT");
        StockDAO.getInstance().delete(stock);
        System.out.println("Deleted User from DB: MSFT");
    }

}
