package com.dekses.jersey.docker.demo;

import static org.junit.Assert.*;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.DeuStock.app.data.User;

/**
 * Testea los metodos REST relacionados con el usuario
 * 
 * @author landersanmillan
 */
public class UserResourceTest {

	
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target(Main.BASE_URI);
    }
    
	@SuppressWarnings("deprecation")
	@After
	public void tearDown() throws Exception {
        server.stop();
	}

	/**
	 *  Tests user register
	 */
	@Test
	public void testRegister() {
	
		String jsonUser = "{\"birthDate\": \"1970-01-15T06:56:08Z[UTC]\",\"country\": \"SPAIN\",\"description\": \"TestAboutMe\",\"fullName\": \"TestFullName\",\"password\": \"TestPass\",\"username\": \"TestUser\"}";
		
		Response response = target.path("users").path("register").request().post(Entity.json(jsonUser));
		assertEquals(Response.status(200).build().getStatus(), response.getStatus());
		
		Response responseCopy = target.path("users").path("register").request().post(Entity.json(jsonUser));
		assertEquals(Response.status(401).build().getStatus(), responseCopy.getStatus());
	}
	
	/**
	 *  Tests user login
	 */
	@Test
	public void testLogin() {
        
		User response = target.path("users").path("login").path("TestUser").path("TestPass").request().get(User.class);
		
        assertEquals("TestFullName", response.getFullName());
		assertEquals("TestAboutMe", response.getDescription());
	}
	
	/**
	 *  Tests user delete
	 */
	@Test
	public void testDelete() {
		Response response = target.path("users").path("delete").path("TestUser").path("TestPass").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON));
		assertEquals(Response.status(200).build().getStatus(), response.getStatus());
		
		Response responseDelete = target.path("users").path("delete").path("TestUser").path("TestPass").request().put(Entity.entity("{}", MediaType.APPLICATION_JSON));
		assertEquals(Response.status(401).build().getStatus(), responseDelete.getStatus());
	}

}
