package es.deusto.deustock.simulation.investment;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.simulation.investment.exceptions.OperationException;
import es.deusto.deustock.simulation.investment.operations.Operation;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erik B. Terres
 */
public class WalletService {
    Wallet wallet;

    StockHistoryDAO stockHistoryDAO;
    WalletDAO walletDAO;

    public WalletService(){
        stockHistoryDAO = StockHistoryDAO.getInstance();
        walletDAO = WalletDAO.getInstance();
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

    public void setWallet(String walletID){
        this.wallet = walletDAO.getWallet(walletID);
    }


    public void openOperation(@NotNull Operation operation) throws OperationException {
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

    public void closeOperation(Operation operation, StockHistory stockHistory){
        double closePrice = operation.getClosePrice();

        wallet.changeMoney(closePrice);
        walletDAO.update(wallet);
        stockHistory.setClosed(true);
        stockHistoryDAO.update(stockHistory);
    }

    public List<StockHistoryDTO> getHoldings(){

        return wallet.getHistory()
                .stream()
                .filter(c -> !c.isClosed())
                .map(stockHistoryDAO::getDTO)
                .collect(Collectors.toList());
    }

}
