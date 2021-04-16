package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.DeuStock;
import org.junit.Test;
import yahoofinance.Stock;

import static org.junit.jupiter.api.Assertions.*;

public class StockHistoryTest {

    @Test
    public void testConstructorDoesNotThrowException(){
        DeuStock stock = new DeuStock("AMZN");

        assertDoesNotThrow(
                () -> new StockHistory( stock, 100, OperationType.LONG)
        );
    }

    @Test
    public void testConstructor(){
        DeuStock stock = new DeuStock("AMZN");
        StockHistory stockH = new StockHistory( stock, 100, OperationType.LONG);
        System.out.println(stockH.toString());
        assertEquals(stockH.toString(), "StockHistory{stock=AMZN, price=100.0, operation=LONG}");
    }

}