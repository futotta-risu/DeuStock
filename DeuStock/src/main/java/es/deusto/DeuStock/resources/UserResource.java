package es.deusto.deustock.resources;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import es.deusto.deustock.app.dao.UserDAO;
import es.deusto.deustock.app.data.User;

@Path("/users")
public class UserResource {
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login/{username}/{password}")
    public User login(@PathParam("username") String username,@PathParam("password") String password) {
    	if(UserDAO.getUser(username) != null) {
    		if(UserDAO.checkPassword(username, password)) {
    			return(UserDAO.getUser(username));
    		}
    	}
    	return null; 
    }
    
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(User user) {
    	if(UserDAO.getUser(user.getUsername()) == null) {
    		UserDAO.storeUser(user);
    		return Response.status(200).build();
    	}else {
    		return Response.status(401).build();
    	}
    }


}