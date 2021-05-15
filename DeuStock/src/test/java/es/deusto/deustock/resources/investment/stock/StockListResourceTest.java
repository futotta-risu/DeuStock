package es.deusto.deustock.resources.investment.stock;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.services.investment.stock.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockListResourceTest {

    private StockService mockStockService;

    @BeforeEach
    void setUp(){
        mockStockService = mock(StockService.class);
    }

    @Test
    void testGetStockListReturns200() {
        // Given
        DeuStock stock1 = new DeuStock("BB").setPrice(20);
        DeuStock stock2 = new DeuStock("BC").setPrice(20);
        List<DeuStock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);

        when(mockStockService.getStockListData(anyString())).thenReturn(stocks);

        StockListResource resource = new StockListResource();
        resource.setStockService(mockStockService);

        // When
        Response response = resource.getStock("TestString");

        // Then
        assertEquals(200, response.getStatus());

    }

    @Test
    void testGetStockListReturnsStocks() {
        // Given
        DeuStock stock1 = new DeuStock("BB").setPrice(20);
        DeuStock stock2 = new DeuStock("BC").setPrice(20);
        List<DeuStock> stocks = new ArrayList<>();
        stocks.add(stock1);
        stocks.add(stock2);

        when(mockStockService.getStockListData(anyString())).thenReturn(stocks);

        StockListResource resource = new StockListResource();
        resource.setStockService(mockStockService);

        // When
        Response response = resource.getStock("TestString");
        List<DeuStock> stocksResult = (List<DeuStock>) response.getEntity();

        // Then
        assertEquals(2, stocksResult.size());

    }

    @Test
    void testGetStockListThrowsIllegalArgumentExceptionOnUnknownList() {
        // Given

        when(mockStockService.getStockListData(anyString()))
                .thenThrow(new IllegalArgumentException("Exception"));

        StockListResource resource = new StockListResource();
        resource.setStockService(mockStockService);

        // When

        // Then
        assertThrows(
                WebApplicationException.class,
                () -> resource.getStock("TestString")
        );

    }

}