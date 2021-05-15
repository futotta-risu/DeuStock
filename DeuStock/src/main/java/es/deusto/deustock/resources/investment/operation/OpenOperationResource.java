package es.deusto.deustock.resources.investment.operation;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;


import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("stock/operation/open")
public class OpenOperationResource {

    private static final Logger logger = Logger.getLogger(OpenOperationResource.class);

    private OperationService operationService;
    private StockService stockService;
    private WalletService walletService;

    public OpenOperationResource(){
        operationService = new OperationService();
        stockService = new StockService();
        walletService = new WalletService();
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
    @Path("/{operation}/{symbol}/{username}/{amount}")
    public Response openOperation(
            @PathParam("operation") String operationTypeString,
            @PathParam("symbol") String symbol,
            @PathParam("username") String username,
            @PathParam("amount") double amount
    ) throws WebApplicationException {
        logger.info("Petition to open a operation");

        var operationType = OperationType.valueOf(operationTypeString);

        try{
            DeuStock stock = stockService.getStockWithPrice(symbol);
            double openPrice = operationService.getOpenPrice(operationType, stock.getPrice(),amount);
            walletService.updateMoney(username, openPrice);
            walletService.addToHoldings(username, stock, amount, operationType);
        }catch (StockException | WalletException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }


        return Response.status(200).build();
    }

}
