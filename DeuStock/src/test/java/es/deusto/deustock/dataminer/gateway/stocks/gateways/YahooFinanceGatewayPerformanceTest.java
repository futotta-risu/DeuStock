package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Erik B. Terres
 */
@Tag("investment")
public class YahooFinanceGatewayPerformanceTest {
    
    @Test
    public void testGetStockListPerformanceSmall(){
        long startTime = System.nanoTime();
        List<StockQueryData> stockList = new ArrayList<>();
        stockList.add(new StockQueryData("BB"));
        stockList.add(new StockQueryData("ETH"));
        stockList.add(new StockQueryData("AMZN"));
        stockList.add(new StockQueryData("GOG"));

        HashMap<String, DeuStock> gateway = YahooFinanceGateway.getInstance().getStocksData(stockList);
        long endTime = System.nanoTime();
        System.out.println("Duration of small performance test " + (endTime - startTime)/1000000 );
    }
    @Test
    public void testGetStockListPerformanceSmallAlternative(){
        long startTime = System.nanoTime();
        List<StockQueryData> stockList = new ArrayList<>();
        stockList.add(new StockQueryData("PLTR"));
        stockList.add(new StockQueryData("AAPL"));
        stockList.add(new StockQueryData("NIO"));
        stockList.add(new StockQueryData("CIG"));

        HashMap<String, DeuStock> gateway = YahooFinanceGateway.getInstance().getStocksData(stockList);
        long endTime = System.nanoTime();
        System.out.println("Duration of smalll alternative performance test " + (endTime - startTime)/1000000 );
    }

    @Test
    public void testGetStockListPerformanceBigThread() {
        long startTime = System.nanoTime();
        List<StockQueryData> stockList = new ArrayList<>();
        stockList.add(new StockQueryData("CIG"));
        stockList.add(new StockQueryData("WFC"));
        stockList.add(new StockQueryData("CLOV"));
        stockList.add(new StockQueryData("PFE"));
        stockList.add(new StockQueryData("PLUG"));
        stockList.add(new StockQueryData("GE"));
        stockList.add(new StockQueryData("AMD"));
        stockList.add(new StockQueryData("AMC"));
        stockList.add(new StockQueryData("VIAC"));
        stockList.add(new StockQueryData("PCG"));
        stockList.add(new StockQueryData("FCEL"));
        stockList.add(new StockQueryData("PINS"));
        stockList.add(new StockQueryData("ITUB"));
        stockList.add(new StockQueryData("PBR"));
        stockList.add(new StockQueryData("IQ"));
        stockList.add(new StockQueryData("CSCO"));
        stockList.add(new StockQueryData("TSLA"));
        stockList.add(new StockQueryData("VALE"));
        stockList.add(new StockQueryData("AAL"));
        stockList.add(new StockQueryData("MSFT"));

        HashMap<String, DeuStock> gateway = YahooFinanceGateway.getInstance().getStocksData(stockList);
        long endTime = System.nanoTime();
        System.out.println("Duration of big performance test " + (endTime - startTime)/1000000 );
    }

}
