package com.dekses.jersey.docker.demo;

import static org.junit.Assert.*;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.Main;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.deustock.data.User;


public class UserResourceTest {

	
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp()  {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        //c.getConfiguration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);
    }
    
	@SuppressWarnings("deprecation")
	@After
	public void tearDown() throws Exception {
        server.stop();
	}

	
	@Test
	public void testRegister() {
	
		String jsonUser = "{\"birthDate\": \"1970-01-15T06:56:08Z[UTC]\",\"country\": \"SPAIN\",\"description\": \"TestAboutMe\",\"fullName\": \"TestFullName\",\"password\": \"TestPass\",\"username\": \"TestUser\"}";
		
		Response response = target.path("users").path("register").request().post(Entity.json(jsonUser));
		assertEquals(Response.status(200).build().getStatus(), response.getStatus());
		
		Response responseCopy = target.path("users").path("register").request().post(Entity.json(jsonUser));
		assertEquals(Response.status(401).build().getStatus(), responseCopy.getStatus());
	}
	
	
	@Test
	public void testLogin() {
        
		User response = target
				.path("users").path("login")
				.path("TestUser").path("TestPass").request(MediaType.APPLICATION_JSON).get(User.class);
		
        assertEquals("TestFullName", response.getFullName());
		assertEquals("TestAboutMe", response.getDescription());
	}
	
	

}
