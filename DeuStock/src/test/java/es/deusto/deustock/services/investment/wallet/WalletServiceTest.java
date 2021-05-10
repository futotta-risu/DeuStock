package es.deusto.deustock.services.investment.wallet;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import yahoofinance.Stock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Tag("service")
public class WalletServiceTest {

    private UserDAO mockUserDAO;
    private StockHistoryDAO mockStockHistoryDAO;
    private WalletDAO mockWalletDAO;

    @BeforeEach
    void setUp(){
        mockUserDAO = mock(UserDAO.class);
        mockStockHistoryDAO = mock(StockHistoryDAO.class);
        mockWalletDAO = mock(WalletDAO.class);
    }

    @Test
    void testConstructorNotNull(){
        // Given

        // When
        WalletService walletService = new WalletService();

        // Then
        assertNotNull(walletService);
    }

    @Test
    void testResetHoldingsResetsWallet() throws WalletException, SQLException {
        // Given
        User u = new User("TestUser", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);
        wallet.changeMoney(200);
        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet, stock, 21.0, 22.0, OperationType.LONG);

        List<StockHistory> stockHistoryList = new ArrayList<>();
        stockHistoryList.add(stockHistory);

        wallet.addHistory(stockHistory);

        when(mockUserDAO.get(anyString())).thenReturn(u);
        when(mockStockHistoryDAO.getStockHistory(anyString())).thenReturn(stockHistoryList);
        doNothing().when(mockWalletDAO).update(any());
        doNothing().when(mockStockHistoryDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setStockHistoryDAO(mockStockHistoryDAO);
        service.setUserDAO(mockUserDAO);

        // When
        service.resetHoldings("TestUser");

        // Then
        assertEquals(5000, wallet.getMoney());
        assertEquals(1, wallet.getHistory().size());
        assertTrue(wallet.getHistory().get(0).isClosed());
    }

    @Test
    void testResetHoldingsThrowsWalletExceptionOnSQLException() throws SQLException {
        // Given
        User u = new User("TestUser", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);
        wallet.changeMoney(200);
        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet, stock, 21.0, 22.0, OperationType.LONG);

        List<StockHistory> stockHistoryList = new ArrayList<>();
        stockHistoryList.add(stockHistory);

        wallet.addHistory(stockHistory);

        when(mockUserDAO.get(anyString())).thenThrow(new SQLException("Error getting wallet"));
        when(mockStockHistoryDAO.getStockHistory(anyString())).thenReturn(stockHistoryList);
        doNothing().when(mockWalletDAO).update(any());
        doNothing().when(mockStockHistoryDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setStockHistoryDAO(mockStockHistoryDAO);
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertThrows(
                WalletException.class,
                () -> service.resetHoldings("TestUser")
        );
    }

