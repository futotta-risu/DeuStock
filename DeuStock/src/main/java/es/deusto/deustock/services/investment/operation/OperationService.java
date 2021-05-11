package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;

import java.sql.SQLException;

/**
 *
 * Service for operations
 *
 * @author Erik B. Terres
 */
public class OperationService {
    Wallet wallet;

    StockHistoryDAO stockHistoryDAO;
    WalletDAO walletDAO;
    StockDAO stockDAO;
    StockDataAPIGateway stockDataAPIGateway;
    OperationFactory operationFactory;
    UserDAO userDAO;

    public OperationService(){
        stockHistoryDAO = StockHistoryDAO.getInstance();
        walletDAO = WalletDAO.getInstance();
        stockDAO = StockDAO.getInstance();
        stockDataAPIGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
        operationFactory = OperationFactory.getInstance();
        userDAO = UserDAO.getInstance();
    }

    public void setStockHistoryDAO(StockHistoryDAO stockHistoryDAO) {
        this.stockHistoryDAO = stockHistoryDAO;
    }

    public void setWalletDAO(WalletDAO walletDAO) {
        this.walletDAO = walletDAO;
    }

    public void setWallet(Wallet wallet){
        this.wallet = wallet;
    }

    public void setWallet(String walletID) throws SQLException {
        this.wallet = walletDAO.getWallet(walletID);
    }


    public double getOpenPrice(
            OperationType operationType, double openPrice, double amount
    ) throws OperationException{
        Operation operation = operationFactory.create(operationType, openPrice, amount);
        return operation.getOpenPrice();
    }

    public double getClosePrice(OperationType operationType, double openPrice, double closePrice, double amount) throws OperationException {

        Operation operation = operationFactory.create(operationType, openPrice, amount);
        return operation.getClosePrice(closePrice);
    }

}
