package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.services.investment.operations.OperationType;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erik B. Terres
 */
public class StockHistoryDAO {

    private static StockHistoryDAO instance;
    private IDBManager dbManager;

    private StockHistoryDAO(){
        super();
        dbManager = DBManager.getInstance();
    }

    public void setDbManager(IDBManager dbManager){
        this.dbManager = dbManager;
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


    public void store(StockHistory stockHistory) throws SQLException {
        dbManager.store(stockHistory);
    }

    public StockHistory get(String id) throws SQLException {
        return (StockHistory) dbManager.get(StockHistory.class, "id == "+id);
    }

    public List<StockHistory> getStockHistory(String walletID) throws SQLException {
        String condition = "wallet.id == '" + walletID + "'";
        return (List<StockHistory>)(List<?>) dbManager.getList(StockHistory.class, condition);
    }

    public void update(StockHistory stockHistory) throws SQLException {
        dbManager.update(stockHistory);
    }

    /**
     * Only sets data non dependent on external services
     */
    public StockHistoryDTO getDTO(StockHistory stockHistory){
        return new StockHistoryDTO()
                .setOpenPrice(stockHistory.getPrice())
                .setAmount(stockHistory.getAmount())
                .setSymbol(stockHistory.getStock().getAcronym())
                .setOperation(stockHistory.getOperation())
                .setId(stockHistory.getId());
    }

    /**
     * Only sets data non dependent on external services
     */
    public List<StockHistoryDTO> getDTO(List<StockHistory> stockHistory){
        return stockHistory.stream().map(this::getDTO).collect(Collectors.toList());
    }
}
