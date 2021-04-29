package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * @author Erik B. Terres
 */
@Path("holdings")
public class BalanceResource {

    private UserDAO userDAO;

    public BalanceResource(){
        userDAO = UserDAO.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Path("{username}/balance")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBalance(@PathParam("username") String username){
        User user = userDAO.getUser(username);

        if(user == null){
            return Response.status(401).build();
        }

        return Response.status(Response.Status.OK)
                .entity(user.getWallet().getMoney())
                .build();
    }
}
