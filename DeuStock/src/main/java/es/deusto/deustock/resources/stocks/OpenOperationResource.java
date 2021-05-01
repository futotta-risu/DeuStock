package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.User;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.services.investment.OperationFactory;
import es.deusto.deustock.services.investment.WalletService;
import es.deusto.deustock.services.investment.exceptions.OperationException;
import es.deusto.deustock.services.investment.operations.Operation;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.investment.operations.OperationType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Erik B. Terres
 */
@Path("stock/operation/open")
public class OpenOperationResource {

    private WalletService walletService;
    private OperationFactory operationFactory;
    private StockDataAPIGateway stockDataAPIGateway;
    private StockDAO stockDAO;
    private UserDAO userDAO;

    public OpenOperationResource(){
        walletService = new WalletService();
        operationFactory = OperationFactory.getInstance();
        stockDataAPIGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
        stockDAO = StockDAO.getInstance();
        userDAO = UserDAO.getInstance();
    }

    public void setOperationFactory(OperationFactory operationFactory) {
        this.operationFactory = operationFactory;
    }

    public void setStockDataAPIGateway(StockDataAPIGateway stockDataAPIGateway) {
        this.stockDataAPIGateway = stockDataAPIGateway;
    }

    public void setStockDAO(StockDAO stockDAO){
        this.stockDAO = stockDAO;
    }

    public void setWalletService(WalletService walletService){
        this.walletService = walletService;
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{operation}/{symbol}/{username}/{amount}")
    public Response openOperation(
            @PathParam("operation") String operationTypeString,
            @PathParam("symbol") String symbol,
            @PathParam("username") String username,
            @PathParam("amount") double amount
    ) throws StockNotFoundException, OperationException, SQLException {
        DeuLogger.logger.info("Petition to open a operation from " + username);

        OperationType operationType = OperationType.valueOf(operationTypeString);

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

        walletService.setWallet(user.getWallet());
        walletService.openOperation(operation);

        return Response.status(200).build();
    }

}
