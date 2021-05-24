package es.deusto.deustock.resources.auth;

import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.Required;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AuthResourceCapacityIT extends JerseyTest {

    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
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
    @PerfTest(invocations = 1000, threads = 10)
    @Required(average = 200, max = 3000, percentile95=1000)
    public void testRegisterCapacity(){
        // Given
        UserDTO user = new UserDTO();

        user.setUsername("TestUserPF_"+ System.currentTimeMillis() + "_" + new Random().nextInt());
        user.setPassword("TestPass");

        // When
        Response response = this.target()
                .path("auth/register")
                .request("application/json")
                .post(Entity.json(user));
        // Then
        assertEquals(200, response.getStatus());

        // After
    }

    @Test
    @PerfTest(invocations = 5000, threads = 10)
    @Required(average = 200, max = 3000, percentile95=1000)
    public void testLoginCapacity() throws SQLException {
        // Given
        String name = "TUPF_"+ System.currentTimeMillis() + "_" + new Random().nextInt();
        User user = new User(name, "TestPass");
        UserDAO.getInstance().store(user);

        // When
        Response response = this.target()
                .path("auth/login").path(name).path("TestPass")
                .request("application/json")
                .get();
        // Then
        assertEquals(200, response.getStatus());

        // After
    }
}
