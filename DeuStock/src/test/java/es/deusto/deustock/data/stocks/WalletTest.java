package es.deusto.deustock.data.stocks;

import es.deusto.deustock.data.DeuStock;

import es.deusto.deustock.services.investment.operations.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class WalletTest {

    @Test
    void testConstructor(){
        Wallet wallet = new Wallet();
        assertNotNull(wallet);
    }

    @Test
    @DisplayName("Test getID works")
    void testGetterId() throws NoSuchFieldException, IllegalAccessException {
        //given
        final Wallet wallet = new Wallet();
        final Field field = wallet.getClass().getDeclaredField("id");
        field.setAccessible(true);
        field.set(wallet, "TestID");

        //when
        final String result = wallet.getId();

        //then
        assertEquals( result, "TestID");
    }


    @Test
    void testInitialMoneyIsCorrect(){
        Wallet wallet = new Wallet();
        assertEquals(wallet.getMoney(),5000);
    }

    @Test
    void testInitialWalletIsEmptyOfStocks(){
        Wallet wallet = new Wallet();
        assertTrue(wallet.getHistory().isEmpty());
    }

    @Test
    @DisplayName("Test setMoney works")
    void testSetMoney() throws NoSuchFieldException, IllegalAccessException {
        //given
        final Wallet wallet = new Wallet();

        //when
        wallet.setMoney(55.0);

        //then
        final Field field = wallet.getClass().getDeclaredField("money");
        field.setAccessible(true);

        assertEquals(field.get(wallet), (double) 55);
    }


    @Test
    void testAddMoney() {
        Wallet wallet = new Wallet();
        wallet.changeMoney(100);
        assertEquals(wallet.getMoney(),5100);
    }

    @Test
    void testTakeMoney() {
        Wallet wallet = new Wallet();
        wallet.changeMoney(-230.4);
        assertEquals(wallet.getMoney(),4769.6);
    }

    @Test
    void testWalletThrowsErrorOnTakingMoreAmountThanWalletHas(){
        Wallet wallet = new Wallet();
        assertThrows(Exception.class, () -> wallet.changeMoney(-6000));
    }

    @Test
    void testWalletHasSameMoneyAfterExceptionOnMoneyChange(){
        Wallet wallet = new Wallet();
        try{
            wallet.changeMoney(-6000);
        }catch (Exception e){
            assertEquals(wallet.getMoney(), 5000);
        }
    }

    @Test
    @DisplayName("Test Has Enough Money returns true if it has more money")
    void testHasEnoughMoney(){
        Wallet wallet = new Wallet();
        assertTrue(wallet.hasEnoughMoney(200));
    }
    @Test
    @DisplayName("Test Has Enough Money returns false if it does not have more money")
    void testDoesNotHaveEnoughMoney(){
        Wallet wallet = new Wallet();
        assertFalse(wallet.hasEnoughMoney(20000));
    }

    @Test
    @DisplayName("Test setMoney works")
    void testAddHistory() throws NoSuchFieldException, IllegalAccessException {
        //given
        final Wallet wallet = new Wallet();
        final DeuStock stock = new DeuStock("BB");
        final StockHistory stockHistory = new StockHistory(wallet,  stock, 20, 30, OperationType.LONG);

        final ArrayList<StockHistory> stockHistories = new ArrayList<>();
        stockHistories.add(stockHistory);


        //when
        wallet.addHistory(stockHistory);

        //then
        final Field field = wallet.getClass().getDeclaredField("history");
        field.setAccessible(true);

        assertEquals(field.get(wallet), stockHistories);
    }


}