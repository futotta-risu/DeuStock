package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class YahooFinanceGatewayTest {

    @Test
    void getStockDataWithoutHistoric() throws StockNotFoundException {
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YahooFinance);


        StockQueryData stockQueryData = new StockQueryData(
                "AMZN",
                StockQueryData.Interval.DAILY
        );

        DeuStock stockData = gateway.getStockData(stockQueryData, false);

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
        );
        assertThrows(
                StockNotFoundException.class,
                () ->gateway.getStockData(stockQueryData, false)
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
        );

        DeuStock stockData = gateway.getStockData(stockQueryData, true);

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().size() > 0);
        assertTrue(stockData.getPrice().intValue() > 0);
    }

    @Test
    void getStocksGeneralData() {
        YahooFinanceGateway gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YahooFinance);

        List<StockQueryData> stockDataList = new ArrayList<>();
        stockDataList.add(new StockQueryData("AMZN", StockQueryData.Interval.DAILY));
        stockDataList.add(new StockQueryData("NOK", StockQueryData.Interval.DAILY));

        HashMap<String, DeuStock> stocks = gateway.getStocksGeneralData(stockDataList);

        assertEquals(stocks.size(), 2);
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

        List<StockQueryData> stockDataList = new ArrayList<>();
        stockDataList.add(new StockQueryData("AMZN", StockQueryData.Interval.DAILY));
        stockDataList.add(new StockQueryData("NOK", StockQueryData.Interval.DAILY));
        stockDataList.add(new StockQueryData("TFXD", StockQueryData.Interval.DAILY));

        HashMap<String, DeuStock> stocks = gateway.getStocksGeneralData(stockDataList);

        assertEquals(stocks.size(), 2);
        assertFalse(stocks.containsKey("TFXD"));
    }
}