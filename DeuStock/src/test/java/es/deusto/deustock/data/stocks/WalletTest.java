package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.simulation.investment.operations.OperationType;
import org.junit.Test;

import java.math.BigDecimal;

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

}