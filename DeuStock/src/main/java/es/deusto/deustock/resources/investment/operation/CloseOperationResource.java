package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.data.stocks.StockHistory;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("stock/operation/close")
public class CloseOperationResource {

    private OperationService operationService;
    private WalletService walletService;
    private StockService stockService;

    public CloseOperationResource(){
        operationService = new OperationService();
        walletService = new WalletService();
        stockService = new StockService();
    }

    public void setOperationService(OperationService operationService){
        this.operationService = operationService;
    }

    public void setWalletService(WalletService walletService){
        this.walletService = walletService;
    }

    public void setStockService(StockService stockService){
        this.stockService = stockService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{stockHistoryID}")
    public Response closeOperation(
            @PathParam("stockHistoryID") String stockHistoryID
    ) throws WebApplicationException {
        DeuLogger.logger.info("Petition to close the operation");

        try {
            StockHistory history = walletService.getStockHistory(stockHistoryID);
            if(history.isClosed()){
                throw new WebApplicationException("Already Closed Operation", Response.Status.UNAUTHORIZED);
            }

            DeuStock stock = stockService.getStockWithPrice(history.getStock().getAcronym());
            double actualPrice = operationService.getClosePrice(
                    history.getOperation(), history.getPrice() , stock.getPrice(), history.getAmount()
            );

            walletService.updateMoneyByWalletID(history.getWallet().getId(), -actualPrice);
            walletService.closeStockHistory(stockHistoryID);
        } catch (OperationException | WalletException | StockException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }

}