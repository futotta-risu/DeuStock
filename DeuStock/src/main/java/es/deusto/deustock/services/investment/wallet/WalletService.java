package es.deusto.deustock.services.investment.wallet;

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
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;

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


    public List<StockHistoryDTO> getHoldings(String username) throws SQLException, StockNotFoundException {
        User user = userDAO.get(username);

        List<StockHistoryDTO> holdings = user
                .getWallet()
                .getHistory()
                .stream()
                .filter(c -> !c.isClosed())
                .map(stockHistoryDAO::getDTO)
                .collect(Collectors.toList());


        for(StockHistoryDTO stockHistoryDTO : holdings){
            DeuStock stock = new DeuStock(stockHistoryDTO.getSymbol())
                    .setPrice(stockHistoryDTO.getOpenPrice());

            OperationType opType = stockHistoryDTO.getOperation();

            Operation openOperation = operationFactory.create(opType, stock, stockHistoryDTO.getAmount());

            stockHistoryDTO.setOpenValue(openOperation.getOpenPrice());

            stock = stockGateway.getStockData(
                    new StockQueryData(stockHistoryDTO.getSymbol())
                            .setWithHistoric(false)
            );

            // Close y Actual
            Operation closeOperation = operationFactory.create(stockHistoryDTO.getOperation(), stock, stockHistoryDTO.getAmount());
            stockHistoryDTO.setActualPrice(stock.getPrice())
                    .setActualValue(closeOperation.getClosePrice());

        }
        return holdings;
    }

}
