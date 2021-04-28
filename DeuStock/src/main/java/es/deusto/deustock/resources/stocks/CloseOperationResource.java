package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.StockHistoryDAO;
import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataAPIGateway;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayEnum;
import es.deusto.deustock.dataminer.gateway.stocks.StockDataGatewayFactory;
import es.deusto.deustock.dataminer.gateway.stocks.StockQueryData;
import es.deusto.deustock.dataminer.gateway.stocks.exceptions.StockNotFoundException;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.simulation.investment.OperationFactory;
import es.deusto.deustock.simulation.investment.WalletService;
import es.deusto.deustock.simulation.investment.operations.Operation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("stock/operation/close")
public class CloseOperationResource {

    private StockHistoryDAO stockHistoryDAO;
    private WalletService walletService;
    private OperationFactory operationFactory;
    private StockDataAPIGateway stockDataAPIGateway;

    public CloseOperationResource(){
        stockHistoryDAO = StockHistoryDAO.getInstance();
        operationFactory = OperationFactory.getInstance();
        stockDataAPIGateway = StockDataGatewayFactory.getInstance().create(StockDataGatewayEnum.YahooFinance);
        walletService = new WalletService();
    }

    public void setOperationFactory(OperationFactory operationFactory) {
        this.operationFactory = operationFactory;
    }

    public void setStockDataAPIGateway(StockDataAPIGateway stockDataAPIGateway) {
        this.stockDataAPIGateway = stockDataAPIGateway;
    }

    public void setStockHistoryDAO(StockHistoryDAO stockHistoryDAO){
        this.stockHistoryDAO = stockHistoryDAO;
    }

    public void setWalletService(WalletService walletService){
        this.walletService = walletService;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{stockHistoryID}")
    public Response closeOperation(
            @PathParam("stockHistoryID") String stockHistoryID
    ) throws StockNotFoundException {
        DeuLogger.logger.info("Petition to close the operation " + stockHistoryID);


        StockHistory stockHistory = stockHistoryDAO.get(stockHistoryID);

        if(stockHistory == null || stockHistory.isClosed()){
            return Response.status(401).build();
        }

        DeuStock stock = stockDataAPIGateway
                .getStockData(
                        new StockQueryData(
                                stockHistory.getStock().getAcronym()
                        ).setWithHistoric(false)
                );

        Operation operation = operationFactory.create(
                stockHistory.getOperation(),
                stock,
                stockHistory.getAmount()
        );

        walletService.setWallet(stockHistory.getWallet());
        walletService.closeOperation(operation, stockHistory);

        return Response.status(200).build();
    }

}
