package es.deusto.deustock.resources.investment.wallet;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.resources.auth.Secured;
import es.deusto.deustock.services.investment.wallet.WalletService;
import es.deusto.deustock.services.investment.wallet.exceptions.WalletException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.sql.SQLException;


/**
 * @author Erik B. Terres
 */
@Path("holdings")
public class BalanceResource {

    private WalletService walletService;

    public BalanceResource(){
        walletService = new WalletService();
    }

    public void setWalletService(WalletService walletService){
        this.walletService = walletService;
    }

    @GET
    @Secured
    @Path("/balance")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBalance(@Context SecurityContext securityContext)  {
        String username = securityContext.getUserPrincipal().getName();

        double balance;
        try{
            balance = walletService.getBalance(username);
        }catch (WalletException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.ok(balance).build();
    }
}
