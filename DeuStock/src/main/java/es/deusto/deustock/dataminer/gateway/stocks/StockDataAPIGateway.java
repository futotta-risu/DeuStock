package es.deusto.deustock.dataminer.gateway.stocks;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Social Network gateway interface for basic functionality.
 *
 * @author Erik B. Terres
 */
public interface StockDataAPIGateway {
	DeuStock getStockData(StockQueryData queryData, boolean withHistoric) throws StockNotFoundException;
	HashMap<String, DeuStock> getStocksData(List<StockQueryData> queryData);
}
