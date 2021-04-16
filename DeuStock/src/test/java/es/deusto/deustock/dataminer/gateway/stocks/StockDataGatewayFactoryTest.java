package es.deusto.deustock.dataminer.gateway.stocks;

import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockDataGatewayFactoryTest {

    @Test
    void tstGetInstanceNotNull() {
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        assertNotNull(factory);
    }

    @Test
    void testCreateYahooFinance() {
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        StockDataAPIGateway  yahooGateway =  factory.create(StockDataGatewayEnum.YahooFinance);
        assertNotNull(yahooGateway);
    }

    @Test
    void testCannotCreateNullGateway(){
        StockDataGatewayFactory factory = StockDataGatewayFactory.getInstance();
        assertThrows(NullPointerException.class, () -> factory.create(null));
    }
}