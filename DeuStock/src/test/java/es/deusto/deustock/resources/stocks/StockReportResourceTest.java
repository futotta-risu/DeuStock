package es.deusto.deustock.resources.stocks;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class StockReportResourceTest {

	private StockDataGatewayFactory mockStockGatewayFactory;
    private YahooFinanceGateway mockStockGateway;
    
    @BeforeEach
    public void setUp(){
    	mockStockGatewayFactory = mock(StockDataGatewayFactory.class);
    	mockStockGateway = mock(YahooFinanceGateway.class);
    }

    public void setMocksToResource(StockReportResource stockReportResource){
    	stockReportResource.setStockGatewayFactory(mockStockGatewayFactory);
    	stockReportResource.setStockGateway(mockStockGateway);
    }
    
    @Test
    @DisplayName("Test create a simple pdf with chart returns 200")
    public void testCreateSimplePdfWithChartReturns200() throws IOException, StockNotFoundException{
    	//Given
    	DeuStock stock = new DeuStock("Test");
    	//When
		when(mockStockGatewayFactory.create(any())).thenReturn(mockStockGateway);
    	when(mockStockGateway.getStockData(any())).thenReturn(stock);
  	    
    	StockReportResource stockReportResource = new StockReportResource();
        setMocksToResource(stockReportResource);
        Response response = stockReportResource.createSimplePdfWithChart("BB", "DAILY");
  	    
        //Then
		assertEquals(200, response.getStatus());
    }
    

    @Test
    @DisplayName("Test create a simple pdf with chart returns Exception")
    public void testCreateSimplePdfWithChartReturnsException() throws StockNotFoundException{
    	//Given
    	StockNotFoundException ex = new StockNotFoundException(new StockQueryData("BB"));

    	//When
		when(mockStockGatewayFactory.create(any())).thenReturn(mockStockGateway);
        when(mockStockGateway.getStockData(any())).thenThrow(ex);
    	StockReportResource stockReportResource = new StockReportResource();
        setMocksToResource(stockReportResource);
        try {
            stockReportResource.createSimplePdfWithChart("BB", "DAILY");
		} catch (Exception e) {
			//Then
			assertEquals(e.getClass(), ex.getClass() );
		}
    }
    
//  @Test
//  @DisplayName("Test create a simple pdf with chart returns 200")
//  public void testCreateSimplePdfWithChartReturns401() throws IOException, StockNotFoundException{
//  	//Given
//
//  	//When
//		when(mockStockGatewayFactory.create(any())).thenReturn(mockStockGateway);
//  	when(mockStockGateway.getStockData(any())).thenReturn(null);
//	    
//  	StockReportResource stockReportResource = new StockReportResource();
//      setMocksToResource(stockReportResource);
//      Response response = stockReportResource.createSimplePdfWithChart("BB", "DAILY");
//	    
//      //Then
//		assertEquals(401, response.getStatus());
//  }

}
