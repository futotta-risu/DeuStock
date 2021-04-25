package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import java.util.List;

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
        return new StockHistory(wallet, stock, stock.getPrice(), amount, operationType);
    }


    public void store(StockHistory stockHistory){
        DBManager.getInstance().storeObject(stockHistory);
    }

    public StockHistory get(String id){
        return (StockHistory) DBManager.getInstance().getObject(StockHistory.class, "id == "+id);
    }

    public List<StockHistory> getStock(Wallet wallet){
        return (List<StockHistory>)(List<?>) DBManager.getInstance().getObjects(StockHistory.class);
    }

    public void update(StockHistory stockHistory){
        DBManager.getInstance().updateObject(stockHistory);
    }

}
