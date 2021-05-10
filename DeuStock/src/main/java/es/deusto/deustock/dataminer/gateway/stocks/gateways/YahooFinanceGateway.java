package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

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

    private static final Logger logger = LoggerFactory.getLogger(YahooFinanceGateway.class);


    private YahooFinanceGateway(){ }

    private Interval adaptInterval(StockQueryData.Interval interval){
        return switch (interval){
            case DAILY -> Interval.DAILY;
            case WEEKLY -> Interval.WEEKLY;
            case MONTHLY -> Interval.MONTHLY;
        };
    }

    public static YahooFinanceGateway getInstance() {
        return instance;
    }

    @Override
    public DeuStock getStockData(StockQueryData queryData) throws StockNotFoundException{
        DeuStock deustock = new DeuStock(queryData);
        try {

            Stock stock;
            if(queryData.isWithHistoric()){
                stock = YahooFinance.get(
                        queryData.getAcronym(),
                        queryData.getFrom(),
                        queryData.getTo(),
                        adaptInterval(queryData.getInterval())
                );
            }else{
                stock = YahooFinance.get(
                        queryData.getAcronym()
                );
            }

            if(stock == null){
                throw new StockNotFoundException(queryData);
            }

            deustock.setPrice(stock.getQuote().getPrice().doubleValue());
            if(queryData.isWithHistoric()){
                deustock.setHistory(stock.getHistory());
            }
        } catch (IOException e) {
            logger.error("Could not get the stock data {}.", queryData.getAcronym());
        }
        return deustock;
    }

    @Override
    public HashMap<String, DeuStock> getStocksData(List<String> stockNames)  {
        HashMap<String, DeuStock> stocksAdapted = new HashMap<>();
        try {
            Map<String, Stock> stocks =  YahooFinance
                    .get(stockNames.toArray(String[]::new), false );

            for( Stock stock : stocks.values()){
                DeuStock deustock = new DeuStock(stock.getSymbol());
                deustock.setPrice(stock.getQuote().getPrice().doubleValue());
                stocksAdapted.put(deustock.getAcronym(), deustock);
            }

        } catch (IOException e) {
            return new HashMap<>();
        }

        return stocksAdapted;
    }
}
