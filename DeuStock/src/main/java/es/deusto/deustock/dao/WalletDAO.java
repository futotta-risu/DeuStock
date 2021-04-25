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

    public void store(Wallet wallet){
        DBManager.getInstance().storeObject(wallet);
    }

    public void update(Wallet wallet){
        DBManager.getInstance().updateObject(wallet);
    }


    public Wallet getWallet(String walletID) {
        String condition = "id == '" + walletID + "'";
        return (Wallet) DBManager.getInstance().getObject(Wallet.class, condition);
    }



}
