package es.deusto.deustock.dataminer.gateway.stocks;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;

import java.util.HashMap;
import java.util.List;

/**
 * Social Network gateway interface for basic functionality.
 *
 * @author Erik B. Terres
 */
public interface StockDataAPIGateway {

	/**
	 * Gets the Stock information from the API, converts it to a DeuStock class and returns it.
	 *
	 * @param queryData Search Query Data
	 * @return Stock queried
	 *
	 * @throws StockNotFoundException If the acronym was not found
	 */
	DeuStock getStockData(StockQueryData queryData) throws StockNotFoundException;

	/**
	 * Gets the Stocks information from the API.
	 *
	 * The stocks not found will be skipped
	 *
	 * @param queryData Search Query Data
	 * @return Stock list queried
	 */
	HashMap<String, DeuStock> getStocksData(List<StockQueryData> queryData);
}
