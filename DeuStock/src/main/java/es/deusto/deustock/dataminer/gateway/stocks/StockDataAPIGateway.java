package es.deusto.deustock.dataminer.gateway.stocks;

import es.deusto.deustock.data.DeuStock;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Social Network gateway interface for basic functionality.
 *
 * @author Erik B. Terres
 */
public interface StockDataAPIGateway {
	DeuStock getStockData(StockDataQueryData queryData, boolean withHistoric) throws IOException;
	HashMap<String, DeuStock> getStocksGeneralData(List<StockDataQueryData> queryData);
}
