package es.deusto.deustock.resources.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.log.DeuLogger;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("user/{username}")
public class UserDetail {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsername(@PathParam("username") String username) {
        UserDAO userDAO = UserDAO.getInstance();

        User user = userDAO.getUser(username);
        UserDTO userDTO = userDAO.getDTO(user);

        if(user == null){
            DeuLogger.logger.error("Cannot get '" + username + "' user  information");
            return Response.status(401).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(user)
                .build();
    }
}