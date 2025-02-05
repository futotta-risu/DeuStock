package es.deusto.deustock.resources.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.resources.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Erik B. Terres
 */
@Tag("server-resource")
class UserDetailIT extends JerseyTest {

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
    @DisplayName("Test get username returns 200")
    void testGetUsernameReturns200() throws SQLException {
        // Given
        User user = new User("TestUserReturn200", "TestPass");
        UserDAO.getInstance().store(user);

        // When
        Response response = target("tpuser")
                .path("TestUserReturn200")
                .request().get();

        // Then
        assertEquals(200, response.getStatus());

        // After
        UserDAO.getInstance().delete(user.getUsername());
    }

    @Test
    @DisplayName("Test get username returns correct user")
    void testGetUsernameReturnsUser() throws SQLException {
        // Given
        User user = new User("TestUserReturnsUser", "TestPass");
        UserDAO.getInstance().store(user);

        // When
        Response response = this.target("tpuser")
                .path("TestUserReturnsUser")
                .request()
                .get();

        UserDTO returnUser = response.readEntity(UserDTO.class);

        // Then
        assertEquals(user.getUsername(), returnUser.getUsername());

        // After
        UserDAO.getInstance().delete(user.getUsername());
    }

    @Test
    @DisplayName("Test get username returns status 401 on non existent user")
    void testGetUsernameReturnsStatus401OnNonExistingUser(){
        Response response = target("tpuser")
                .path("UserDetailTest401OnNonExistentUser")
                .request().get();

        assertEquals(401, response.getStatus());
    }
}
