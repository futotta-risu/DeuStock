package es.deusto.deustock.resources.help;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.glassfish.jersey.test.TestProperties;
import org.json.JSONObject;

import org.junit.jupiter.api.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
@Tag("server-resource")
class FAQListIT extends JerseyTest {

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
        return new ResourceConfig(FAQList.class);
    }

    @Test
    void testFAQReturns200(){
        Response response = target("help/faq/list").request().get();

        assertEquals(200, response.getStatus());
    }

    @Test
    void testFAQReturnsQuestions()  {
        Response response = target("help/faq/list").request().get();

        JSONObject faq = new JSONObject(response.readEntity(String.class));

        assertTrue(faq.getJSONArray("questions").length() > 0);
    }

}
