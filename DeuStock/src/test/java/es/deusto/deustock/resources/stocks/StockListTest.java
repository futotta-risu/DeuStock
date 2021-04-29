package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import yahoofinance.Stock;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Erik B. Terres
 */
public class StockListTest {


    private HashMap<String, DeuStock> stocks;
    private DeuStock stockBB, stockAMZN;

    @BeforeEach
    void setUp(){
        stockBB = new DeuStock("BB").setPrice(10);
        stockAMZN = new DeuStock("AMZN").setPrice(789);

        stocks = new HashMap<>();

        stocks.put("BB", stockBB);
        stocks.put("AMZN", stockAMZN);
    }

    @Test
    @DisplayName("Test get stock list returns status 200")
    public void testGetStockListReturns200(){
        // Given
        YahooFinanceGateway stockGateway = mock(YahooFinanceGateway.class);
        StockDataGatewayFactory factory = mock(StockDataGatewayFactory.class);
        when(stockGateway.getStocksData(anyList())).thenReturn(stocks);
        when(factory.create(StockDataGatewayEnum.YahooFinance)).thenReturn(stockGateway);

        StockList stockList = new StockList();
        stockList.setStockDataGatewayFactory(factory);


        // When
        Response response = stockList.getStock("small");

        // Then
        assertEquals(200, response.getStatus());
    }


    @Test
    @DisplayName("Test get stock returns stock list")
    public void testGetStockReturnsStockList(){
        // Given
        YahooFinanceGateway stockGateway = mock(YahooFinanceGateway.class);
        StockDataGatewayFactory factory = mock(StockDataGatewayFactory.class);
        when(stockGateway.getStocksData(anyList())).thenReturn(stocks);
        when(factory.create(StockDataGatewayEnum.YahooFinance)).thenReturn(stockGateway);

        StockList stockListResource = new StockList();
        stockListResource.setStockDataGatewayFactory(factory);

        // When
        Response response = stockListResource.getStock("big");

        ArrayList<DeuStock> stockList = (ArrayList<DeuStock>) response.getEntity();

        // Then
        assertEquals(2, stockList.size());
        assertTrue(stockList.stream().anyMatch(item -> item.equals(stockBB)));
        assertTrue(stockList.stream().anyMatch(item -> item.equals(stockAMZN)));
    }

    @Test
    @DisplayName("Test get stock returns empty list on unknown stock list")
    public void testGetStockReturnsEmptyListOnUnknownStockList(){
        // Given
        YahooFinanceGateway stockGateway = mock(YahooFinanceGateway.class);
        StockDataGatewayFactory factory = mock(StockDataGatewayFactory.class);
        when(stockGateway.getStocksData(anyList())).thenReturn(stocks);
        when(factory.create(StockDataGatewayEnum.YahooFinance)).thenReturn(stockGateway);

        StockList stockListResource = new StockList();
        stockListResource.setStockDataGatewayFactory(factory);

        // When
        Response response = stockListResource.getStock("Unknown");

        // Then
        assertEquals(401, response.getStatus());
    }
}
