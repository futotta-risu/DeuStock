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
import es.deusto.deustock.simulation.investment.OperationFactory;
import es.deusto.deustock.simulation.investment.WalletService;
import es.deusto.deustock.simulation.investment.operations.Operation;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.simulation.investment.operations.OperationType;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static es.deusto.deustock.simulation.investment.operations.OperationType.LONG;

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
    ) throws StockNotFoundException {
        DeuLogger.logger.info("Petition to open a operation from " + username);
        DeuLogger.logger.error("Prueba T-12");
        OperationType operationType = OperationType.valueOf(operationTypeString);
        DeuLogger.logger.error("Prueba T-13");
        DeuStock stock;
        if(!stockDAO.has(symbol)){
            stock = new DeuStock(symbol);
            stockDAO.store(stock);
        }
        DeuLogger.logger.error("Prueba T-14");
        stock = stockDAO.get(symbol);
        DeuLogger.logger.error("Prueba T-15");
        DeuStock stockData = stockDataAPIGateway
                .getStockData(new StockQueryData(symbol).setWithHistoric(false));
        DeuLogger.logger.error("Prueba T-16");
        stock.setPrice(stockData.getPrice());
        DeuLogger.logger.error("Prueba T-17");
        Operation operation = operationFactory.create(operationType, stock, amount);
        User user = userDAO.getUser(username);
        DeuLogger.logger.error("Prueba T-18");
        walletService.setWallet(user.getWallet());
        walletService.openOperation(operation);
        DeuLogger.logger.error("Prueba T-19");
        return Response.status(200).build();
    }

}
