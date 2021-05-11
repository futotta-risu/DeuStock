package es.deusto.deustock.resources.user;

import es.deusto.deustock.data.dto.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Erik B. Terres
 */
@Path("users/details/change")
public class UserChangeDetails {

    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserDetail.class);

    public UserChangeDetails(){
        this.userService = new UserService();
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changeDetails(UserDTO userDTO) throws WebApplicationException{

        try{
            userService.updateUser(userDTO);
        }catch (UserException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }
}
