package es.deusto.deustock.dao;

import java.util.ArrayList;

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
	 * Se llama a la funcion getObject de DBManager pasando la clase y los parametros del query
	 * @param acronym el parametro a añadir en el query
	 * @return se devuelve el objeto Stock
	 */
	public DeuStock getStock(String acronym) {
		String whereCondition = "acronym  == '" + acronym + "'";
		return (DeuStock) getObject(DeuStock.class, whereCondition);
	}
	
	public ArrayList<DeuStock> getStocks(){
		ArrayList<DeuStock> stockList  = new ArrayList<>();
		for (Object stocks : getObjects(DeuStock.class)) {
			stockList.add((DeuStock) stocks);
		}
		return stockList;
	}
	
	public void deleteStock(String acronym) {
		String whereCondition = "acronym  == '" + acronym + "'";
		deleteObject(Stock.class, whereCondition);
	}
	
	public void storeStock(DeuStock stock) {
		storeObject(stock);
	}

	public void updateStock(DeuStock stock) {
		updateObject(stock);
	}

	public boolean hasStock(String acronym){
		DeuStock stock = getStock(acronym);
		return stock != null;
	}

	public DeuStock getOrCreateStock(String acronym){
		if(!hasStock(acronym)){
			storeStock(new DeuStock(acronym));
		}
		return (DeuStock) getStock(acronym);
	}
}

