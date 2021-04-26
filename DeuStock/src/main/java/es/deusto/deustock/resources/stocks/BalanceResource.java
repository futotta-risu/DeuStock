package es.deusto.deustock.resources.stocks;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Erik B. Terres
 */
@Path("holdings")
public class BalanceResource {

    private UserDAO userDAO;

    public BalanceResource(){
        System.out.println("t.6");
        userDAO = UserDAO.getInstance();
    }

    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Path("{username}/balance")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getBalance(@PathParam("username") String username){
        System.out.println("t.1");
        User user = userDAO.getUser(username);
        System.out.println("t.4");
        if(user == null){
            System.out.println("t.3");
            return Response.status(401).build();
        }
        System.out.println("t.2");
        System.out.println(user == null);
        System.out.println(user.toString());
        System.out.println(user.getWallet());
        System.out.println(user.getWallet().getMoney());
        System.out.println("t.1");
        return Response.status(Response.Status.OK)
                .entity(user.getWallet().getMoney())
                .build();
    }
}
