package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockDAO;
import es.deusto.deustock.data.DeuStock;
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

    public OpenOperationResource(){
        walletService = new WalletService();
        operationFactory = OperationFactory.getInstance();
        stockDataAPIGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
        stockDAO = StockDAO.getInstance();
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


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{operation}/{symbol}/{walletID}/{amount}")
    public Response openOperation(
            @PathParam("operation") String operationTypeString,
            @PathParam("symbol") String symbol,
            @PathParam("walletID") String walletID,
            @PathParam("amount") double amount
    ) throws StockNotFoundException {
        DeuLogger.logger.info("Petition to open a operation from " + walletID);

        OperationType operationType = OperationType.valueOf(operationTypeString);

        DeuStock stock;
        if(!stockDAO.hasStock(symbol)){
            stock = new DeuStock(symbol);
            stockDAO.storeStock(stock);

        }
        stock = stockDAO.getStock(symbol);

        DeuStock stockData = stockDataAPIGateway
                .getStockData(new StockQueryData(symbol).setWithHistoric(false));

        stock.setPrice(stockData.getPrice());

        Operation operation = operationFactory.create(operationType, stock, amount);

        walletService.setWallet(walletID);
        walletService.openOperation(operation);

        return Response.status(200).build();
    }

}
