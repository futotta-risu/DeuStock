package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik B. Terres
 */
public class YahooFinanceGatewayPerformanceTest {
	
	@Rule
	public ContiPerfRule i = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(throughput = 10, average = 600, max = 2000, percentile95=1200)
    public void testGetStockListPerformanceSmall(){
        List<StockQueryData> stockList = new ArrayList<>();
        stockList.add(new StockQueryData("BB"));
        stockList.add(new StockQueryData("ETH"));
        stockList.add(new StockQueryData("AMZN"));
        stockList.add(new StockQueryData("GOG"));

        YahooFinanceGateway.getInstance().getStocksData(stockList);
    }
    
    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(throughput = 10, average = 1200, max = 2500, percentile95=2000)
    public void testGetStockListPerformanceSmallAlternative(){
        List<StockQueryData> stockList = new ArrayList<>();
        stockList.add(new StockQueryData("PLTR"));
        stockList.add(new StockQueryData("AAPL"));
        stockList.add(new StockQueryData("NIO"));
        stockList.add(new StockQueryData("CIG"));

        YahooFinanceGateway.getInstance().getStocksData(stockList);
    }

    @Test
    @PerfTest(invocations = 25, threads = 5)
    @Required(throughput = 1, average = 3000, max = 5000, percentile95=3500)
    public void testGetStockListPerformanceBigThread() {
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

        YahooFinanceGateway.getInstance().getStocksData(stockList);
    }

}
