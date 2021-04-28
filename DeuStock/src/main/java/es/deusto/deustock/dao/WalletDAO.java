package es.deusto.deustock.dao;

import es.deusto.deustock.data.stocks.Wallet;

/**
 * @author Erik B. Terres
 */
public class WalletDAO {
	
    private IDBManager dbManager;
    private static WalletDAO instance = null;

    private WalletDAO(){
        super();
        dbManager = DBManager.getInstance();
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
        dbManager.storeObject(wallet);
    }

    public void update(Wallet wallet){
        dbManager.updateObject(wallet);
    }


    public Wallet getWallet(String walletID) {
        String condition = "id == '" + walletID + "'";
        return (Wallet) dbManager.getObject(Wallet.class, condition);
    }


}
