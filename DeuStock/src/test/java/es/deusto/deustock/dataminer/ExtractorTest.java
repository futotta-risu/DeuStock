package es.deusto.deustock.dataminer;

import es.deusto.deustock.dataminer.features.SentimentExtractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtractorTest {

    @Test
    void testConstructor(){
        SentimentExtractor e = new SentimentExtractor(SocialNetworkGatewayEnum.Twitter);
        assertNotNull(e);
    }

    @Test
    void getSentimentTendency() throws InterruptedException {
        SentimentExtractor e = new SentimentExtractor(SocialNetworkGatewayEnum.Twitter);
        SocialNetworkQueryData sqd = new SocialNetworkQueryData("BTC");

        double result = e.getSentimentTendency(sqd);

        assertTrue(result >= 0 && result <= 4);
    }
}