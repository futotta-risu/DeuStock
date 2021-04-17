package es.deusto.deustock.dataminer;

import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

@Tag("sentiment")
public class ExtractorTest {

    @Test
    void testConstructor(){
        Extractor e = new Extractor(SocialNetworkGatewayEnum.Twitter);
        assertNotNull(e);
    }

    @Test
    void getSentimentTendency() throws InterruptedException {
        Extractor e = new Extractor(SocialNetworkGatewayEnum.Twitter);
        SocialNetworkQueryData sqd = new SocialNetworkQueryData("BTC");

        double result = e.getSentimentTendency(sqd);

        assertTrue(result >= 0 && result <= 4);
    }
}