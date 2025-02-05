package es.deusto.deustock.services.investment.wallet;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.wallet.exceptions.NotEnoughMoneyException;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletNotFoundException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for wallet instance.
 *
 * @author Erik B. Terres
 */
public class WalletService {

    private StockHistoryDAO stockHistoryDAO;
    private WalletDAO walletDAO;
    private UserDAO userDAO;

    private final Logger logger = Logger.getLogger(WalletService.class);

    public WalletService(){
        userDAO = UserDAO.getInstance();
        walletDAO = WalletDAO.getInstance();
        stockHistoryDAO = StockHistoryDAO.getInstance();
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



    public double getBalance(String username) throws WalletException{
        try {
            Wallet wallet = userDAO.get(username).getWallet();
            return wallet.getMoney();
        }catch (SQLException e){
            throw new WalletException(e.getMessage());
        }
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



    public void resetHoldings(String username) throws WalletException {
        try{
            var user = userDAO.get(username);
            var wallet = user.getWallet();

            List<StockHistory> shList = stockHistoryDAO.getStockHistory(wallet.getId());
            for (StockHistory stockHistory : shList) {
                stockHistory.setClosed(true);
                stockHistoryDAO.update(stockHistory);
            }
            wallet.setMoney(5000);
            walletDAO.update(wallet);
        } catch (SQLException e){
            throw new WalletException("Error resetting wallet");
        }
    }


    public List<StockHistoryDTO> getHoldings(String username) throws WalletException {

        try {
            var user = userDAO.get(username);
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

    public List<StockHistoryDTO> getUserHistory(String username) throws WalletException {
        try {
            var user = userDAO.get(username);
            return user
                    .getWallet()
                    .getHistory()
                    .stream()
                    .filter(StockHistory::isClosed)
                    .map(stockHistoryDAO::getDTO)
                    .collect(Collectors.toList());
        }catch (SQLException e){
            throw new WalletException("Error getting wallet");
        }
    }


    public void updateMoney(String username, double amount) throws WalletException {
        var wallet = getWallet(username);

        if(!wallet.hasEnoughMoney(amount)){
            logger.error("Not enough money on wallet.");
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
            logger.error("Not enough money on wallet.");
            throw new NotEnoughMoneyException("Not enough money to open operation");
        }

        wallet.changeMoney(-amount);

        updateWallet(wallet);
    }

    /**
     *
     * @param stock Required stock with price
     * @throws WalletException
     */
    public void addToHoldings(String username, DeuStock stock, double amount, OperationType type) throws WalletException {
        var wallet = getWallet(username);

        var stockHistory = stockHistoryDAO.create(
                wallet, stock, amount, type
        );
        wallet.addHistory(stockHistory);

        updateWallet(wallet);
    }

    public StockHistory getStockHistory(String stockHistoryID) throws WalletException {
        try {
            return stockHistoryDAO.get(stockHistoryID);
        } catch (SQLException sqlException) {
            throw new WalletException("Error updating wallet");
        }
    }

    public void closeStockHistory(String stockHistoryID) throws WalletException {
        try {
            var stockHistory = stockHistoryDAO.get(stockHistoryID);
            stockHistory.setClosed(true);
            stockHistoryDAO.update(stockHistory);
        } catch (SQLException sqlException) {
            throw new WalletException("Error updating wallet");
        }
    }

}
