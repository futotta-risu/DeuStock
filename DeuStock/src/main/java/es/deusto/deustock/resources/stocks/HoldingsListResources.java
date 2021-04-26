package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.dao.UserDAO;
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
import es.deusto.deustock.simulation.investment.OperationFactory;
import es.deusto.deustock.simulation.investment.WalletService;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Erik B. Terres
 */
@Path("user/{username}/holdings")
public class HoldingsListResources {

    private UserDAO userDAO;
    private WalletService walletService;
    private StockDataAPIGateway stockGateway;
    private OperationFactory operationFactory;

    public HoldingsListResources(){
        userDAO = UserDAO.getInstance();
        walletService = new WalletService();
        stockGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
        operationFactory = OperationFactory.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void setWalletService(WalletService walletService) {
        this.walletService = walletService;
    }

    public void setStockGateway(StockDataAPIGateway stockDataAPIGateway){
        this.stockGateway = stockDataAPIGateway;
    }

    public void setOperationFactory(OperationFactory operationFactory){
        this.operationFactory = operationFactory;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHoldings(@PathParam("username") String username) throws StockNotFoundException {
        User user = userDAO.getUser(username);

        if(user == null){
            throw new IllegalArgumentException("Username does not exit");
        }
        Wallet wallet = user.getWallet();

        walletService.setWallet(wallet);
        List<StockHistory> holdingsN = StockHistoryDAO.getInstance().getStockHistory(wallet.getId());
        holdingsN = holdingsN.stream().filter(c -> !c.isClosed()).collect(Collectors.toList());
        List<StockHistoryDTO> holdings = StockHistoryDAO.getInstance().getDTO(holdingsN);

        for(StockHistoryDTO stockHistoryDTO : holdings){
            DeuStock stock = new DeuStock(stockHistoryDTO.getSymbol())
                    .setPrice(stockHistoryDTO.getOpenPrice());

            OperationType operation = stockHistoryDTO.getOperation();

            Operation openOperation = operationFactory.create(operation, stock, stockHistoryDTO.getAmount());

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
}
