package es.deusto.deustock.resources.investment;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.dataminer.gateway.stocks.gateways.YahooFinanceGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import yahoofinance.histquotes.HistoricalQuote;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author landersanmillan
 */
@Tag("server-resource")
class StockReportResourceTest {

    private YahooFinanceGateway mockStockGateway;
    
    @BeforeEach
    public void setUp(){
    	mockStockGateway = mock(YahooFinanceGateway.class);
    }

    public void setMocksToResource(StockReportResource stockReportResource){
    	stockReportResource.setStockGateway(mockStockGateway);
    }
    
    @Test
    @DisplayName("Test create a simple pdf with chart returns 200")
    void testCreateSimplePdfWithChartReturns200() throws IOException, StockNotFoundException, SQLException {
    	//Given
    	DeuStock stock = new DeuStock("Test");
    	List<HistoricalQuote> quotes = new LinkedList<>();

    	for(int i = 0; i < 5; i++){
            HistoricalQuote quote = new HistoricalQuote();
            Calendar calendar = Calendar.getInstance();
            calendar.set(2020, i, 28);
            quote.setDate(calendar);
            quote.setClose(new BigDecimal(10+i));
            quotes.add(quote);
        }

    	stock.setHistory(quotes);

    	when(mockStockGateway.getStockData(any())).thenReturn(stock);
  	    
    	StockReportResource stockReportResource = new StockReportResource();
        setMocksToResource(stockReportResource);
        Response response;

        response = stockReportResource.createSimplePdfWithChart("BB", "DAILY");


        //Then
		assertEquals(200, response.getStatus());
    }
    

    @Test
    @DisplayName("Test create a simple pdf with chart returns Exception")
    void testCreateSimplePdfWithChartReturnsException() throws StockNotFoundException{
    	//Given
    	StockNotFoundException ex = new StockNotFoundException(new StockQueryData("BB"));

    	//When
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
    
    @Test
    @DisplayName("Test create report returns 401 on non existent stock")
    void testCreateReportReturns401OnNonExistentStock() throws IOException, StockNotFoundException, SQLException {
        //Given
        when(mockStockGateway.getStockData(any())).thenReturn(null);

        StockReportResource stockReportResource = new StockReportResource();
        setMocksToResource(stockReportResource);

  	    //When
        Response response = stockReportResource.createSimplePdfWithChart("BB", "DAILY");

        //Then
	    assertEquals(401, response.getStatus());
    }

}
