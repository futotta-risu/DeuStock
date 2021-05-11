package es.deusto.deustock.dataminer.gateway.stocks;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("investment")
class StockDataGatewayFactoryTest {

    @Test
    void testGetInstanceNotNull() {
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        assertNotNull(factory);
    }

    @Test
    void testCreateYahooFinance() {
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        StockDataAPIGateway  yahooGateway =  factory.create(StockDataGatewayEnum.YAHOO_FINANCE);
        assertNotNull(yahooGateway);
    }

    @Test
    void testCannotCreateNullGateway(){
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        assertThrows(NullPointerException.class, () -> factory.create(null));
    }
}