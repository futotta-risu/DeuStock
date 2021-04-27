package es.deusto.deustock.dao;

import java.util.ArrayList;
import java.util.Collection;
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

	private static StockDAO instance;

	private StockDAO() {
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


	public DeuStock getOrCreateStock(String acronym){
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
	public boolean has(Object identity) {
		return get(identity) != null;
	}

	@Override
	public void store(DeuStock object) {
		DBManager.getInstance().storeObject(object);
	}

	/**
	 * Get a Stock from the persistence manager
	 *
	 * @param identity Symbol of the Stock
	 */
	@Override
	public DeuStock get(Object identity) {
		String whereCondition = "acronym  == '" + identity + "'";
		return (DeuStock) DBManager.getInstance().getObject(DeuStock.class, whereCondition);
	}

	@Override
	public Collection<DeuStock> getAll() {
		return DBManager.getInstance()
				.getObjects(DeuStock.class).stream()
				.filter(DeuStock.class::isInstance)
				.map(DeuStock.class::cast)
				.collect(Collectors.toList());

	}

	@Override
	public void update(DeuStock object) {
		DBManager.getInstance().updateObject(object);
	}

	public void deleteBySymbol(String symbol){
		String whereCondition = "acronym  == '" + symbol + "'";
		DBManager.getInstance().deleteObject(Stock.class, whereCondition);
	}

	@Override
	public void delete(DeuStock object) {
		deleteBySymbol(object.getAcronym());
	}
}

