package es.deusto.deustock.resources.user;

import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.resources.auth.Secured;
import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * @author Erik B. Terres
 */
@Path("users/details/change")
public class UserChangeDetails {

    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserChangeDetails.class);

    public UserChangeDetails(){
        this.userService = new UserService();
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeDetails(
            UserDTO userDTO,
            @Context SecurityContext securityContext
    ) throws WebApplicationException{
        logger.info("UserChangeDetail request");
        String username =securityContext.getUserPrincipal().getName();
        try{
            // TODO if username not equls userDTO forbidden;
            userService.updateUser(userDTO);
        }catch (UserException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }
}
