package es.deusto.deustock.dao;


import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData;
import org.junit.Test;

import java.util.List;


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
		;
      	DeuStock stock1 = new DeuStock(new StockDataQueryData("MSFT"));
		DeuStock stock2 = new DeuStock(new StockDataQueryData("GOOG"));
        StockDAO.getInstance().storeStock(stock1);
        StockDAO.getInstance().storeStock(stock2);
	}

	
	/**
	 * Tests Stock queries
	*/
	@SuppressWarnings("unchecked")
	@Test
    public void testStockQuery() {
		DeuStock stock = StockDAO.getInstance().getStock("MSFT");
		List<DeuStock> stocks = StockDAO.getInstance().getStocks();
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
