package es.deusto.deustock.simulation.investment;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.simulation.investment.exceptions.OperationException;
import es.deusto.deustock.simulation.investment.operations.LongOperation;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.simulation.investment.operations.OperationType;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Erik B. Terres
 */
class WalletServiceTest {

    @Test
    public void TestEmptyConstructorDoesNotThrow(){
        // Given

        // When

        // Then
        assertDoesNotThrow(WalletService::new);
    }

    @Test
    public void TestEmptyConstructorIsNotNull(){
        // Given

        // When
        WalletService walletService = new WalletService();
        // Then
        assertNotNull(walletService);
    }

    @Test
    void setStockHistoryDAO() throws NoSuchFieldException, IllegalAccessException {
        // Given
        StockHistoryDAO stockHistoryDAO = mock(StockHistoryDAO.class);
        WalletService walletService = new WalletService();

        final Field field = walletService.getClass().getDeclaredField("stockHistoryDAO");
        field.setAccessible(true);

        // When
        walletService.setStockHistoryDAO(stockHistoryDAO);

        // Then
        assertEquals(field.get(walletService), stockHistoryDAO);

    }

    @Test
    void setWalletDAO() throws NoSuchFieldException, IllegalAccessException {
        // Given
        WalletDAO walletDAO = mock(WalletDAO.class);
        WalletService walletService = new WalletService();

        final Field field = walletService.getClass().getDeclaredField("walletDAO");
        field.setAccessible(true);

        // When
        walletService.setWalletDAO(walletDAO);

        // Then
        assertEquals(field.get(walletService), walletDAO);
    }

    @Test
    void setWalletByWalletObject() throws NoSuchFieldException, IllegalAccessException {
        //given
        final WalletService walletService = new WalletService();
        final Wallet wallet = new Wallet();

        //when
        walletService.setWallet(wallet);

        //then
        final Field field = walletService.getClass().getDeclaredField("wallet");
        field.setAccessible(true);

        assertEquals(field.get(walletService), wallet);
    }

    @Test
    void testSetWalletByWalletID() throws NoSuchFieldException, IllegalAccessException {
        //given
        final WalletService walletService = new WalletService();
        WalletDAO walletDAO = mock(WalletDAO.class);
        final Wallet wallet = new Wallet();
        when(walletDAO.getWallet(anyString())).thenReturn(wallet);

        final Field field = walletService.getClass().getDeclaredField("walletDAO");
        field.setAccessible(true);
        field.set(walletService, walletDAO);

        //when
        walletService.setWallet(wallet);

        //then
        final Field walletField = walletService.getClass().getDeclaredField("wallet");
        walletField.setAccessible(true);

        assertEquals(walletField.get(walletService), wallet);
    }

    @Test
    void openOperationWithEnoughMoney() {
        // Given
        StockHistoryDAO stockHistoryDAO = mock(StockHistoryDAO.class);
        WalletDAO walletDAO = mock(WalletDAO.class);
        final Wallet wallet = new Wallet();

        doNothing().when(stockHistoryDAO).store(any(StockHistory.class));
        doNothing().when(walletDAO).update(any(Wallet.class));
        WalletService walletService = new WalletService();
        walletService.setWalletDAO(walletDAO);
        walletService.setStockHistoryDAO(stockHistoryDAO);
        walletService.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(200);

        Operation operation = new LongOperation(stock, 4);

        // When

        // Then
        assertDoesNotThrow(() -> walletService.openOperation(operation));
        assertEquals(4200, wallet.getMoney());
    }

    @Test
    void openOperationWithoutEnoughMoney() {
        // Given
        StockHistoryDAO stockHistoryDAO = mock(StockHistoryDAO.class);
        WalletDAO walletDAO = mock(WalletDAO.class);
        final Wallet wallet = new Wallet();

        doNothing().when(stockHistoryDAO).store(any(StockHistory.class));
        doNothing().when(walletDAO).update(any(Wallet.class));
        WalletService walletService = new WalletService();
        walletService.setWalletDAO(walletDAO);
        walletService.setStockHistoryDAO(stockHistoryDAO);
        walletService.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(2000);

        Operation operation = new LongOperation(stock, 4);

        // When

        // Then
        assertThrows(OperationException.class, () -> walletService.openOperation(operation));
        assertEquals(5000, wallet.getMoney());
    }

    @Test
    void closeOperation() {
        // Given
        StockHistoryDAO stockHistoryDAO = mock(StockHistoryDAO.class);
        WalletDAO walletDAO = mock(WalletDAO.class);
        final Wallet wallet = new Wallet();

        doNothing().when(stockHistoryDAO).update(any(StockHistory.class));
        doNothing().when(walletDAO).update(any(Wallet.class));

        WalletService walletService = new WalletService();
        walletService.setWalletDAO(walletDAO);
        walletService.setStockHistoryDAO(stockHistoryDAO);
        walletService.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(200);

        Operation operation = new LongOperation(stock, 4);
        StockHistory stockHistory = new StockHistory(
                wallet, stock, 150,4, OperationType.LONG
        );

        // When

        // Then
        assertDoesNotThrow(
                () -> walletService.closeOperation(operation, stockHistory)
        );
        assertEquals(5200, wallet.getMoney());
    }

}