package es.deusto.deustock.resources;

import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.Required;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;
import es.deusto.deustock.dao.UserDAO;
import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.UserDTO;
import es.deusto.deustock.resources.auth.AuthResource;
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

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserResourceCapacityIT extends JerseyTest {

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
        return new ResourceConfig(UserResource.class);
    }

    @Test
    @PerfTest(invocations = 5000, threads = 20)
    @Required(average = 100, max = 700, percentile95=300)
    public void testGetUserCapacity() throws SQLException {
        // Given
        String name = "TestUser"+ System.currentTimeMillis() + "_" + new Random().nextInt();
        User user = new User(name, "Test");

        UserDAO.getInstance().store(user);


        // When
        Response response = this.target()
                .path("tpuser").path(name)
                .request("application/json")
                .get();

        // Then
        assertEquals(200, response.getStatus());

    }
}
