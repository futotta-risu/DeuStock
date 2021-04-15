package es.deusto.deustock.dataminer;

import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.dataminer.gateway.socialnetworks.gateways.TwitterGateway;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorTest {

    @Test
    void testConstructor(){
        Extractor e = new Extractor(SocialNetworkGatewayEnum.Twitter);
        assertNotNull(e);
    }

    @Test
    void getSentimentTendency() {
        Extractor e = new Extractor(SocialNetworkGatewayEnum.Twitter);
        SocialNetworkQueryData sqd = new SocialNetworkQueryData("BTC");

        double result = e.getSentimentTendency(sqd);

        assertTrue(result >= 0 && result <= 4);
    }
}