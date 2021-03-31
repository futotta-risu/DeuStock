package es.deusto.DeuStock.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.DeuStock.app.data.Stock;

/**
 * <strong>Pattern</strong>
 * <ul>
 *      <li>DAO</li>
 *      <li>Singleton</li>
 * </ul>
 */
public class StockDAO extends GenericDAO{
	
	   private static StockDAO INSTANCE;
	   private StockDAO() {}
	    
	    public static StockDAO getInstance() {
	        if(INSTANCE == null) INSTANCE = new StockDAO();
	        return INSTANCE;
	    }
	
	
	public static void storeStock(Stock stock) {
		PersistenceManager pm = getPMF().getPersistenceManager();
	    Transaction tx=pm.currentTransaction();

		try{
	        tx.begin();
	        pm.makePersistent(stock);
	        tx.commit();
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    finally{
	        if (tx.isActive()){
	            tx.rollback();
	        }
	        pm.close();
	    }
	}
	
	public static List<Stock> getStocks() {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);

		Transaction tx = pm.currentTransaction();
		List<Stock> stocks = new ArrayList<>();

		try {
			System.out.println("   * Retrieving an Extent for Stock.");

			tx.begin();
			Extent<Stock> extent = pm.getExtent(Stock.class, true);

			for (Stock s : extent) {
				stocks.add((Stock) pm.detachCopy(s));
			}

			tx.commit();
		} catch (Exception ex) {
			System.out.println("   $ Error Getting stocks: " + ex.getMessage());
		} finally {
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}

			pm.close();
		}
		return stocks;
	
	}
	
	public static Stock getStock(String acronym) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		Stock stock = null;
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a Stock: " + acronym);
	
			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + Stock.class.getName() + " WHERE acronym == '" + acronym +"'");
			query.setUnique(true);
			stock = (Stock) pm.detachCopy((Stock) query.execute());
			tx.commit();
	
		} catch (Exception ex) {
			System.out.println("   $ Error Getting Stock: " + ex.getMessage());
		} finally {
	
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
	
			pm.close();
		}
		return stock;
	}
	
//	public static void updateStock(Stock stock) {
//		PersistenceManager pm = getPMF().getPersistenceManager();
//		Transaction tx = pm.currentTransaction();
//	
//		try {
//			tx.begin();
//			Query<?> query = pm.newQuery("javax.jdo.query.SQL", "UPDATE " + Stock.class.getSimpleName() + " SET DESCRIPTION = ? WHERE acronym =  ?" );
//			query.execute(stock.getDescription(), stock.getAcronym());
//			tx.commit();
//		} catch (Exception ex) {
//			System.out.println("   $ Error Updating stock: " + ex.getMessage());
//		} finally {
//			if (tx != null && tx.isActive()) {
//				tx.rollback();
//			}
//	
//			pm.close();
//		}
//	}

	public static void deleteStock(Stock stock) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(3);
		Transaction tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a Stock: " + stock.getAcronym());
	
			tx.begin();
			Query<?> query = pm.newQuery("SELECT FROM " + Stock.class.getName() + " WHERE acronym == '" + stock.getAcronym() +"'");
			query.setUnique(true);
			query.deletePersistentAll();
			tx.commit();
	
		} catch (Exception ex) {
			System.out.println("   $ Error Getting Stock: " + ex.getMessage());
		} finally {
	
			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
	
			pm.close();
		}
	}
	
}