    @Test
    void testGetHoldingsReturnsCorrectList() throws WalletException, SQLException {
        // Given
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);
        wallet.changeMoney(200);
        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet, stock, 21.0, 22.0, OperationType.LONG);
        StockHistory stockHistory2 = new StockHistory(wallet, stock, 56.0, 22.0, OperationType.LONG);
        stockHistory2.setClosed(true);

        wallet.addHistory(stockHistory);
        wallet.addHistory(stockHistory2);

        when(mockUserDAO.get(anyString())).thenReturn(u);

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When
        List<StockHistoryDTO> story = service.getHoldings("TestUser");

        // Then
        assertEquals(1, story.size());
        assertEquals(21.0, story.get(0).getOpenPrice());
    }

    @Test
    void testGetHoldingsThrowsExceptionOnSQLException() throws WalletException, SQLException {
        // Given
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);
        wallet.changeMoney(200);
        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet, stock, 21.0, 22.0, OperationType.LONG);
        StockHistory stockHistory2 = new StockHistory(wallet, stock, 56.0, 22.0, OperationType.LONG);
        stockHistory2.setClosed(true);

        wallet.addHistory(stockHistory);
        wallet.addHistory(stockHistory2);

        when(mockUserDAO.get(anyString())).thenThrow(new SQLException("User not found"));

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertThrows(
                WalletException.class,
                () -> service.getHoldings("TestUser")
        );
    }

    @Test
    void testUpdateMoneyUpdatesMoney() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        when(mockUserDAO.get(anyString())).thenReturn(u);
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When
        service.updateMoney("TestUser", 2000.0);

        // Then
        assertEquals(3000.0, wallet.getMoney());
    }

    @Test
    void testUpdateMoneyThrowsExceptionOnSQLException() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        when(mockUserDAO.get(anyString())).thenThrow(new SQLException("User not found"));
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertThrows(
                WalletException.class,
                () -> service.updateMoney("TestUser", 2000.0)
        );
    }

    @Test
    void testUpdateMoneyThrowsExceptionOnNotEnoughMoney() throws SQLException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        when(mockUserDAO.get(anyString())).thenReturn(u);
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When

        // Then
        assertThrows(
                WalletException.class,
                () -> service.updateMoney("TestUser", 6000.0)
        );
    }

    @Test
    void testUpdateMoneyByWalletIDUpdatesMoney() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        when(mockWalletDAO.getWallet(anyString())).thenReturn(wallet);
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When
        service.updateMoneyByWalletID("TestUser", 2000.0);

        // Then
        assertEquals(3000.0, wallet.getMoney());
    }

    @Test
    void testUpdateMoneyByWalletIDThrowsExceptionOnSQLException() throws SQLException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        when(mockWalletDAO.getWallet(anyString())).thenThrow(new SQLException("Wallet not found"));
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When


        // Then
        assertThrows(
            WalletException.class,
            () -> service.updateMoneyByWalletID("TestUser", 2000.0)
        );
    }

    @Test
    void testUpdateMoneyByWalletIDThrowsExceptionOnNotEnoughMoney() throws SQLException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        when(mockWalletDAO.getWallet(anyString())).thenReturn(wallet);
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);

        // When


        // Then
        assertThrows(
                WalletException.class,
                () -> service.updateMoneyByWalletID("TestUser", 6000.0)
        );
    }

    @Test
    void testAddToHoldingsAddsStockHistory() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet,stock,20.0,20.0,OperationType.LONG);

        when(mockUserDAO.get(anyString())).thenReturn(u);
        when(mockStockHistoryDAO.create(any(),any(),anyDouble(),any())).thenReturn(stockHistory);
        doNothing().when(mockStockHistoryDAO).store(any());
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);
        service.setStockHistoryDAO(mockStockHistoryDAO);

        // When
        service.addToHoldings("TestUsername",stock,20.0, OperationType.LONG);

        // Then
        assertEquals(1, wallet.getHistory().size());
    }

    @Test
    void testAddToHoldingsThrowsOnSQLException() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet,stock,20.0,20.0,OperationType.LONG);

        when(mockUserDAO.get(anyString())).thenReturn(u);
        when(mockStockHistoryDAO.create(any(),any(),anyDouble(),any())).thenReturn(stockHistory);
        doThrow(new SQLException("Can't store")).when(mockStockHistoryDAO).store(any());
        doNothing().when(mockWalletDAO).update(any());

        WalletService service = new WalletService();
        service.setWalletDAO(mockWalletDAO);
        service.setUserDAO(mockUserDAO);
        service.setStockHistoryDAO(mockStockHistoryDAO);

        // When

        // Then
        assertThrows(
                WalletException.class,
                () -> service.addToHoldings("TestUsername",stock,20.0, OperationType.LONG)
        );
    }

    @Test
    void testGetStockHistoryReturnsStockHistory() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet,stock,20.0,20.0,OperationType.LONG);

        doReturn(stockHistory).when(mockStockHistoryDAO).get(any());

        WalletService service = new WalletService();
        service.setStockHistoryDAO(mockStockHistoryDAO);

        // When
        StockHistory result = service.getStockHistory("TestUsername");

        // Then
        assertEquals(stockHistory, result);
    }

    @Test
    void testGetStockHistoryThrowsWalletExceptionOnSQLException() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet,stock,20.0,20.0,OperationType.LONG);

        doThrow(new SQLException("No StockHistory")).when(mockStockHistoryDAO).get(any());

        WalletService service = new WalletService();
        service.setStockHistoryDAO(mockStockHistoryDAO);

        // When

        // Then
        assertThrows(
                WalletException.class,
                () -> service.getStockHistory("TestUsername")
        );
    }

    @Test
    void testCloseHistoryClosesHistory() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet,stock,20.0,20.0,OperationType.LONG);

        doReturn(stockHistory).when(mockStockHistoryDAO).get(any());
        doNothing().when(mockStockHistoryDAO).update(any());

        WalletService service = new WalletService();
        service.setStockHistoryDAO(mockStockHistoryDAO);

        // When
        service.closeStockHistory("TestUsername");

        // Then
        assertTrue(stockHistory.isClosed());
    }

    @Test
    void testCloseHistoryThrowsWalletExceptionOnSQLException() throws SQLException, WalletException {
        User u = new User("TestUsername", "TestPass");
        Wallet wallet = new Wallet();
        u.setWallet(wallet);

        DeuStock stock = new DeuStock("BB").setPrice(20.0);
        StockHistory stockHistory = new StockHistory(wallet,stock,20.0,20.0,OperationType.LONG);

        doThrow(new SQLException("Exception")).when(mockStockHistoryDAO).get(any());
        doNothing().when(mockStockHistoryDAO).update(any());

        WalletService service = new WalletService();
        service.setStockHistoryDAO(mockStockHistoryDAO);

        // When

        // Then
        assertThrows(
                WalletException.class,
                () -> service.closeStockHistory("TestUsername")
        );
    }

}
