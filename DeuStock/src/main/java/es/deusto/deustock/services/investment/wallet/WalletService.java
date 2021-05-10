package es.deusto.deustock.services.investment.wallet;

import com.thoughtworks.qdox.model.expression.Not;
import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.investment.operation.OperationFactory;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.wallet.exceptions.NotEnoughMoneyException;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletNotFoundException;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for wallet the wallet instance.
 */
public class WalletService {

    private StockHistoryDAO stockHistoryDAO;
    private WalletDAO walletDAO;
    private UserDAO userDAO;
    private OperationFactory operationFactory;
    private StockDataAPIGateway stockGateway;

    public WalletService(){
        userDAO = UserDAO.getInstance();
        walletDAO = WalletDAO.getInstance();
        stockHistoryDAO = StockHistoryDAO.getInstance();
        operationFactory = OperationFactory.getInstance();
        stockGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void setStockHistoryDAO(StockHistoryDAO stockHistoryDAO){
        this.stockHistoryDAO = stockHistoryDAO;
    }

    public void setWalletDAO(WalletDAO walletDAO){
        this.walletDAO = walletDAO;
    }



    public Response getBalance(String username){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private Wallet getWallet(String username) throws WalletException {
        Wallet wallet;
        try {
            wallet = userDAO.get(username).getWallet();

            if(wallet == null){
                throw new WalletNotFoundException("Wallet not found");
            }
        } catch (SQLException sqlException) {
            throw new WalletNotFoundException("Wallet not found");
        }
        return wallet;
    }

    private void updateWallet(Wallet wallet) throws WalletException {
        try {
            walletDAO.update(wallet);
        } catch (SQLException sqlException) {
            throw new WalletException("Error updating wallet");
        }
    }



    public void resetHoldings(String username) throws SQLException {
        User user = userDAO.get(username);
        Wallet wallet = user.getWallet();

        List<StockHistory> shList = stockHistoryDAO.getStockHistory(wallet.getId());
        for (StockHistory stockHistory : shList) {
            stockHistory.setClosed(true);
            stockHistoryDAO.update(stockHistory);
        }
        wallet.setMoney(5000);
        walletDAO.update(wallet);
    }


    public List<StockHistoryDTO> getHoldings(String username) throws WalletException {

        try {
            User user = userDAO.get(username);
            return user
                    .getWallet()
                    .getHistory()
                    .stream()
                    .filter(c -> !c.isClosed())
                    .map(stockHistoryDAO::getDTO)
                    .collect(Collectors.toList());
        }catch (SQLException e){
            throw new WalletException("Error getting wallet");
        }
    }


    public void updateMoney(String username, double amount) throws WalletException {
        Wallet wallet = getWallet(username);

        if(!wallet.hasEnoughMoney(amount)){
            DeuLogger.logger.error("Not enough money on wallet.");
            throw new NotEnoughMoneyException("Not enough money to open operation");
        }

        wallet.changeMoney(-amount);

        updateWallet(wallet);
    }

    public void updateMoneyByWalletID(String walletID, double amount) throws WalletException {
        Wallet wallet = null;
        try {
            wallet = walletDAO.getWallet(walletID);
        } catch (SQLException sqlException) {
            throw new WalletException("Unknown wallet");
        }

        if(!wallet.hasEnoughMoney(amount)){
            DeuLogger.logger.error("Not enough money on wallet.");
            throw new NotEnoughMoneyException("Not enough money to open operation");
        }

        wallet.changeMoney(-amount);

        updateWallet(wallet);
    }

    /**
     *
     * @param username
     * @param stock Required stock with price
     * @param amount
     * @param type
     * @throws WalletException
     */
    public void addToHoldings(String username, DeuStock stock, double amount, OperationType type) throws WalletException {

        Wallet wallet = getWallet(username);

        StockHistory stockHistory = stockHistoryDAO.create(
                wallet, stock, amount, type
        );

        try {
            stockHistoryDAO.store(stockHistory);
        } catch (SQLException sqlException) {
            throw new WalletException("Error updating wallet");
        }

        updateWallet(wallet);
    }

    public StockHistory getStockHistory(String stockHistoryID) throws WalletException {
        StockHistory stockHistory;

        try {
            stockHistory = stockHistoryDAO.get(stockHistoryID);
        } catch (SQLException sqlException) {
            throw new WalletException("Error updating wallet");
        }

        return stockHistory;
    }

    public void closeStockHistory(String stockHistoryID) throws WalletException {
        StockHistory stockHistory;

        try {
            stockHistory = stockHistoryDAO.get(stockHistoryID);
            stockHistory.setClosed(true);
            stockHistoryDAO.update(stockHistory);
        } catch (SQLException sqlException) {
            throw new WalletException("Error updating wallet");
        }


    }

}
