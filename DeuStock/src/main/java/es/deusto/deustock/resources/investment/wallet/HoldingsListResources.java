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
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.operation.type.Operation;
import es.deusto.deustock.services.investment.operation.type.OperationType;

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

    public HoldingsListResources(){
        walletService = new WalletService();
    }

    public void setWalletService(WalletService walletService) {
        this.walletService = walletService;
    }

    @Path("holdings")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHoldings(@PathParam("username") String username) throws WebApplicationException {
        List<StockHistoryDTO> holdings = null;
        try {
            holdings = walletService.getHoldings(username);
        } catch (SQLException | StockNotFoundException sqlException) {
            throw new WebApplicationException(sqlException.getMessage());
        }

        return Response.status(200).entity(holdings).build();
    }
    
    @GET
	@Path("/holdings/reset")
	public Response resetHoldings(@PathParam("username") String username) throws WebApplicationException {
        try {
            walletService.resetHoldings(username);
        } catch (SQLException sqlException) {
            throw new WebApplicationException(sqlException.getMessage(), Response.Status.UNAUTHORIZED);
        }
        return Response.status(200).build();
	}
}
