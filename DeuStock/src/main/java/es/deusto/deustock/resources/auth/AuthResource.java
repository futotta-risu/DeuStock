package es.deusto.deustock.resources.auth;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.services.auth.AuthService;
import es.deusto.deustock.services.auth.exceptions.AuthException;
import es.deusto.deustock.services.auth.exceptions.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Funciones REST relativas a la Auth
 * 
 * @author Erik B. Terres
 */
@Path("auth")
public class AuthResource {
	private AuthService authService;

	private final Logger logger = LoggerFactory.getLogger(AuthResource.class);

	public AuthResource(){
		authService = new AuthService();
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
	public Response login(
			@PathParam("username") String username,
			@PathParam("password") String password
	){
		logger.info("Login petition detected");
		System.out.println("T-1");

		try{
			return Response
					.ok(authService.login(username, password))
					.build();
		}catch (LoginException e){
			throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
		}
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
		logger.info("Register petition for User {}", userDTO.getUsername());
		System.out.println("T-2");
		try{
			authService.register(userDTO);
		}catch (AuthException e){
			logger.warn("Error adding user.");
			throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
		}


		return Response.ok().build();
	}

}
