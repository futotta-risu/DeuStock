package es.deusto.deustock.resources;

import static org.junit.Assert.*;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.Main;
import es.deusto.deustock.dao.UserDAO;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.deusto.deustock.data.User;

import java.util.Date;

/**
 * Testea los metodos REST relacionados con el usuario
 * 
 * @author landersanmillan
 */
/*
public class UserResourceTest {

	
    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp()  {
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

	public void testRegister() {
	
		String jsonUser = "{\"birthDate\": \"1970-01-15T06:56:08Z[UTC]\",\"country\": \"SPAIN\",\"description\": \"TestAboutMe\",\"fullName\": \"TestFullName\",\"password\": \"TestPass\",\"username\": \"TestUser\"}";
		
		Response response = target.path("users").path("register").request("application/json").post(Entity.json(jsonUser));
		assertEquals(Response.status(200).build().getStatus(), response.getStatus());

		Response responseCopy = target.path("users").path("register").request("application/json").post(Entity.json(jsonUser));
		assertEquals(Response.status(401).build().getStatus(), responseCopy.getStatus());
	}
	
	/**
	 *  Tests user login

	public void testLogin() {
		User user2 = new User("UserResourceTest1", "UserResourceTest1", "fullName3", new Date(1234567890), "country2", "description2");
		UserDAO.getInstance().storeUser(user2);

		Response response = target
				.path("users").path("login")
				.path("UserResourceTest1").path("UserResourceTest1").request(MediaType.APPLICATION_JSON).get();
		User user = response.readEntity(User.class);

        assertEquals("fullName3", user.getFullName());
	}
	
	/**
	 *  Tests user delete

	public void testDelete() {
		Response response = target
				.path("users").path("delete")
				.path("TestUser").path("TestPass")
				.request().get();
		assertEquals(Response.status(200).build().getStatus(), response.getStatus());
		
		Response responseDelete = target
				.path("users").path("delete")
				.path("TestUser").path("TestPass")
				.request().get();
		assertEquals(Response.status(401).build().getStatus(), responseDelete.getStatus());
	}

}
*/