package es.deusto.deustock.resources.auth;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.data.User;
import es.deusto.deustock.dao.UserDAO;

import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.resources.auth.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;


import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.*;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testea los metodos REST relacionados con el usuario
 * 
 * @author landersanmillan
 */
@Tag("server-resource")
public class UserResourceIT extends JerseyTest{

	@BeforeEach
	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	@AfterEach
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Override
	protected Application configure() {
		forceSet(TestProperties.CONTAINER_PORT, "0");
		return new ResourceConfig(UserResource.class);
	}

	@Test
	@DisplayName("Test Register returns 200")
	public void testRegisterReturns200() throws SQLException {
		// Given
		User user = new User("ResourceRegisterReturns200", "TestPass");

		// When
		Response response = this.target("users/register")
				.request("application/json")
				.post(Entity.json(user));

		// Then
		assertEquals(200, response.getStatus());

		// After
		UserDAO.getInstance().deleteUser(user.getUsername());
	}

	@Test
	@DisplayName("Test Register saves user")
	public void testRegisterSavesUser() throws SQLException {
		// Given
		User user = new User("ResourceRegisterSavesUser", "TestPass");

		// When
		this.target("users/register")
				.request("application/json")
				.post(Entity.json(user));

		// Then
		assertNotNull(UserDAO.getInstance().getUser(user.getUsername()));

		// After
		UserDAO.getInstance().deleteUser(user.getUsername());
	}

	@Test
	@DisplayName("Test Register doesn't save duplicated User")
	public void testRegisterCannotSaveDuplicatedUser() throws SQLException {
		//Given
		User user = new User("ResourceRegisterDoesNotSaveDuplicated", "TestPass");
		UserDAO.getInstance().storeUser(user);

		// When
		Response response = this.target("users/register")
				.request("application/json")
				.post(Entity.json(user));

		// Then
		assertEquals(401, response.getStatus());

		// After
		UserDAO.getInstance().deleteUser(user.getUsername());
	}

    @Test
	@DisplayName("Test Login returns 200 on success")
	public void testLoginReturns200OnSuccess() throws SQLException {
		// Given
		User user = new User("ResourceLoginReturns200", "TestPass");
		UserDAO.getInstance().storeUser(user);

		// When
		Response response = target("users/login")
				.path("ResourceLoginReturns200")
				.path("TestPass")
				.request(MediaType.APPLICATION_JSON).get();

		UserDTO userLogin = response.readEntity(UserDTO.class);

		// Then
        assertEquals("ResourceLoginReturns200", userLogin.getUsername());

        // After
		UserDAO.getInstance().deleteUser(user.getUsername());
	}

	@Test
	@DisplayName("Test Login returns 401 with incorrect pass")
	public void testLoginReturns401WithIncorrectPass() throws SQLException {
		// Given
		User user = new User("ResourceLoginReturns401WithIncorrectPass", "TestPass");
		UserDAO.getInstance().storeUser(user);

		// When
		Response response = target("users/login")
				.path("ResourceLoginReturns401WithIncorrectPass")
				.path("TestPassIncorrect")
				.request(MediaType.APPLICATION_JSON).get();

		// Then
		assertEquals(401, response.getStatus());

		// After
		UserDAO.getInstance().deleteUser(user.getUsername());
	}

	@Test
	@DisplayName("Test Login returns 401 with non existent user")
	public void testLoginReturns401WithNonExistentUser() {
		// Given

		// When
		Response response = target("users/login")
				.path("LoginReturns401WithNonExistentUser")
				.path("TestPass")
				.request(MediaType.APPLICATION_JSON).get();

		// Then
		assertEquals(401, response.getStatus());
	}

    @Test
	@DisplayName("Test Delete user returns 200")
	public void testDeleteReturns200() throws SQLException {
		// Given
		User user = new User("ResourceDelete200", "TestPass");
		UserDAO.getInstance().storeUser(user);

		// When
		Response response = target("users")
				.path("delete")
				.path("ResourceDelete200").path("TestPass")
				.request().get();

		// Then
		assertEquals(200, response.getStatus());
	}

	@Test
	@DisplayName("Test Delete returns 401 on non existent user")
	public void testDeleteReturns401OnUnknownUser(){
		// Given

		// When
		Response responseDelete = target("users")
				.path("delete")
				.path("DeleteReturns401OnUnknownUser").path("TestPass")
				.request().get();

		// Then
		assertEquals(401, responseDelete.getStatus());
	}

	@Test
	@DisplayName("Test Delete returns 401 on incorrect Pass")
	public void testDeleteReturns401WithIncorrectPass() throws SQLException {
		// Given
		User user = new User("ResourceDelete401IncorrectPass", "TestPass");
		UserDAO.getInstance().storeUser(user);

		// When
		Response response = target("users")
				.path("delete")
				.path("ResourceDelete401IncorrectPass").path("TestPassIncorrect")
				.request().get();

		// Then
		assertEquals(401, response.getStatus());

		// After
		UserDAO.getInstance().deleteUser(user.getUsername());
	}

}
