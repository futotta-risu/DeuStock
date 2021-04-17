package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.DeuStock;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class WalletTest {

    @Test
    public void testConstructor(){
        Wallet wallet = new Wallet();
        assertNotNull(wallet);
    }

    @Test
    public void testInitialMoneyIsCorrect(){
        Wallet wallet = new Wallet();
        assertEquals(wallet.getMoney(),5000);
    }

    @Test
    public void testInitialWalletIsEmptyOfStocks(){
        Wallet wallet = new Wallet();
        assertTrue(wallet.getHistory().isEmpty());
    }

    @Test
    public void testAddMoney() throws Exception {
        Wallet wallet = new Wallet();
        wallet.changeMoney(100);
        assertEquals(wallet.getMoney(),5100);
    }

    @Test
    public void testTakeMoney() throws Exception {
        Wallet wallet = new Wallet();
        wallet.changeMoney(-230.4);
        assertEquals(wallet.getMoney(),4769.6);
    }

    @Test
    public void testWalletThrowsErrorOnTakingMoreAmountThanWalletHas(){
        Wallet wallet = new Wallet();
        assertThrows(Exception.class, () -> wallet.changeMoney(-6000));
    }

    @Test
    public void testWalletHasSameMoneyAfterExceptionOnMoneyChange(){
        Wallet wallet = new Wallet();
        try{
            wallet.changeMoney(-6000);
        }catch (Exception e){
            assertEquals(wallet.getMoney(), 5000);
        }
    }

    @Test
    public void testAddStockHistory(){
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("ETH").setPrice(new BigDecimal(20));

        StockHistory stockH = new StockHistory( stock, 20, OperationType.SHORT);

        wallet.addStock(stock, OperationType.SHORT);

        assertArrayEquals(wallet.getHoldings().toArray(),new StockHistory[]{stockH});
    }

    @Test
    public void testCannotAddNullStock(){
        Wallet wallet = new Wallet();

        assertThrows(NullPointerException.class,
                () -> wallet.addStock(null, OperationType.LONG)
        );
    }
    @Test
    public void testCannotAddStockWithNullOperation(){
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("ETH");

        assertThrows(NullPointerException.class,
                () -> wallet.addStock(stock, null)
        );
    }

    @Test
    public void testDeleteStockHistory(){
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("ETH").setPrice(new BigDecimal("20.4"));
        StockHistory stockH = new StockHistory( stock, 20.4, OperationType.SHORT);

        wallet.addStock(stock, OperationType.SHORT);
        wallet.deleteStockHistory(stockH);
        assertArrayEquals(wallet.getHoldings().toArray(),new StockHistory[]{});
    }

    @Test
    public void testCannotDeleteHistoryNotOnHoldings(){
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("ETH").setPrice(new BigDecimal(980));
        DeuStock stockOther = new DeuStock("BB").setPrice(new BigDecimal(60));

        StockHistory stockHistory = new StockHistory( stock, 980, OperationType.LONG);
        StockHistory stockHistoryOther = new StockHistory( stockOther, 60, OperationType.LONG);

        wallet.addStock(stock, OperationType.LONG);

        wallet.deleteStockHistory(stockHistoryOther);
        assertArrayEquals(wallet.getHoldings().toArray(),new StockHistory[]{stockHistory});
    }

    @Test
    public void testDeleteNullImpliesNotDeleting(){
        Wallet wallet = new Wallet();
        DeuStock stock = new DeuStock("ETH").setPrice(new BigDecimal(980));

        StockHistory stockHistory = new StockHistory( stock, 980, OperationType.LONG);

        wallet.addStock(stock, OperationType.LONG);

        wallet.deleteStockHistory(null);
        assertArrayEquals(wallet.getHoldings().toArray(),new StockHistory[]{stockHistory});
    }

}