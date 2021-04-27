package es.deusto.deustock.dao;

import es.deusto.deustock.data.stocks.Wallet;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Tag("dbmanager")
class WalletDAOIT {

    @Test
    void store() {
        Wallet wallet = new Wallet();
        WalletDAO.getInstance().store(wallet);
    }

    @Test
    void getWallet() {

        Wallet wallet = new Wallet();
        String id = wallet.getId();
        WalletDAO.getInstance().store(wallet);
        wallet = WalletDAO.getInstance().getWallet(id.toString());
        assertNotNull(wallet);
    }
}