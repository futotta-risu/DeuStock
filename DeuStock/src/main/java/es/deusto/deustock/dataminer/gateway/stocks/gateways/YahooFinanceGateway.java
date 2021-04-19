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
 * Yahoo Finance Gateway.
 * This class encapsulates the functionality needed from the Yahoo Finance library.
 *
 * @author Erik B. Terres
 */
public class YahooFinanceGateway implements StockDataAPIGateway {

    private static final YahooFinanceGateway instance = new YahooFinanceGateway();

    private YahooFinanceGateway(){ }

    public static YahooFinanceGateway getInstance() {
        return instance;
    }

    @Override
    public DeuStock getStockData(StockQueryData queryData) throws StockNotFoundException{
        DeuStock deustock = new DeuStock(queryData);
        try {
            Stock stock = YahooFinance.get(queryData.getAcronym());

            if(stock == null){
                throw new StockNotFoundException(queryData);
            }

            deustock.setPrice(stock.getQuote(true).getPrice());
            if(queryData.isWithHistoric()) deustock.setHistory(stock.getHistory());
        } catch (IOException e) {
            e.printStackTrace();
            DeuLogger.logger.error("Could not get the stock data " + queryData.getAcronym());
        }
        return deustock;
    }

    private static class StockExtract extends Thread{
        StockQueryData stockData;
        DeuStock stock;
        boolean stockFound = false;

        public DeuStock getStock(){
            return this.stock;
        }

        public String getAcronym(){
            return stockData.getAcronym();
        }

        public StockExtract setStockQuery(StockQueryData stockData){
            this.stockData = stockData;
            return this;
        }

        public boolean isStockFound() {
            return stockFound;
        }

        @Override
        public void run() {
            try {
                this.stockData.setWithHistoric(false);

                this.stock = YahooFinanceGateway
                        .getInstance()
                        .getStockData(stockData);
                this.stockFound = true;

            } catch (StockNotFoundException e) {
                DeuLogger.logger.error(e.getMessage());
            }
        }
    }

    @Override
    public HashMap<String, DeuStock> getStocksData(List<StockQueryData> queryData){
        // We use LinkedList so that we can use the O(1) getLast in the loop
        LinkedList<StockExtract> stockThreadList = new LinkedList<>();

        for(StockQueryData data : queryData){
            stockThreadList.add(new StockExtract().setStockQuery(data));
            stockThreadList.getLast().start();
        }

        HashMap<String, DeuStock> stocks = new HashMap<>();

        for(StockExtract stockThread : stockThreadList){
            try {
                stockThread.join();
            } catch (InterruptedException e) {
                DeuLogger.logger.info("The thread was stopped and stock could not be retrieved." );
                continue;
            }

            if(stockThread.isStockFound()){
                stocks.put(stockThread.getAcronym(),stockThread.getStock());
            }else{
                DeuLogger.logger.info("Skipping unknown stock " + stockThread.getAcronym() + "." );
            }

        }
        return stocks;
    }
}
