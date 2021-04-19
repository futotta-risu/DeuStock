package es.deusto.deustock.resources.user;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.log.DeuLogger;

/**
 * Clase que contiene los metodos REST asociados a la clase Usuario
 * 
 * @author landersanmillan 
 * @see UserDAO
 */
@Path("/users")
public class UserResource {

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
	public Response login(@PathParam("username") String username, @PathParam("password") String password) {
		DeuLogger.logger.info("Login petition for User " + username);

		User user = UserDAO.getInstance().getUser(username);
		if(user == null) {
			DeuLogger.logger.warn("User not in DB");
			return Response.status(401).build();
		}

		if(!user.checkPassword(password)) {
			DeuLogger.logger.warn("Incorrect password for user " + username);
			return Response.status(401).build();
		}

		return Response
				.status(Response.Status.OK)
				.entity(user)
				.build();

	}

	/**
	 * Metodo que permite registrar un nuevo usuario en la BD, se recibe la
	 * informacion del mimso a traves de un JSON
	 * 
	 * @param user Objeto usuario generado del JSON
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
	public Response register(User user) {
		DeuLogger.logger.info("Register petition for User " + user.getUsername());

		if ( UserDAO.getInstance().getUser(user.getUsername()) != null) {
			DeuLogger.logger.warn("Cannot register User. User " + user.getUsername() + " alredy in DB");
			return Response.status(401).build();
		}

		UserDAO.getInstance().storeUser(user);
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
	@Path("/delete/{username}/{password}")
	public Response delete(@PathParam("username") String username, @PathParam("password") String password) {
		DeuLogger.logger.info("User delete petition for User " + username);
		User user = UserDAO.getInstance().getUser(username);

		if (user == null) {
			DeuLogger.logger.warn("User " + username + " not found in DB while deleting");
			return Response.status(401).build();
		}

		if(!user.checkPassword(password)) {
			DeuLogger.logger.warn("Wrong user/pass combination for " + username + "  while deleting");
			return Response.status(401).build();
		}

		UserDAO.getInstance().deleteUser(username);
		return Response.status(200).build();

	}


}
