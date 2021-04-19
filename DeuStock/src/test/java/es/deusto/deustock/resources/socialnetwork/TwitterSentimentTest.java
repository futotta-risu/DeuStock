package es.deusto.deustock.resources.socialnetwork;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
@Tag("server-resource")
public class TwitterSentimentTest extends JerseyTest {

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
        return new ResourceConfig(TwitterSentiment.class);
    }

    @Test
    public void testTwitterSentimentReturns200() throws InterruptedException {
        Response response = target("twitter/sentiment")
                .path("BTC")
                .request().get();

        assertEquals(200, response.getStatus());
    }

}
