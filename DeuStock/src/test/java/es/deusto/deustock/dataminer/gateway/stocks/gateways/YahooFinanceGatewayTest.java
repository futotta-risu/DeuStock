package es.deusto.deustock.dataminer.gateway.stocks.gateways;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Erik B. Terres
 */
class YahooFinanceGatewayTest {
    private YahooFinanceGateway gateway;
    private Stock stock, stockFake;

    @BeforeEach
    void setUp(){
        this.gateway = (YahooFinanceGateway) StockDataGatewayFactory
                .getInstance()
                .create(StockDataGatewayEnum.YAHOO_FINANCE);

        stock = new Stock("AMZN");
        StockQuote quote = new StockQuote("AMZN");
        quote.setPrice(new BigDecimal(2000));
        stock.setQuote(quote);

        stockFake = new Stock("AMZN1");
        StockQuote quoteFake = new StockQuote("AMZN1");
        quoteFake.setPrice(new BigDecimal(2001));
        stockFake.setQuote(quoteFake);
    }

    @Test
    void getStockDataWithoutHistoric() throws StockNotFoundException {

        // Given
        StockQueryData stockQueryData = new StockQueryData(
                "AMZN", StockQueryData.Interval.DAILY
        ).setWithHistoric(false);

        DeuStock stockData;

        try (MockedStatic<YahooFinance> yahooFinanceMock = mockStatic(YahooFinance.class)) {

            yahooFinanceMock.when(() -> YahooFinance.get(anyString())).thenReturn(stock);
            yahooFinanceMock.when(() -> YahooFinance.get(anyString(),any(),any(), any())).thenReturn(stockFake);

            // When
            stockData = gateway.getStockData(stockQueryData);
        }

        // Then
        assertNotNull(stockData);
        assertEquals("AMZN", stockData.getAcronym());
        assertTrue(stockData.getHistory().isEmpty());
        assertTrue(stockData.getPrice() > 0);
    }

    @Test
    void getStockDataWithoutHistoricWeekly() throws StockNotFoundException {

        StockQueryData stockQueryData = new StockQueryData(
                "AMZN",
                StockQueryData.Interval.WEEKLY
        ).setWithHistoric(false);

        DeuStock stockData;

        try (MockedStatic<YahooFinance> yahooFinanceMock = mockStatic(YahooFinance.class)) {

            yahooFinanceMock.when(() -> YahooFinance.get(anyString())).thenReturn(stock);
            yahooFinanceMock.when(() -> YahooFinance.get(anyString(),any(),any(), any())).thenReturn(stockFake);

            // When
            stockData = gateway.getStockData(stockQueryData);
        }

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

        DeuStock stockData;

        try (MockedStatic<YahooFinance> yahooFinanceMock = mockStatic(YahooFinance.class)) {

            yahooFinanceMock.when(() -> YahooFinance.get(anyString())).thenReturn(stock);
            yahooFinanceMock.when(() -> YahooFinance.get(anyString(),any(),any(), any())).thenReturn(stockFake);

            // When
            stockData = gateway.getStockData(stockQueryData);
        }

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

        try (MockedStatic<YahooFinance> yahooFinanceMock = mockStatic(YahooFinance.class)) {

            yahooFinanceMock.when(() -> YahooFinance.get(anyString())).thenReturn(null);
            yahooFinanceMock.when(() -> YahooFinance.get(anyString(),any(),any(), any())).thenReturn(stockFake);

            // When

            // Then
            assertThrows(
                    StockNotFoundException.class,
                    () ->gateway.getStockData(stockQueryData)
            );
        }

    }




    @Test
    void getStockDataWithHistoric() throws StockNotFoundException {
        // Given
        StockQueryData stockQueryData = new StockQueryData(
                "BB",
                StockQueryData.Interval.DAILY
        ).setWithHistoric(true);

        List<HistoricalQuote> historicalQuotes = new LinkedList<>();
        historicalQuotes.add(new HistoricalQuote());
        historicalQuotes.add(new HistoricalQuote());
        stock.setHistory(historicalQuotes);

        DeuStock stockData;

        try (MockedStatic<YahooFinance> yahooFinanceMock = mockStatic(YahooFinance.class)) {

            yahooFinanceMock.when(() -> YahooFinance.get(anyString())).thenReturn(stockFake);
            yahooFinanceMock.when(() -> YahooFinance.get(anyString(),any(),any(), any())).thenReturn(stock);

            // When
            stockData = gateway.getStockData(stockQueryData);
        }

        assertNotNull(stockData);
        assertTrue(stockData.getHistory().size() > 0);
        assertTrue(stockData.getPrice() > 0);
    }

    @Test
    void getStocksGeneralData() {
        // Given
        List<String> stockDataList = new ArrayList<>();
        stockDataList.add("AMZN");
        stockDataList.add("AMZN1");

        Map<String, Stock> stocks = new HashMap<>();
        stocks.put("AMZN", stock);
        stocks.put("AMZN1", stockFake);

        HashMap<String, DeuStock> result;

        try (MockedStatic<YahooFinance> yahooFinanceMock = mockStatic(YahooFinance.class)) {

            yahooFinanceMock.when(() -> YahooFinance.get(any(String[].class), anyBoolean())).thenReturn(stocks);

            // When
            result = gateway.getStocksData(stockDataList);
        }

        // Then
        assertEquals(2, result.size());
        assertTrue(result.containsKey("AMZN"));
        assertTrue(result.containsKey("AMZN1"));
        assertNotNull(result.get("AMZN"));
        assertNotNull(result.get("AMZN1"));
    }

    @Test
    void getStocksGeneralDataWithNonExistentStock() {
        // Given
        List<String> stockDataList = new ArrayList<>();
        stockDataList.add("AMZN");
        stockDataList.add("NOK");
        stockDataList.add("TFXD");

        Map<String, Stock> stocks = new HashMap<>();
        stocks.put("AMZN", stock);
        stocks.put("NOK", stockFake);

        HashMap<String, DeuStock> result;

        try (MockedStatic<YahooFinance> yahooFinanceMock = mockStatic(YahooFinance.class)) {

            yahooFinanceMock.when(() -> YahooFinance.get(any(String[].class), anyBoolean())).thenReturn(stocks);

            // When
            result = gateway.getStocksData(stockDataList);
        }
        assertEquals(2 , result.size());
        assertFalse(result.containsKey("TFXD"));
    }
}
