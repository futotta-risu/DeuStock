package es.deusto.deustock.resources.stocks;

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
import es.deusto.deustock.services.investment.OperationFactory;
import es.deusto.deustock.services.investment.WalletService;
import es.deusto.deustock.services.investment.operations.Operation;
import es.deusto.deustock.services.investment.operations.OperationType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Erik B. Terres
 */
@Path("user/{username}/")
public class HoldingsListResources {

    private UserDAO userDAO;
    private WalletDAO walletDAO;
    private StockHistoryDAO stockHistoryDAO;
    private WalletService walletService;
    private StockDataAPIGateway stockGateway;
    private OperationFactory operationFactory;


    public HoldingsListResources(){
        userDAO = UserDAO.getInstance();
        walletDAO = WalletDAO.getInstance();
        walletService = new WalletService();
        stockGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
        operationFactory = OperationFactory.getInstance();
        stockHistoryDAO = StockHistoryDAO.getInstance();

    }

    public void setUserDAO(UserDAO userDAO) { this.userDAO = userDAO; }
    public void setWalletDAO(WalletDAO walletDAO) { this.walletDAO = walletDAO; }
    public void setStockGateway(StockDataAPIGateway stockDataAPIGateway) { this.stockGateway = stockDataAPIGateway; }
    public void setOperationFactory(OperationFactory operationFactory) { this.operationFactory = operationFactory; }
    public void setStockHistoryDAO(StockHistoryDAO stockHistoryDAO) { this.stockHistoryDAO = stockHistoryDAO; }

    @Path("holdings")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHoldings(@PathParam("username") String username) throws StockNotFoundException, SQLException {
        User user = userDAO.getUser(username);

        if(user == null){
            throw new IllegalArgumentException("Username does not exit");
        }
        Wallet wallet = user.getWallet();
        walletService.setWallet(wallet);

        List<StockHistoryDTO> holdings = walletService.getHoldings();


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
        return Response.status(200).entity(holdings).build();
    }
    
    @GET
	@Path("/holdings/reset")
	public Response resetHoldings(@PathParam("username") String username) throws SQLException {
		DeuLogger.logger.info("User delete petition for User " + username);
		User user = userDAO.getUser(username);

		if (user == null) {
			DeuLogger.logger.warn("User " + username + " not found in DB while deleting");
			return Response.status(401).build();
		}

		Wallet wallet = user.getWallet();

		List<StockHistory> shList = stockHistoryDAO.getStockHistory(wallet.getId());
		for (StockHistory stockHistory : shList) {
			stockHistory.setClosed(true);
			stockHistoryDAO.update(stockHistory);
		}
		wallet.setMoney(5000);
		walletDAO.update(wallet);

		return Response.status(200).build();
	}
}
