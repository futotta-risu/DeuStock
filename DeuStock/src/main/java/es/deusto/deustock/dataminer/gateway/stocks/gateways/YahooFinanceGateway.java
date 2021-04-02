package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import com.google.protobuf.TextFormat;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataQueryData;

import org.json.simple.parser.ParseException;
import twitter4j.JSONObject;
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
    public DeuStock getStockData(StockDataQueryData queryData, boolean withHistoric){
        DeuStock deustock = new DeuStock(queryData);
        try {
            Stock stock = YahooFinance.get(queryData.getAcronym());

            deustock.setPrice(stock.getQuote(true).getPrice());
            if(withHistoric) deustock.setHistory(stock.getHistory());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deustock;
    }

    @Override
    public HashMap<String, DeuStock> getStocksGeneralData(List<StockDataQueryData> queryData) {
        HashMap<String, DeuStock> stocks = new HashMap<>();
        for(StockDataQueryData stockData : queryData){
            stocks.put(stockData.getAcronym(), getStockData(stockData, false));
        }
        return stocks;
    }
}
