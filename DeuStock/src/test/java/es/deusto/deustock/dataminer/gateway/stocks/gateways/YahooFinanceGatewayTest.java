package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("investment")
class YahooFinanceGatewayTest {

    @Test
    void getStockDataWithoutHistoric() throws StockNotFoundException {
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YahooFinance);


        StockQueryData stockQueryData = new StockQueryData(
                "AMZN",
                StockQueryData.Interval.DAILY
        ).setWithHistoric(false);

        DeuStock stockData = gateway.getStockData(stockQueryData);

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().isEmpty());
        assertTrue(stockData.getPrice().intValue() > 0);
    }

    @Test
    void getStockDataFailsOnUnknownStock() {
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YahooFinance);


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
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YahooFinance);


        StockQueryData stockQueryData = new StockQueryData(
                "BB",
                StockQueryData.Interval.DAILY
        ).setWithHistoric(true);

        DeuStock stockData = gateway.getStockData(stockQueryData);

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().size() > 0);
        assertTrue(stockData.getPrice().intValue() > 0);
    }

    @Test
    void getStocksGeneralData() {
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YahooFinance);

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
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YahooFinance);

        List<String> stockDataList = new ArrayList<>();
        stockDataList.add("AMZN");
        stockDataList.add("NOK");
        stockDataList.add("TFXD");

        HashMap<String, DeuStock> stocks = gateway.getStocksData(stockDataList);

        assertEquals(2 , stocks.size());
        assertFalse(stocks.containsKey("TFXD"));
    }
}