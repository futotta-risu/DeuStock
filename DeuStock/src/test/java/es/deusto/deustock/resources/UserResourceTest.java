package es.deusto.deustock.resources;


import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.data.User;
import es.deusto.deustock.dao.UserDAO;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;


import org.junit.jupiter.api.*;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testea los metodos REST relacionados con el usuario
 * 
 * @author landersanmillan
 */
@Tag("server-resource")
public class UserResourceTest  extends JerseyTest {

	private User user;

	private void resetUser(){
		this.user = new User("TestUser","TestPass")
				.setCountry("SPAIN")
				.setFullName("TestFullName")
				.setDescription("TestAboutMe")
				.setBirthDate(new Date());
	}

	@BeforeEach
	@Override
	public void setUp() throws Exception {
		super.setUp();
		resetUser();
	}

	@AfterEach
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	protected Application configure() {
		return new ResourceConfig(UserResource.class);
	}

	@Test
	@DisplayName("Test Register returns 200")
	public void testRegisterReturns200(){
		Response response = this.target("users/register")
				.request("application/json")
				.post(Entity.json(this.user));

		assertEquals(200, response.getStatus());

		UserDAO.getInstance().deleteUser(this.user.getUsername());
	}

	@Test
	@DisplayName("Test Register saves user")
	public void testRegisterSavesUser(){
		Response response = this.target("users/register")
				.request("application/json")
				.post(Entity.json(user));

		assertTrue(UserDAO.getInstance().getUser(this.user.getUsername()) != null);
		UserDAO.getInstance().deleteUser("TestUser");
	}

	@Test
	@DisplayName("Test Register doesn't save duplicated User")
	public void testRegisterCannotSaveDuplicatedUser() {

		UserDAO.getInstance().storeUser(this.user);
		resetUser();

		Response responseCopy = this.target("users/register")
				.request("application/json")
				.post(Entity.json(user));

		assertEquals(401, responseCopy.getStatus());
		UserDAO.getInstance().deleteUser(this.user.getUsername());
	}

    @Test
	@DisplayName("Test Login returns 200")
	public void testLogin() {
		UserDAO.getInstance().storeUser(this.user);

		Response response = target("users/login")
				.path("TestUser")
				.path("TestPass")
				.request(MediaType.APPLICATION_JSON).get();

		User userLogin = response.readEntity(User.class);

        assertEquals("TestUser", userLogin.getUsername());
		UserDAO.getInstance().deleteUser("TestUser");
	}

	@Test
	@DisplayName("Test Login returns 401 with incorrect pass")
	public void testLoginReturns401WithIncorrectPass() {
		UserDAO.getInstance().storeUser(this.user);

		Response response = target("users/login")
				.path("TestUser")
				.path("TestPassIncorrect")
				.request(MediaType.APPLICATION_JSON).get();

		User userLogin = response.readEntity(User.class);

		assertNull(userLogin);
		UserDAO.getInstance().deleteUser("TestUser");
	}

	@Test
	@DisplayName("Test Login returns 401 with non existent user")
	public void testLoginReturns401WithNonExistentUser() {
		Response response = target("users/login")
				.path("TestUser")
				.path("TestPass")
				.request(MediaType.APPLICATION_JSON).get();

		User userLogin = response.readEntity(User.class);

		assertNull(userLogin);
	}

    @Test
	@DisplayName("Test Delete user in DB")
	public void testDelete() {
		UserDAO.getInstance().storeUser(this.user);

		Response response = target("users")
				.path("delete")
				.path("TestUser").path("TestPass")
				.request().get();

		assertEquals(200, response.getStatus());
	}

	@Test
	@DisplayName("Test Delete fails on non existent user")
	public void testCannotDeleteUnknownUser(){
		Response responseDelete = target("users")
				.path("delete")
				.path("TestUser").path("TestPass")
				.request().get();

		assertEquals(401, responseDelete.getStatus());
	}

	@Test
	@DisplayName("Test Delete fails on incorrect Pass")
	public void testCannotDeleteWithIncorrectPass(){
		UserDAO.getInstance().storeUser(this.user);

		Response response = target("users")
				.path("delete")
				.path("TestUser").path("TestPassIncorrect")
				.request().get();

		assertEquals(401, response.getStatus());
	}

}
