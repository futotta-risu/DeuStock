package com.dekses.jersey.docker.demo;



import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import es.deusto.DeuStock.app.dao.UserDAO;
import es.deusto.DeuStock.app.data.User;

@Path("/users")
public class UserResource {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login/{username}/{password}")
    public User login(@PathParam("username") String username,@PathParam("password") String password) {
    	if(UserDAO.getInstance().getUser(username) != null) {
    		if(UserDAO.getInstance().checkPassword(username, password)) {
    			return(UserDAO.getInstance().getUser(username));
    		}
    	}
    	return null; 
    }
    
   
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response register(User user) {
    	if(UserDAO.getInstance().getUser(user.getUsername()) == null) {
    		UserDAO.getInstance().storeUser(user);
    		return Response.status(200).build();
    	}else {
    		return Response.status(401).build();
    	}
    }
    
    @PUT
    @Path("/delete/{username}/{password}")
    public Response delete(@PathParam("username") String username, @PathParam("password") String password) {
    	if(UserDAO.getInstance().getUser(username) != null) {
    		if(UserDAO.getInstance().checkPassword(username, password)) {
    			UserDAO.getInstance().deleteUser(UserDAO.getInstance().getUser(username));
        		return Response.status(200).build();
    		}else {
        		return Response.status(401).build();
    		}
    	}else {
    		return Response.status(401).build();
    	}
    }


}
