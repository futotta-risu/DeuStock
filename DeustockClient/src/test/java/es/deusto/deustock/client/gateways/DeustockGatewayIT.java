package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.simulation.investment.operations.OperationType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


/**
 * Integration test for the functionality between the client and the DeuStock Server
 * @author landersanmi
 */
@Tag("integration-resource")
public class DeustockGatewayIT {

    private DeustockGateway gateway = new DeustockGateway();

    @Test
    void testGetStockReturnsNotNull(){
        //When
        Stock stock = gateway.getStock("BB", "DAILY");
        //Then
        assertNotNull(stock);
    }

    @Test
    void testGetStockReturnsNull(){
        //When
        Stock stock = gateway.getStock("BB", "TESTING");
        //Then
        assertNull(stock);
    }

    @Test
    void testGetSearchedStockReturnsNotNull(){
        //When
        Stock stock = gateway.getSearchedStock("BB");
        //Then
        assertNotNull(stock);
    }

    @Test
    void testGetSearchedStockReturnsNull(){
        //When
        Stock stock = gateway.getSearchedStock("NULL_STOCK");
        //Then
        assertNull(stock);
    }

    @Test
    void testGetTwitterSentiment(){
        //When
        double result = gateway.getTwitterSentiment("Happy");
        double result2 = gateway.getTwitterSentiment("Angry");
        //Then
        assertTrue(result>2);
        assertTrue(result2<2);
    }

    @Test
    void testGetStockListReturnsNotNull(){
        //When
        List<Stock> stockList = gateway.getStockList("big");
        //Then
        assertNotNull(stockList);
    }

    @Test
    void testGetStockListReturnsNull(){
        //When
        List<Stock> stockList = gateway.getStockList("NULL_LIST_TYPE");
        //Then
        assertNull(stockList);
    }

    @Test
    void testGetStockReportReturnsNotNull() throws IOException {
        //When
        File file = gateway.getStockReport("BB", "DAILY", "../");
        byte[] file2 = gateway.getStockReport("BB", "DAILY");
        //Then
        assertNotNull(file);
        assertNotNull(file2);
    }

    @Test
    void testResetHoldingsReturnsFalse(){
        //When & Then
        assertFalse(gateway.resetHoldings("NULL_USER"));
    }
    @Test
    void testGetBalanceReturnsNullPointer(){
        //When & Then
        assertThrows(NullPointerException.class, () -> gateway.getBalance("NULL_USER"));
    }

    @Test
    void testOpenOperationReturnsIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> gateway.openOperation(null, null, "sdsdsdsdafsdg", 20));
        assertThrows(IllegalArgumentException.class, ()-> gateway.openOperation(OperationType.LONG, null, "dhasofdjpiasd", -1));
    }

    @Test
    void testOpenOperationWith0Cuantity(){
        assertDoesNotThrow(() -> gateway.openOperation(OperationType.LONG, null, "sdsdsdsdafsdg", 0));
    }

    @Test
    void testOpenOperationReturnsNullPointer(){
        assertThrows(NullPointerException.class, () -> gateway.openOperation(OperationType.LONG, null, "fjdsnfsd", 2));
    }

    @Test
    void testCloseOperation(){
        assertDoesNotThrow( ()-> gateway.closeOperation("fbndif", "nfdjnagrf"));
    }







}
