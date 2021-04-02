package es.deusto.DeuStock.dao;


import java.util.List;

import org.junit.Test;

import es.deusto.DeuStock.app.dao.StockDAO;
import es.deusto.DeuStock.app.data.Stock;


public class StockDAOTest {

	/**
	 * Tests Stock creation
	*/
	@Test
    public void testStockCreation() {	
      	Stock stock1 = new Stock("MSFT", "Microsoft");
      	Stock stock2 = new Stock("YAHOY", "Yahoo");
        StockDAO.getInstance().storeStock(stock1);
        StockDAO.getInstance().storeStock(stock2);
	}

	
	/**
	 * Tests Stock queries
	*/
	@SuppressWarnings("unchecked")
	@Test
    public void testStockQuery() {
		Stock stock = StockDAO.getInstance().getStock("MSFT");
		List<Stock> stocks = StockDAO.getInstance().getStocks();
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
