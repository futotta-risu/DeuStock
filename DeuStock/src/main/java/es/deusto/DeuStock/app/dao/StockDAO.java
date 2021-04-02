package es.deusto.DeuStock.app.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.DeuStock.app.data.Stock;

/**
 * Clase de acceso a datos de Stocks en la BD.<br>
 * <strong>Patterns:</strong>
 * <ul>
 * <li>DAO</li>
 * <li>Singleton</li>
 * </ul>
 * 
 * @see GenericDAO
 * @author landersanmillan
 */
public class StockDAO extends GenericDAO {

	private static StockDAO INSTANCE;

	private StockDAO() {
	}

	/**
	 * Se obtiene la unica instancia de la clase StockDAO
	 * 
	 * @return <strong>StockDAO</strong> -> Instancia de la clase StockDAO
	 */
	public static StockDAO getInstance() {
		if (INSTANCE == null)
			INSTANCE = new StockDAO();
		return INSTANCE;
	}

	/**
	 * Permite almacenar un stock en la BD
	 * 
	 * @param stock -> Objeto stock que se quiere almacenar en la BD
	 */
	public void storeStock(Stock stock) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		Transaction tx = pm.currentTransaction();

		try {
			tx.begin();
			pm.makePersistent(stock);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Permite obtener una lista con todos los Stocks que se encuentran en la BD
	 * 
	 * @return <strong> List[Stock]</strong> -> Lista que contiene todos los Stocks
	 *         almacenados en la BD
	 */
	public List<Stock> getStocks() {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);

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
			if (tx != null && tx.isActive())
				tx.rollback();
			pm.close();
		}
		return stocks;

	}

	/**
	 * Permite obtener un Stock de la BD a partir de su acronimo
	 * 
	 * @param acronym -> Acronimo del stock
	 * @return <strong> Stock </strong> Objeto Stock solicitado
	 */
	public Stock getStock(String acronym) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		Stock stock = null;
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a Stock: " + acronym);

			tx.begin();
			Query<?> query = pm
					.newQuery("SELECT FROM " + Stock.class.getName() + " WHERE acronym == '" + acronym + "'");
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
	
	
	/**
	 * Permite eliminar un stock de la BD a partir de su acronimo
	 * 
	 * @param acronym -> Acronimo del Stock que se quiere eliminar
	 */
	public void deleteStock(String acronym) {
		PersistenceManager pm = getPMF().getPersistenceManager();
		pm.getFetchPlan().setMaxFetchDepth(-1);
		Transaction tx = pm.currentTransaction();
		pm.setDetachAllOnCommit(true);
		try {
			System.out.println("   * Querying a Stock: " + acronym);

			tx.begin();
			Query<?> query = pm
					.newQuery("SELECT FROM " + Stock.class.getName() + " WHERE acronym == '" + acronym + "'");
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
