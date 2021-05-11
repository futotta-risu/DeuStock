package es.deusto.deustock.dataminer.features;

import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("sentiment")
class SentimentExtractorIT {

    @Test
    @DisplayName("Test the constructor does not return null")
    void testConstructor(){
        SentimentExtractor e = new SentimentExtractor(SocialNetworkGatewayEnum.Twitter);
        assertNotNull(e);
    }

    @Test
    @DisplayName("Tests the Get Sentiment Tendency from the Extractor")
    void getSentimentTendency() throws InterruptedException {
        SentimentExtractor e = new SentimentExtractor(SocialNetworkGatewayEnum.Twitter);
        SocialNetworkQueryData sqd = new SocialNetworkQueryData("BTC");

        double result = e.getSentimentTendency(sqd);

        assertTrue(result >= 0 && result <= 4);
    }
}