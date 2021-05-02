package es.deusto.deustock.dao;

import es.deusto.deustock.data.stocks.Wallet;

import java.sql.SQLException;

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

    public void store(Wallet wallet) throws SQLException {
        dbManager.store(wallet);
    }

    public void update(Wallet wallet) throws SQLException {
        dbManager.update(wallet);
    }


    public Wallet getWallet(String walletID) throws SQLException {
        String condition = "id == '" + walletID + "'";
        return (Wallet) dbManager.get(Wallet.class, condition);
    }


}
