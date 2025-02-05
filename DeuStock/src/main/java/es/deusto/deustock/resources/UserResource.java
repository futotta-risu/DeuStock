package es.deusto.deustock.resources;

import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.resources.auth.Secured;
import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;
import jdk.jfr.SettingDefinition;
import org.apache.lucene.index.ReaderSlice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("tpuser")
public class UserResource {

    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(UserResource.class);

    public UserResource(){
        this.userService = new UserService();
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}")
    public Response getUser(@PathParam("username") String username) throws WebApplicationException{
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

    @PUT
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(
            UserDTO user,
            @Context SecurityContext securityContext
    ) throws WebApplicationException{
        logger.info("UserChangeDetail request");

        String username = securityContext.getUserPrincipal().getName();
        try{
            // TODO if username not equals userDTO forbidden;
            userService.updateUser(user);
        }catch (UserException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }

    @DELETE
    @Secured
    public Response deleteUser(
            @Context SecurityContext securityContext
    ) throws WebApplicationException{
        logger.info("User delete petition");
        String username = securityContext.getUserPrincipal().getName();

        try{
            userService.deleteUser(username);
        }catch (UserException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();
    }

}
