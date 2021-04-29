package es.deusto.deustock.resources.stocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

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
    private StockDataGatewayFactory mockGatewayFactory;
	
	
    @BeforeEach
    public void setUp() throws Exception {
    	mockGateway = mock(StockDataAPIGateway.class);
    	mockGatewayFactory = mock(StockDataGatewayFactory.class);
    }

    
    public void setMocksToResource(StockDetail stockDetailResource){
    	stockDetailResource.setStockGatewayFactory(mockGatewayFactory);
    	stockDetailResource.setStockGateway(mockGateway);
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank NAME returns 401")
    public void testGetStockDetailWithBlankNameReturns401(){
    	StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);
        Response response = stockDetailResource.getStock("", "DAILY");
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank INTERVAL returns 401")
    public void testGetStockDetailWithBlankIntervalReturns401(){
    	StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);
        Response response = stockDetailResource.getStock("BB", "");
		assertEquals(401, response.getStatus());
    }
    
    @Test
    @DisplayName("Test get stock detail with a blank INTERVAL returns 401")
    public void testGetStockDetailWithWrongIntervalReturns401(){
    	StockDetail stockDetailResource = new StockDetail();
        setMocksToResource(stockDetailResource);
        Response response = stockDetailResource.getStock("BB", "ThisReturns401");
		assertEquals(401, response.getStatus());
    }
    
//    @Test
//    @DisplayName("Test get stock detail throws exception")
//    public void testGetStockDetailThrowsException() throws StockNotFoundException{
//    	//Given
//    	StockNotFoundException ex = new StockNotFoundException(new StockQueryData("BB"));
//		
//        //When
//    	when(mockGatewayFactory.create(any())).thenReturn(mockGateway);
//    	when(mockGateway.getStockData(any())).thenThrow(ex);
//
//    	StockDetail stockDetailResource = new StockDetail();
//        setMocksToResource(stockDetailResource);
//    	
//    	assertThrows(ex.getClass(), () -> stockDetailResource.getStock("BB", "ThisReturns401"));
//    }
}
