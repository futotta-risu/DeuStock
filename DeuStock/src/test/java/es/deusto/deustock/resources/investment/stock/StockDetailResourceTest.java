package es.deusto.deustock.resources.investment.stock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.resources.investment.stock.StockDetailResource;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.InvalidStockQueryDataException;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;

/**
 * @author landersanmillan
 */
@Tag("server-resource-2")
public class StockDetailResourceTest {
	
	private StockService stockService;
	
	
    @BeforeEach
    void setUp()  {
        stockService = mock(StockService.class);
    }

    
    void setMocksToResource(StockDetailResource stockDetailResource){
    	stockDetailResource.setStockService(stockService);
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank NAME returns 401")
    void testGetStockDetailWithBlankNameReturns401() throws StockException {
    	StockDetailResource stockDetailResource = new StockDetailResource();

    	when(stockService.getStockDetailData(any(),any())).thenThrow(new StockException("Invalid symbol"));
        setMocksToResource(stockDetailResource);

		assertThrows(WebApplicationException.class, () -> stockDetailResource.getStock("  ", "DAILY"));
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank INTERVAL returns 401")
    void testGetStockDetailWithBlankIntervalReturns401() throws StockException {
    	StockDetailResource stockDetailResource = new StockDetailResource();

        when(stockService.getStockDetailData(any(),any())).thenThrow(new StockException("Invalid interval"));
        setMocksToResource(stockDetailResource);

		assertThrows(WebApplicationException.class, () -> stockDetailResource.getStock("BB", "   "));
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank INTERVAL returns 401")
    void testGetStockDetailWithWrongIntervalReturns401() throws StockException {
    	StockDetailResource stockDetailResource = new StockDetailResource();

        when(stockService.getStockDetailData(any(),any())).thenThrow(new StockException("Invalid Interval"));
        setMocksToResource(stockDetailResource);

		assertThrows(WebApplicationException.class, () -> stockDetailResource.getStock("BB", "ThisReturns401"));
    }
    
    @Test
    @DisplayName("Test get stock detail throws exception on unknown exception")
    void testGetStockDetailThrowsExceptionOnUnknownStock() throws StockException {
        //Given
        StockDetailResource stockDetailResource = new StockDetailResource();
        when(stockService.getStockDetailData(any(),any())).thenThrow(new StockException("Unknown Stock"));
        setMocksToResource(stockDetailResource);

        //When

        // Then
        assertThrows(WebApplicationException.class, () -> stockDetailResource.getStock("TestStockName", "DAILY"));
    }

    @Test
    @DisplayName("Test get stock detail returns status 200")
    void testGetStockReturns200() throws  StockException {
    	//Given
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(stockService.getStockDetailData(any(),any())).thenReturn(stock);


        //When
        StockDetailResource stockDetailResource = new StockDetailResource();
        setMocksToResource(stockDetailResource);

        Response response = stockDetailResource.getStock("BB", "DAILY");

        // Then
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test get stock detail returns stock")
    void testGetStockReturnsStock() throws StockException {
        //Given
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(stockService.getStockDetailData(any(), any())).thenReturn(stock);


        //When
        StockDetailResource stockDetailResource = new StockDetailResource();
        setMocksToResource(stockDetailResource);

        Response response = stockDetailResource.getStock("BB", "DAILY");
        DeuStock result = (DeuStock) response.getEntity();

        // Then
        assertNotNull(result);
        assertEquals("BB", result.getAcronym());
        assertEquals(20, result.getPrice());
    }
}
