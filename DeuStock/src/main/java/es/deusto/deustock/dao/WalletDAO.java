package es.deusto.deustock.dao;

import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;

import java.util.List;

/**
 * @author Erik B. Terres
 */
public class WalletDAO {
	
    private IDBManager dbManager;
    private static WalletDAO instance = null;

    private WalletDAO(){
        super();
    }
    
    public void setDBManager(IDBManager dbManager){
        this.dbManager = dbManager;
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
