package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erik B. Terres & Lander San Millan
 */
public class YahooFinanceGatewayPerformanceIT {
	
	@Rule
	public ContiPerfRule i = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(average = 99999, max = 99999, percentile95=99999)
    public void testGetStockListPerformanceSmall(){
        List<String> stockList = new ArrayList<>();
        stockList.add("BB");
        stockList.add("VIAC");
        stockList.add("AMZN");
        stockList.add("TSLA");

        YahooFinanceGateway.getInstance().getStocksData(stockList);
    }
    
    @Test
    @PerfTest(invocations = 50, threads = 5)
    @Required(average = 99999, max = 99999, percentile95=99999)
    public void testGetStockListPerformanceSmallAlternative(){
        List<String> stockList = new ArrayList<>();
        stockList.add("PLTR");
        stockList.add("AAPL");
        stockList.add("NIO");
        stockList.add("CIG");

        YahooFinanceGateway.getInstance().getStocksData(stockList);
    }

    @Test
    @PerfTest(invocations = 25, threads = 5)
    @Required(average = 99999, max = 99999, percentile95=99999)
    public void testGetStockListPerformanceBigThread() {
        List<String> stockList = new ArrayList<>();
        stockList.add("CIG");
        stockList.add("WFC");
        stockList.add("CLOV");
        stockList.add("PFE");
        stockList.add("PLUG");
        stockList.add("GE");
        stockList.add("AMD");
        stockList.add("AMC");
        stockList.add("VIAC");
        stockList.add("PCG");
        stockList.add("FCEL");
        stockList.add("PINS");
        stockList.add("ITUB");
        stockList.add("IQ");
        stockList.add("CSCO");
        stockList.add("TSLA");
        stockList.add("VALE");
        stockList.add("AAL");
        stockList.add("MSFT");

        YahooFinanceGateway.getInstance().getStocksData(stockList);
    }

}
