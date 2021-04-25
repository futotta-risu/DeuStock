package es.deusto.deustock.simulation.investment;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.simulation.investment.operations.OperationType;
import es.deusto.deustock.simulation.investment.operations.StockOperation;

import javax.ws.rs.core.Response;

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

    public WalletService(String walletID){
        this.wallet = WalletDAO.getInstance().getWallet(walletID);
    }

    public WalletService(Wallet wallet){
        this.wallet = wallet;
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


    public void openOperation(Operation operation){
        if(!wallet.hasEnoughMoney(operation.getOpenPrice())){
            DeuLogger.logger.error("Not enough money on wallet.");
            return;
        }


        StockHistory stockHistory = stockHistoryDAO.create(
                wallet,
                operation.getStock(),
                operation.getAmount(),
                operation.getType()
        );

        stockHistoryDAO.store(stockHistory);
        walletDAO.updateMoney(wallet, -operation.getOpenPrice());

    }
}
