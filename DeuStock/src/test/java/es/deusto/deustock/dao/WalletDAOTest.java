package es.deusto.deustock.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import es.deusto.deustock.data.stocks.Wallet;

import java.sql.SQLException;


/**
 * @author landersanmillan
 */
class WalletDAOTest {
    private IDBManager dbManager;
    private WalletDAO walletDAO;

    @BeforeEach
    void setUp(){
        dbManager = mock(DBManager.class);
        walletDAO = WalletDAO.getInstance();
        walletDAO.setDBManager(dbManager);
    }
    

    @Test
    @DisplayName("Test get Wallet on non existent wallet")
    void testGetOnNonExistentWallet() throws SQLException {
        when(dbManager.getByID(eq(Wallet.class), anyString())).thenReturn(null);

        final Wallet result = walletDAO.getWallet("Test");

        assertNull(result);
    }
    
    @Test
    @DisplayName("Test store function does not throw error")
    void testStoreWallet() throws SQLException {
        Wallet wallet = new Wallet();
        
        doNothing().when(dbManager).store(any());

        assertDoesNotThrow( () -> walletDAO.store(wallet));
    }

    
    @Test
    @DisplayName("Test update function does not throw error")
    void testUpdateWallet() throws SQLException {
        Wallet wallet = new Wallet();
        wallet.changeMoney(999999);
        doNothing().when(dbManager).update(any());

        assertDoesNotThrow( () -> walletDAO.update(wallet));
    }
    
//  @Test
//  @DisplayName("Test get Wallet on existent wallet")
//  public void testGetOnExistentWallet(){
//      Wallet wallet = new Wallet();
//      wallet.setMoney(800);
//      when(dbManager.getObject(eq(Wallet.class), anyString())).thenReturn(wallet);
//
//      final Wallet result = walletDAO.getWallet("Test");
//
//      assertNotNull(result);
//  }
}
