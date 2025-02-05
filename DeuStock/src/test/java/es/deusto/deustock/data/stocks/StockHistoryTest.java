package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.DeuStock;
import org.junit.jupiter.api.Test;

import static es.deusto.deustock.services.investment.operation.type.OperationType.LONG;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
class StockHistoryTest {

    @Test
    void testConstructorDoesNotThrowException(){
        DeuStock stock = new DeuStock("AMZN");
        Wallet wallet = new Wallet();
        assertDoesNotThrow(
                () -> new StockHistory(wallet, stock,100, 100, LONG)
        );
    }

    @Test
    void testConstructor(){
        DeuStock stock = new DeuStock("AMZN");
        Wallet wallet = new Wallet();
        StockHistory stockH = new StockHistory(wallet, stock,100,  100, LONG);

        assertEquals("StockHistory{stock=AMZN, price=100.0, operation=LONG}", stockH.toString());
    }

}