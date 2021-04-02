package es.deusto.deustock.resources.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Erik B. Terres
 */
@Path("user/{username}")
public class UserDetail {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUsername(@PathParam("username") String username) {
        return UserDAO.getInstance().getUser(username);
    }
}