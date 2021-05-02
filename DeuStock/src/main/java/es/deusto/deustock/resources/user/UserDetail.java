package es.deusto.deustock.resources.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

/**
 * @author Erik B. Terres
 */
@Path("user/{username}")
public class UserDetail {
	
	private UserDAO userDAO;
	private final Logger logger = LoggerFactory.getLogger(UserDetail.class);
	
	public UserDetail(){
		this.userDAO = UserDAO.getInstance();
	}

	public void setUserDAO(UserDAO userDAO){
		this.userDAO = userDAO;
	}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsername(@PathParam("username") String username) throws SQLException {
        User user = userDAO.get(username);

        if(user == null){
            logger.error("Cannot get user  information");
            return Response.status(401).build();
        }

        UserDTO userDTO = userDAO.getDTO(user);

        return Response
                .status(Response.Status.OK)
                .entity(userDTO)
                .build();
    }
}