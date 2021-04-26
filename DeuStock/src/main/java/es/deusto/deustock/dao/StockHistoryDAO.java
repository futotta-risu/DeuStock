package es.deusto.deustock.dao;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.simulation.investment.OperationFactory;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public List<StockHistory> getStockHistory(String walletID){
        String condition = "wallet.id == '" + walletID + "'";
        return (List<StockHistory>)(List<?>) DBManager.getInstance().getObjects(StockHistory.class, condition);
    }

    public List<StockHistory> getStock(Wallet wallet){
        return (List<StockHistory>)(List<?>) DBManager.getInstance().getObjects(StockHistory.class);
    }

    public void update(StockHistory stockHistory){
        DBManager.getInstance().updateObject(stockHistory);
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