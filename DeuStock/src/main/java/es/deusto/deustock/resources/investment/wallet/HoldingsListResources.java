package es.deusto.deustock.resources.investment.wallet;

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
import es.deusto.deustock.services.investment.operation.OperationService;
import es.deusto.deustock.services.investment.operation.exceptions.OperationException;
import es.deusto.deustock.services.investment.stock.StockService;
import es.deusto.deustock.services.investment.stock.exceptions.StockException;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Erik B. Terres
 */
@Path("user/{username}/")
public class HoldingsListResources {

    private WalletService walletService;
    private StockService stockService;
    private OperationService operationService;

    public HoldingsListResources(){
        walletService = new WalletService();
        stockService = new StockService();
        operationService = new OperationService();
    }

    public void setWalletService(WalletService walletService) {
        this.walletService = walletService;
    }

    public void setOperationService(OperationService operationService){
        this.operationService = operationService;
    }

    public void setStockService(StockService stockService){
        this.stockService = stockService;
    }

    @Path("holdings")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHoldings(@PathParam("username") String username) throws WebApplicationException {
        List<StockHistoryDTO> holdings = null;
        try {
            holdings = walletService.getHoldings(username);

            for(StockHistoryDTO hist : holdings){
                DeuStock stock = stockService.getStockWithPrice(hist.getSymbol());

                hist.setOpenValue(
                    operationService.getOpenPrice(
                        hist.getOperation(), hist.getOpenPrice(), hist.getAmount()
                    )
                );
                hist.setActualValue(
                    operationService.getClosePrice(
                        hist.getOperation(), hist.getOpenPrice(), stock.getPrice(), hist.getAmount()
                    )
                );
            }
        } catch (WalletException | StockException | OperationException sqlException) {
            throw new WebApplicationException(sqlException.getMessage());
        }

        return Response.status(200).entity(holdings).build();
    }
    
    @GET
	@Path("/holdings/reset")
	public Response resetHoldings(@PathParam("username") String username) throws WebApplicationException {
        try {
            walletService.resetHoldings(username);
        } catch (WalletException sqlException) {
            throw new WebApplicationException(sqlException.getMessage(), Response.Status.UNAUTHORIZED);
        }
        return Response.status(200).build();
	}
}
