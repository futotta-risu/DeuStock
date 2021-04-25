package es.deusto.deustock.dao;

import es.deusto.deustock.data.stocks.Wallet;

/**
 * @author Erik B. Terres
 */
public class WalletDAO {

    private static WalletDAO instance;

    private WalletDAO(){
        super();
    }

    public static WalletDAO getInstance(){
        if(instance == null){
            instance = new WalletDAO();
        }
        return instance;
    }

    public Wallet getWallet(int walletID) {
        return null;
    }

}
