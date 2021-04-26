package es.deusto.deustock.simulation.investment;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.WalletDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.data.stocks.Wallet;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.simulation.investment.operations.OperationType;
import es.deusto.deustock.simulation.investment.operations.StockOperation;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
        double openPrice = operation.getOpenPrice();
        if(!wallet.hasEnoughMoney(openPrice)){
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
        wallet.changeMoney(-openPrice);
        walletDAO.update(wallet);

    }

    public void closeOperation(Operation operation, StockHistory stockHistory){
        double closePrice = operation.getClosePrice();

        wallet.changeMoney(closePrice);
        walletDAO.update(wallet);

        stockHistoryDAO.update(stockHistory.setClosed(true));
    }

    public List<StockHistoryDTO> getHoldings(){
        System.out.println("dasd-32 " + wallet);
        System.out.println("dasd-33 " + wallet.getHistory());
        List<StockHistoryDTO> result =  wallet.getHistory()
                .stream()
                .filter(c -> !c.isClosed())
                .map(stockHistoryDAO::getDTO)
                .collect(Collectors.toList());

        System.out.println(" Este el total " + result.size());
        return result;
    }

}
