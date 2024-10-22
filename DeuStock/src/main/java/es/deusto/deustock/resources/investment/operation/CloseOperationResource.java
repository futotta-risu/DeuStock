package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.resources.auth.Secured;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * @author Erik B. Terres
 */
@Path("stock/operation")
public class CloseOperationResource {

    private static final Logger logger = Logger.getLogger(CloseOperationResource.class);

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

    @POST
    @Secured
    @Path("close")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response closeOperation(
            String stockHistoryID,
            @Context SecurityContext securityContext
        ) throws WebApplicationException {
        logger.info("Petition to close the operation");

        String username = securityContext.getUserPrincipal().getName();

        try {
            var history = walletService.getStockHistory(stockHistoryID);

            // TODO check if same user

            if(history.isClosed()){
                throw new WebApplicationException("Already Closed Operation", Response.Status.UNAUTHORIZED);
            }

            var stock = stockService.getStockWithPrice(history.getStock().getAcronym());
            double actualPrice = operationService.getClosePrice(
                    history.getOperation(), history.getPrice() , stock.getPrice(), history.getAmount()
            );

            walletService.updateMoneyByWalletID(history.getWallet().getId(), -actualPrice);
            walletService.closeStockHistory(stockHistoryID);
        } catch (WalletException | StockException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }

}
