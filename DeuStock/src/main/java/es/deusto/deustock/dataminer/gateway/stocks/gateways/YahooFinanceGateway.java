package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData;
import es.deusto.deustock.log.DeuLogger;

import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;
import java.util.*;

/**
 * Twitter gateway.
 *
 * @author Erik B. Terres
 */
public class YahooFinanceGateway implements StockDataAPIGateway {

    private static YahooFinanceGateway instance = null;

    private YahooFinanceGateway(){ }

    public static YahooFinanceGateway getInstance() {
        if(instance == null) instance = new YahooFinanceGateway();
        return instance;
    }

    @Override
    public DeuStock getStockData(StockQueryData queryData, boolean withHistoric) throws StockNotFoundException{
        DeuStock deustock = new DeuStock(queryData);
        try {
            Stock stock = YahooFinance.get(queryData.getAcronym());

            if(stock == null){
                throw new StockNotFoundException(queryData);
            }

            deustock.setPrice(stock.getQuote(true).getPrice());
            if(withHistoric) deustock.setHistory(stock.getHistory());
        } catch (IOException e) {
            e.printStackTrace();
            DeuLogger.logger.error("Could not get the stock data.");
        }
        return deustock;
    }


    /**
     * Gets the Stocks price of a List.
     */
    @Override
    public HashMap<String, DeuStock> getStocksGeneralData(List<StockQueryData> queryData) {
        HashMap<String, DeuStock> stocks = new HashMap<>();

        for(StockQueryData stockData : queryData){
            try {
                DeuStock stock = getStockData(stockData, false);
                stocks.put(stockData.getAcronym(), stock);

            } catch (StockNotFoundException e) {
                DeuLogger.logger.error(e.getMessage());
                DeuLogger.logger.info("Skipping unknown stock " + stockData.getAcronym() + "." );
            }
        }

        return stocks;
    }
}
