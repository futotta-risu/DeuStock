package es.deusto.deustock.dataminer.gateway.stocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockDataGatewayFactoryTest {

    @Test
    void testGetInstanceNotNull() {
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        assertNotNull(factory);
    }

    @Test
    void testCreateYahooFinance() {
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        StockDataAPIGateway yahooGateway =  factory.create(StockDataGatewayEnum.YahooFinance);
        assertNotNull(yahooGateway);
    }

    @Test
    void testCannotCreateNullGateway(){
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        assertThrows(NullPointerException.class, () -> factory.create(null));
    }
}