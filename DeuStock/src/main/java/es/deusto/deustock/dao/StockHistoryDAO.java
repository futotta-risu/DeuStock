package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.simulation.investment.operations.OperationType;

/**
 * @author Erik B. Terres
 */
public class StockHistoryDAO {

    private static StockHistoryDAO instance;

    private StockHistoryDAO(){
        super();
    }

    public static StockHistoryDAO getInstance(){
        if(instance == null){
            instance = new StockHistoryDAO();
        }
        return instance;
    }

    public StockHistory create(Wallet wallet, DeuStock stock, double amount, OperationType operationType){
        StockHistory stockHistory = new StockHistory(wallet, stock, stock.getPrice(), amount, operationType);
        return stockHistory;
    }


    public void store(StockHistory stockHistory){
        DBManager.getInstance().storeObject(stockHistory);
    }

}
