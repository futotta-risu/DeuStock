package es.deusto.deustock.resources.user;

import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Erik B. Terres
 */
@Tag("server-resource")
public class UserDetailTest extends JerseyTest {

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

        if(UserDAO.getInstance().getUser("TestUser")!= null) {
            UserDAO.getInstance().deleteUser("TestUser");
        }

    }

    @Override
    protected Application configure() {
        forceSet(TestProperties.CONTAINER_PORT, "0");
        return new ResourceConfig(UserDetail.class);
    }


    @Test
    @DisplayName("Test get username returns 200")
    public void testGetUsernameReturns200(){
        UserDAO.getInstance().storeUser(user);
        Response response = target("user")
                .path("TestUser")
                .request().get();

        assertEquals(200, response.getStatus());
        UserDAO.getInstance().deleteUser(this.user.getUsername());
    }

    @Test
    @DisplayName("Test get username returns correct user")
    public void testGetUsernameReturnsUser(){
        UserDAO.getInstance().storeUser(this.user);
        resetUser();

        Response response = this.target("user")
                .path("TestUser")
                .request()
                .get();

        UserDTO returnUser = response.readEntity(UserDTO.class);

        assertEquals(this.user.getUsername(), returnUser.getUsername());
        UserDAO.getInstance().deleteUser(this.user.getUsername());
    }

    @Test
    @DisplayName("Test get username returns status 401 on non existent user")
    public void testGetUsernameReturnsStatus401OnNonExistingUser(){
        UserDAO.getInstance().storeUser(user);

        Response response = target("user")
                .path("TestUserFake")
                .request().get();

        assertEquals(401, response.getStatus());

        UserDAO.getInstance().deleteUser(this.user.getUsername());
    }


}
