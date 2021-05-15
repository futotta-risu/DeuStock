package es.deusto.deustock.resources.user;

import es.deusto.deustock.services.user.UserService;
import es.deusto.deustock.services.user.exceptions.UserException;

import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UserDeleteResource {
    private UserService userService;

    private final Logger logger = Logger.getLogger(UserDeleteResource.class);

    public UserDeleteResource(){
        this.userService = new UserService();
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }


    /**
     * Metodo que permite eliminar un usuario de la BD, en el path se reciben el
     * nombre de usuario y la contraseña encriptada en SHA-256
     *
     * @param username Nombre del usuario
     * @param password Contraseña encriptada asociada a la cuenta
     * @return <strong>Response</strong> Devuelve una respuesta dependiendo del
     *         estado resultante del borrado:
     *         <ul>
     *         <li>200 - OK</li>
     *         <li>401 - Forbidden</li>
     *         </ul>
     */

    // TODO Change location
    @GET
    @Path("delete/{username}/{password}")
    public Response delete(
            @PathParam("username") String username,
            @PathParam("password") String password
    ) throws WebApplicationException {
        logger.info("User delete petition");
        try{
            userService.deleteUser(username, password);
        }catch (UserException e){
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

        return Response.status(200).build();

    }
}
