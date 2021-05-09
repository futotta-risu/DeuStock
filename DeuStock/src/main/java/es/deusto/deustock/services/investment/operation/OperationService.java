package es.deusto.deustock.services.investment.operation;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.stock.StockService;

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


    public void openOperation(
            OperationType operationType,
            String symbol,
            String username,
            double amount
    ) throws OperationException, SQLException, StockNotFoundException {

        DeuStock stock;
        if(!stockDAO.has(symbol)){
            stock = new DeuStock(symbol);
            stockDAO.store(stock);
        }

        stock = stockDAO.get(symbol);

        DeuStock stockData = stockDataAPIGateway
                .getStockData(new StockQueryData(symbol).setWithHistoric(false));
        stock.setPrice(stockData.getPrice());

        Operation operation = operationFactory.create(operationType, stock, amount);
        User user = userDAO.get(username);

        this.wallet = user.getWallet();

        double openPrice = operation.getOpenPrice();
        if(!wallet.hasEnoughMoney(openPrice)){
            DeuLogger.logger.error("Not enough money on wallet.");
            throw new OperationException("Not enough money to open operation");
        }

        StockHistory stockHistory = stockHistoryDAO.create(
                wallet,
                operation.getStock(),
                operation.getAmount(),
                operation.getType()
        );

        stockHistoryDAO.store(stockHistory);
        wallet.changeMoney(-openPrice);
        walletDAO.update(wallet);
    }

    public void closeOperation(String stockHistoryID) throws OperationException, StockNotFoundException {

        StockHistory stockHistory;
        try {
            stockHistory = stockHistoryDAO.get(stockHistoryID);
        } catch (SQLException throwables) {
            throw new OperationException("Unknown stockHistoryID");
        }

        if(stockHistory.isClosed()){
            throw new OperationException("Unknown stockHistoryID");
        }


        DeuStock stock = stockDataAPIGateway
                .getStockData(
                        new StockQueryData(
                                stockHistory.getStock().getAcronym()
                        ).setWithHistoric(false)
                );

        Operation operation = operationFactory.create(
                stockHistory.getOperation(),
                stock,
                stockHistory.getAmount()
        );
        double closePrice = operation.getClosePrice();

        this.wallet = stockHistory.getWallet();

        try {
            wallet.changeMoney(closePrice);
            walletDAO.update(wallet);
            stockHistory.setClosed(true);
            stockHistoryDAO.update(stockHistory);
        }catch (SQLException sqlException){
            // TODO Add catch functionality
            throw new OperationException("SQL Error");
        }
    }

}
