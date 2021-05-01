package es.deusto.deustock.resources.auth;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.services.auth.AuthService;
import es.deusto.deustock.services.auth.exceptions.AuthException;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Clase que contiene los metodos REST asociados a la clase Usuario
 * 
 * @author landersanmillan 
 * @see UserDAO
 */
@Path("users")
public class UserResource {

	private UserDAO userDAO;
	private AuthService authService;

	private Logger logger = LoggerFactory.getLogger(UserResource.class);

	public UserResource(){
		this.userDAO = UserDAO.getInstance();
		authService = new AuthService();
	}

	public void setUserDAO(UserDAO userDAO){
		this.userDAO = userDAO;
	}

	public void setAuthService(AuthService authService){
		this.authService = authService;
	}


	/**
	 * Metodo que permite iniciar sesion a un usuario, en el path se reciben el
	 * nombre de usuario y la contraseña encriptada en SHA-256
	 * 
	 * @param username Nombre del usuario
	 * @param password Contraseña encriptada asociada a la cuenta
	 * @return <strong>User</strong> Objeto de usuario que contiene toda la
	 *         informacion de la cuenta con la que se ha iniciado sesion
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login/{username}/{password}")
	public Response login(@PathParam("username") String username, @PathParam("password") String password){
		logger.info("Login petition detected");

		UserDTO user;

		try{
			user = authService.login(username, password);
		}catch (LoginException e){
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}

		return Response.ok(user).build();
	}

	/**
	 * Metodo que permite registrar un nuevo usuario en la BD, se recibe la
	 * informacion del mimso a traves de un JSON
	 * 
	 * @param userDTO Objeto usuario generado del JSON
	 * @return <strong>Response</strong> Devuelve una respuesta dependiendo del
	 *         estado resultante del registro:
	 *         <ul>
	 *         <li>200 - OK</li>
	 *         <li>401 - Forbidden</li>
	 *         </ul>
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/register")
	public Response register(UserDTO userDTO) throws WebApplicationException {
		DeuLogger.logger.info("Register petition for User " + userDTO.getUsername());

		try{
			authService.register(userDTO);
		}catch (AuthException e){
			DeuLogger.logger.warn("Error adding user.");
			throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
		}


		return Response.status(200).build();
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

	@GET
	@Path("delete/{username}/{password}")
	public Response delete(
			@PathParam("username") String username,
			@PathParam("password") String password
	) throws SQLException {
		DeuLogger.logger.info("User delete petition for User " + username);
		User user = userDAO.get(username);

		if (user == null) {
			DeuLogger.logger.warn("User " + username + " not found in DB while deleting");
			return Response.status(401).build();
		}

		if(!user.checkPassword(password)) {
			DeuLogger.logger.warn("Wrong user/pass combination for " + username + "  while deleting");
			return Response.status(401).build();
		}

		userDAO.delete(username);
		return Response.status(200).build();

	}


}
