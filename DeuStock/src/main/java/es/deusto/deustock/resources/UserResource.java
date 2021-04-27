package es.deusto.deustock.resources;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	 * @param username -> Nombre del usuario
	 * @param password -> Contraseña encriptada asociada a la cuenta
	 * @return <strong>User</strong> -> Objeto de usuario que contiene toda la
	 *         informacion de la cuenta con la que se ha iniciado sesion
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login/{username}/{password}")
	public User login(@PathParam("username") String username, @PathParam("password") String password) {
		User user = UserDAO.getInstance().getUser(username);
		if (user != null) {
			if (user.checkPassword(password)) {
				return user;
			}
		}
		DeuLogger.logger.error("Wrong username or password");
		return null;
	}

	/**
	 * Metodo que permite registrar un nuevo usuario en la BD, se recibe la
	 * informacion del mimso a traves de un JSON
	 * 
	 * @param user Objeto usuario generado del JSON
	 * @return <strong>Response</strong> -> Devuelve una respuesta dependiendo del
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
		if (UserDAO.getInstance().getUser(user.getUsername()) == null) {
			UserDAO.getInstance().storeUser(user);
			return Response.status(200).build();
		} else {
			return Response.status(401).build();
		}
	}

	/**
	 * Metodo que permite eliminar un usuario de la BD, en el path se reciben el
	 * nombre de usuario y la contraseña encriptada en SHA-256
	 * 
	 * @param username -> Nombre del usuario
	 * @param password -> Contraseña encriptada asociada a la cuenta
	 * @return <strong>Response</strong> -> Devuelve una respuesta dependiendo del
	 *         estado resultante del borrado:
	 *         <ul>
	 *         <li>200 - OK</li>
	 *         <li>401 - Forbidden</li>
	 *         </ul>
	 */

	@GET
	@Path("/delete/{username}/{password}")
	public Response delete(@PathParam("username") String username, @PathParam("password") String password) {
		User user = UserDAO.getInstance().getUser(username);
		System.out.println("T1-1");
		if (user != null) {
			System.out.println("T1-2");
			if (user.checkPassword(password)) {
				System.out.println("T1-3");
				UserDAO.getInstance().deleteUser(username);
				System.out.println("T1-4");
				return Response.status(200).build();
			} else return Response.status(401).build();
		} else return Response.status(401).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update")
	public Response update(User user) throws ParseException {
		User toUpdate = UserDAO.getInstance().getUser(user.getUsername());
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
		Date birthDateC = dateFormat.parse(user.getBirthDate().toString());
		System.out.println("TO UPDATE" + toUpdate);
		System.out.println("NEW DATA" + user);
		if(toUpdate!=null){
			toUpdate.setFullName(user.getFullName());
			toUpdate.setBirthDate(user.getBirthDate());
			toUpdate.setDescription(user.getDescription());
			toUpdate.setCountry(user.getCountry());
			UserDAO.getInstance().storeUser(toUpdate);
			return Response.status(200).build();
		}else return Response.status(401).build();
		
	}

}
