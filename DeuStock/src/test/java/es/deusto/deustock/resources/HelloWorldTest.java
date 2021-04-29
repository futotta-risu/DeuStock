package es.deusto.deustock.resources;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Hello World Resource Test Class
 *
 * @author Erik B. Terres
 */
@Tag("server-resource")
public class HelloWorldTest extends JerseyTest {

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
        return new ResourceConfig(HelloWorld.class);
    }


    @Test
    @DisplayName("Test Hello World returns status 200")
    public void testHelloWorldReturnsStatus200(){
        Response response = this.target("helloworld/")
                .request().get();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test Hello World returns 'Hello World'")
    public void testHelloWorldReturnsHelloWorld(){
        Response response = this.target("helloworld/")
                .request().get();

        String result = response.readEntity(String.class);

        assertEquals("Hello World", result);

    }

}
