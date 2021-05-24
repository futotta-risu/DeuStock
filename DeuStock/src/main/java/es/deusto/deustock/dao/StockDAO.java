package es.deusto.deustock.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import es.deusto.deustock.data.DeuStock;
import yahoofinance.Stock;

/**
 * Clase de acceso a datos de Stocks en la BD.<br>
 * <strong>Patterns:</strong>
 * <ul>
 * <li>DAO</li>
 * <li>Singleton</li>
 * </ul>
 *
 * @author landersanmillan
 */
public class StockDAO implements IDAO<DeuStock>{

	private static StockDAO instance = null;
	private IDBManager dbManager;


	private StockDAO() {
		dbManager = DBManager.getInstance();
	}

	public void setDbManager(IDBManager dbManager) {
		this.dbManager = dbManager;
	}

	/**
	 * Se obtiene la unica instancia de la clase StockDAO
	 * 
	 * @return <strong>StockDAO</strong> -> Instancia de la clase StockDAO
	 */
	public static StockDAO getInstance() {
		if (instance == null) {
			instance = new StockDAO();
		}
		return instance;
	}


	public DeuStock getOrCreateStock(String acronym) throws SQLException {
		if(!has(acronym)){
			store(new DeuStock(acronym));
		}
		return get(acronym);
	}

	/**
	 *
	 * @param identity Symbol of the Stock
	 */
	@Override
	public boolean has(Object identity) throws SQLException {
		return get(identity) != null;
	}

	@Override
	public void store(DeuStock object) throws SQLException {
		dbManager.store(object);
	}

	/**
	 * Get a Stock from the persistence manager
	 *
	 * @param identity Symbol of the Stock
	 */
	@Override
	public DeuStock get(Object identity) throws SQLException {
		var whereCondition = "acronym  == :acronym";
		HashMap<String,Object> params = new HashMap<>();
		params.put("acronym", identity);
		return (DeuStock) dbManager.get(DeuStock.class, whereCondition, params);
	}

	@Override
	public Collection<DeuStock> getAll() {
		return dbManager
				.getAll(DeuStock.class).stream()
				.filter(DeuStock.class::isInstance)
				.map(DeuStock.class::cast)
				.collect(Collectors.toList());

	}

	@Override
	public void update(DeuStock object) throws SQLException {
		dbManager.update(object);
	}

	public void deleteBySymbol(String symbol) throws SQLException {
		var whereCondition = "acronym  == :acronym";
		HashMap<String,String> params = new HashMap<>();
		params.put("acronym", symbol);

		dbManager.delete(Stock.class, whereCondition, params);
	}

	@Override
	public void delete(DeuStock stock) {
		dbManager.delete(stock);
	}
}

