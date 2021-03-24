package com.dekses.jersey.docker.demo;


import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import es.deusto.DeuStock.app.dao.UserDAO;
import es.deusto.DeuStock.app.data.User;






@Path("/users")
public class UserResource {
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public User login(String username, String password) {
    	if(UserDAO.getUser(username) != null) {
    		if(UserDAO.checkPassword(username, password)) {
    			return(UserDAO.getUser(username));
    		}
    	}
    	return null; 
    }
    
   
    @POST
    @Consumes("text/plain")
    @Path("/register")
    public boolean register(String username, String password, String fullName, Date birthDate, String country, String description) {
    	if(UserDAO.getUser(username) == null) {
    		UserDAO.storeUser(new User(username, password, fullName, birthDate, country, description));
    		return true;
    	}else {
    		return false;
    	}
    }


}
