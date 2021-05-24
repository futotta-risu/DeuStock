package es.deusto.deustock.resources.help;

import com.github.javatlacati.contiperf.PerfTest;
import com.github.javatlacati.contiperf.Required;
import com.github.javatlacati.contiperf.junit.ContiPerfRule;
import es.deusto.deustock.resources.auth.AuthResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class FAQListCapacityIT extends JerseyTest {

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
        return new ResourceConfig(FAQList.class);
    }

    @Test
    @PerfTest(invocations = 1000, threads = 10)
    @Required(average = 200, max = 3000, percentile95=1000)
    public void testFAQCapacity(){
        Response response = target("help/faq/list").request().get();

        assertEquals(200, response.getStatus());
    }
}
