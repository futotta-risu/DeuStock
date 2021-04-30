package es.deusto.deustock.resources.stocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

import es.deusto.deustock.data.DeuStock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
public class StockDetailResourceTest {
	
	private StockDataAPIGateway mockGateway;
	
	
    @BeforeEach
    void setUp() throws Exception {
    	mockGateway = mock(StockDataAPIGateway.class);
    }

    
    void setMocksToResource(StockDetail stockDetailResource){
    	stockDetailResource.setStockGateway(mockGateway);
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank NAME returns 401")
    void testGetStockDetailWithBlankNameReturns401(){
    	StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);
        Response response = stockDetailResource.getStock("  ", "DAILY");
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank INTERVAL returns 401")
    void testGetStockDetailWithBlankIntervalReturns401(){
    	StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);
        Response response = stockDetailResource.getStock("BB", "   ");
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank INTERVAL returns 401")
    void testGetStockDetailWithWrongIntervalReturns401(){
    	StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);
        Response response = stockDetailResource.getStock("BB", "ThisReturns401");
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test get stock detail throws exception")
    void testGetStockDetailThrowsExceptionOnUnknownStock() throws StockNotFoundException {
        //Given

        when(mockGateway.getStockData(any())).thenThrow(
                new StockNotFoundException(
                        new StockQueryData("TestStockName")
                )
        );

        StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);

        //When
        Response response = stockDetailResource.getStock("TestStockName", "DAILY");


        // Then
        assertEquals(401, response.getStatus());
    }

    @Test
    @DisplayName("Test get stock detail returns status 200")
    void testGetStockReturns200() throws StockNotFoundException{
    	//Given
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(mockGateway.getStockData(any())).thenReturn(stock);


        //When
        StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);

        Response response = stockDetailResource.getStock("BB", "DAILY");

        // Then
        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test get stock detail returns stock")
    void testGetStockReturnsStock() throws StockNotFoundException{
        //Given
        DeuStock stock = new DeuStock("BB").setPrice(20);

        when(mockGateway.getStockData(any())).thenReturn(stock);


        //When
        StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);

        Response response = stockDetailResource.getStock("BB", "DAILY");
        DeuStock result = (DeuStock) response.getEntity();

        // Then
        assertNotNull(result);
        assertEquals("BB", result.getAcronym());
        assertEquals(20, result.getPrice());
    }
}
