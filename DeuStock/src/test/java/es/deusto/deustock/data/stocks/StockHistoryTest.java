package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.DeuStock;
import org.junit.Test;

import static es.deusto.deustock.simulation.investment.operations.OperationType.LONG;
import static org.junit.jupiter.api.Assertions.*;

public class StockHistoryTest {

    @Test
    public void testConstructorDoesNotThrowException(){
        DeuStock stock = new DeuStock("AMZN");
        Wallet wallet = new Wallet();
        assertDoesNotThrow(
                () -> new StockHistory(wallet, stock,100, 100, LONG)
        );
    }

    @Test
    public void testConstructor(){
        DeuStock stock = new DeuStock("AMZN");
        Wallet wallet = new Wallet();
        StockHistory stockH = new StockHistory(wallet, stock,100,  100, LONG);
        System.out.println(stockH.toString());
        assertEquals(stockH.toString(), "StockHistory{stock=AMZN, price=100.0, operation=LONG}");
    }

}