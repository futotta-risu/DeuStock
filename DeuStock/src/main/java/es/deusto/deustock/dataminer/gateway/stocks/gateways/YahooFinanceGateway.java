package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
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
            DeuLogger.logger.error("Could not get the stock data " + queryData.getAcronym());
        }
        return deustock;
    }

    private static class StockExtract extends Thread{
        StockQueryData stockData;
        DeuStock stock;

        public DeuStock getStock(){
            return this.stock;
        }

        public String getAcronym(){
            return stockData.getAcronym();
        }

        public void setStockQuery(StockQueryData stockData){
            this.stockData = stockData;
        }

        @Override
        public void run() {
            try {
                this.stock = YahooFinanceGateway.getInstance().getStockData(stockData, false);
            } catch (StockNotFoundException e) {
                this.stock = null;
                DeuLogger.logger.error(e.getMessage());
            }
        }
    }

    /**
     * Gets the Stocks price of a List.
     */
    @Override
    public HashMap<String, DeuStock> getStocksData(List<StockQueryData> queryData){
        List<StockExtract> threads = new LinkedList<>();

        for(StockQueryData data : queryData){
            StockExtract thread = new StockExtract();
            thread.setStockQuery(data);
            threads.add(thread);
            thread.start();
        }

        HashMap<String, DeuStock> stocks = new HashMap<>();
        for(StockExtract thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                DeuLogger.logger.info("Something happened with the stock obtention proccess." );
                return stocks;
            }
            DeuStock stock = thread.getStock();
            if(stock==null){
                DeuLogger.logger.info("Skipping unknown stock " + thread.getAcronym() + "." );
            }else{
                stocks.put(stock.getAcronym(),stock);
            }
        }
        return stocks;
    }
}
