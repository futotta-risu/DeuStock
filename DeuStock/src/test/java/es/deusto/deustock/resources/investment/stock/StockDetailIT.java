package es.deusto.deustock.resources.investment.stock;

import es.deusto.deustock.data.DeuStock;
import es.deusto.deustock.resources.investment.stock.StockDetailResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.glassfish.jersey.test.TestProperties;
import org.junit.jupiter.api.*;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erik B. Terres
 */
@Tag("server-integration-resource")
class StockDetailIT extends JerseyTest {
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
        return new ResourceConfig(StockDetailResource.class);
    }

    @Test
    @DisplayName("Test get stock returns status 200")
    void testGetStockReturns200(){
        Response response = target("stock/detail")
                .path("BB")
                .path("DAILY")
                .request().get();

        assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Test get stock returns correct Stock")
    void testGetStockReturnsCorrectStock(){
        Response response = target("stock/detail")
                .path("BB")
                .path("DAILY")
                .request().get();

        DeuStock stock = response.readEntity(DeuStock.class);

        assertEquals("BB", stock.getAcronym());
        assertTrue(stock.getPrice() > 0);
        assertFalse(stock.getHistory().isEmpty());
    }

    @Test
    @DisplayName("Test get stock returns 401 on non existent Stock")
    void testGetStockReturns401OnNonExistentStock(){
        Response response = target("stock/detail")
                .path("TXFG")
                .path("DAILY")
                .request().get();

        assertEquals(401, response.getStatus());
    }

    @Test
    @DisplayName("Test get stock returns 401 on non existent interval")
    void testGetStockReturns401OnNonExistentInterval(){
        Response response = target("stock/detail")
                .path("BB")
                .path("NonExistent")
                .request().get();

        assertEquals(401, response.getStatus());
    }

}
