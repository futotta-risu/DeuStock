package es.deusto.deustock.dao;

import es.deusto.deustock.data.stocks.Wallet;

import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * Singleton Wallet class DAO. Contains all the methods to handle the
 * database process for the Wallet class.
 *
 * {@link WalletDAO#getInstance()}
 *
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
        var whereCondition = "id == :walletID";
        HashMap<String,String> params = new HashMap<>();
        params.put("walletID", walletID);
        return (Wallet) dbManager.get(Wallet.class, whereCondition, params);
    }


}
