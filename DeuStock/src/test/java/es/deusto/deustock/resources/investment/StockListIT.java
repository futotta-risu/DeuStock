package es.deusto.deustock.resources.investment;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.resources.investment.stock.StockListResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
@Tag("server-resource")
public class StockListIT extends JerseyTest {

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
        return new ResourceConfig(StockListResource.class);
    }

    @Test
    @DisplayName("Test get stock list returns status 200")
    public void testGetStockListReturns200(){
        Response response = target("stock/list")
                .path("small")
                .request()
                .get();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test get stock returns stock list")
    public void testGetStockReturnsStockList(){
        Response response = target("stock/list")
                .path("small")
                .request()
                .get();

        ArrayList<DeuStock> stocks = response.readEntity(new GenericType<>() {});

        assertTrue(stocks.size() > 0);
    }

    @Test
    @DisplayName("Test get stock returns empty list on unknown stock list")
    public void testGetStockReturnsEmptyListOnUnknownStockList(){
        Response response = target("stock/list")
                .path("fakeList")
                .request()
                .get();

        assertEquals(401, response.getStatus());
    }


}
