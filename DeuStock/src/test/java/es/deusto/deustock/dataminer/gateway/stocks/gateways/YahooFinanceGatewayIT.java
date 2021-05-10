package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("investment")
class YahooFinanceGatewayIT {

    private YahooFinanceGateway gateway;

    @BeforeEach
    public void setUp(){
        this.gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YAHOO_FINANCE);
    }

    @Test
    void getStockDataWithoutHistoric() throws StockNotFoundException {

        StockQueryData stockQueryData = new StockQueryData(
                "AMZN",
                StockQueryData.Interval.DAILY
        ).setWithHistoric(false);

        DeuStock stockData = gateway.getStockData(stockQueryData);

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().isEmpty());
        assertTrue(stockData.getPrice() > 0);
    }

    @Test
    void getStockDataWithoutHistoricWeekly() throws StockNotFoundException {

        StockQueryData stockQueryData = new StockQueryData(
                "AMZN",
                StockQueryData.Interval.WEEKLY
        ).setWithHistoric(false);

        DeuStock stockData = gateway.getStockData(stockQueryData);

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().isEmpty());
        assertTrue(stockData.getPrice() > 0);
    }

    @Test
    void getStockDataWithoutHistoricMonthly() throws StockNotFoundException {

        StockQueryData stockQueryData = new StockQueryData(
                "AMZN",
                StockQueryData.Interval.MONTHLY
        ).setWithHistoric(false);

        DeuStock stockData = gateway.getStockData(stockQueryData);

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().isEmpty());
        assertTrue(stockData.getPrice() > 0);
    }

    @Test
    void getStockDataFailsOnUnknownStock() {

        StockQueryData stockQueryData = new StockQueryData(
                "FXQH",
                StockQueryData.Interval.DAILY
        ).setWithHistoric(false);

        assertThrows(
                StockNotFoundException.class,
                () ->gateway.getStockData(stockQueryData)
        );
    }




    @Test
    void getStockDataWithHistoric() throws StockNotFoundException {
        StockQueryData stockQueryData = new StockQueryData(
                "BB",
                StockQueryData.Interval.DAILY
        ).setWithHistoric(true);

        DeuStock stockData = gateway.getStockData(stockQueryData);

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().size() > 0);
        assertTrue(stockData.getPrice() > 0);
    }

    @Test
    void getStocksGeneralData() {
        List<String> stockDataList = new ArrayList<>();
        stockDataList.add("AMZN");
        stockDataList.add("NOK");

        HashMap<String, DeuStock> stocks = gateway.getStocksData(stockDataList);

        assertEquals(2, stocks.size());
        assertTrue(stocks.containsKey("AMZN"));
        assertTrue(stocks.containsKey("NOK"));
        assertNotNull(stocks.get("AMZN"));
        assertNotNull(stocks.get("NOK"));
    }

    @Test
    void getStocksGeneralDataWithNonExistentStock() {
        List<String> stockDataList = new ArrayList<>();
        stockDataList.add("AMZN");
        stockDataList.add("NOK");
        stockDataList.add("TFXD");

        HashMap<String, DeuStock> stocks = gateway.getStocksData(stockDataList);

        assertEquals(2 , stocks.size());
        assertFalse(stocks.containsKey("TFXD"));
    }

}