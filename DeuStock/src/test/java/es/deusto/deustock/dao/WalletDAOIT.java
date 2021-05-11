package es.deusto.deustock.dao;

import es.deusto.deustock.data.stocks.Wallet;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("dbmanager")
class WalletDAOIT {

    @Test
    void store() throws SQLException {
        // Given
        Wallet wallet = new Wallet();
        String id = wallet.getId();

        // When
        WalletDAO.getInstance().store(wallet);

        // Then
        assertDoesNotThrow( () -> WalletDAO.getInstance().getWallet(id));
    }


}