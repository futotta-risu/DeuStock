package es.deusto.deustock.resources.user;

import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("user/{username}")
public class UserDetail {
	
	private UserService userService;
	private final Logger logger = Logger.getLogger(UserDetail.class);
	
	public UserDetail(){
		this.userService = new UserService();
	}

	public void setUserService(UserService userService){
		this.userService = userService;
	}

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Response getUsername(@PathParam("username") String username) throws WebApplicationException{
        logger.info("Get user petition");
	    UserDTO userDTO;

        try {
            userDTO = userService.getUserByUsername(username);
        } catch (UserException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response
                .status(Response.Status.OK)
                .entity(userDTO)
                .build();
    }
}