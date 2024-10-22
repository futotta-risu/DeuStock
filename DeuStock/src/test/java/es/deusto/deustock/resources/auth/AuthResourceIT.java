package es.deusto.deustock.resources.auth;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.deusto.deustock.data.User;
import es.deusto.deustock.dao.UserDAO;

import es.deusto.deustock.data.dto.UserDTO;
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
class AuthResourceIT extends JerseyTest{

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
		return new ResourceConfig(AuthResource.class);
	}

	@Test
	@DisplayName("Test Register returns 200")
	void testRegisterReturns200() throws SQLException {
		// Given
		UserDTO user = new UserDTO();
		user.setUsername("ResourceRegisterReturns200");
		user.setPassword("TestPass");

		// When
		Response response = this.target()
				.path("auth/register")
				.request("application/json")
				.post(Entity.json(user));

		// Then
		assertEquals(200, response.getStatus());

		// After
		UserDAO.getInstance().delete(user.getUsername());
	}

	@Test
	@DisplayName("Test Register saves user")
	void testRegisterSavesUser() throws SQLException {
		// Given
		UserDTO user = new UserDTO();
		user.setUsername("ResourceRegisterSavesUser");
		user.setPassword("TestPass");
		// When
		this.target("auth/register")
				.request("application/json")
				.post(Entity.json(user));

		// Then
		assertNotNull(UserDAO.getInstance().get(user.getUsername()));

		// After
		UserDAO.getInstance().delete(user.getUsername());
	}

	@Test
	@DisplayName("Test Register doesn't save duplicated User")
	void testRegisterCannotSaveDuplicatedUser() throws SQLException {
		//Given
		UserDTO user = new UserDTO();
		user.setUsername("ResourceRegisterDoesNotSaveDuplicated");
		user.setPassword("TestPass");
		User userU = new User("ResourceRegisterDoesNotSaveDuplicated", "TestPass");
		UserDAO.getInstance().store(userU);

		// When
		Response response = this.target("auth/register")
				.request("application/json")
				.post(Entity.json(user));

		// Then
		assertEquals(401, response.getStatus());

		// After
		UserDAO.getInstance().delete(user.getUsername());
	}

    @Test
	@DisplayName("Test Login returns 200 on success")
	void testLoginReturns200OnSuccess() throws SQLException {
		// Given
		UserDTO user = new UserDTO();
		user.setUsername("ResourceLoginReturns200");
		user.setPassword("TestPass");

		User rUser = new User("ResourceLoginReturns200", "TestPass");
		UserDAO.getInstance().store(rUser);

		// When
		Response response = target("auth/login")
				.path("ResourceLoginReturns200")
				.path("TestPass")
				.request(MediaType.APPLICATION_JSON).get();

		String token = response.readEntity(String.class);

		// Then
        assertFalse(token.isBlank());

        // After
		UserDAO.getInstance().delete(user.getUsername());
	}

	@Test
	@DisplayName("Test Login returns 401 with incorrect pass")
	void testLoginReturns401WithIncorrectPass() throws SQLException {
		// Given
		User user = new User("ResourceLoginReturns401WithIncorrectPass", "TestPass");
		UserDAO.getInstance().store(user);
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("TestPassFake");
		userDTO.setUsername("ResourceLoginReturns401WithIncorrectPass");

		// When
		Response response = target("auth/login")
				.path("ResourceLoginReturns401WithIncorrectPass")
				.path("TestPassIncorrect")
				.request(MediaType.APPLICATION_JSON).get();

		// Then
		assertEquals(401, response.getStatus());

		// After
		UserDAO.getInstance().delete(user.getUsername());
	}

	@Test
	@DisplayName("Test Login returns 401 with non existent user")
	void testLoginReturns401WithNonExistentUser() {
		// Given

		// When
		Response response = target("auth/login")
				.path("LoginReturns401WithNonExistentUser")
				.path("TestPass")
				.request(MediaType.APPLICATION_JSON).get();

		// Then
		assertEquals(401, response.getStatus());
	}

}
