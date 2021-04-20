package es.deusto.deustock.dao;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;

import org.junit.Test;


/**
 * Testea los metodos de acceso a datos de stock en la BD
 * 
 * @author landersanmillan
 */
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
		DeuStock stock = StockDAO.getInstance().getStock("MSFT");
		ArrayList<DeuStock> stocks = StockDAO.getInstance().getStocks();
		System.out.println(stock);
		System.out.println(stocks);
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
