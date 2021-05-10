package es.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.util.file.DSJSONUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TwitterGatewayTest {

    @Test
    @DisplayName("Twitter API get Tweets")
    void testGetMessageList() {
        SocialNetworkQueryData queryData = new SocialNetworkQueryData();
        queryData.setSearchQuery("\"BTC\"");
        assertTrue(TwitterGateway.getInstance().getMessageList(queryData).size()>0);
    }

    @Test
    @DisplayName("Get Json Configurations")
    void getConfiguration() {
        String path = "data/secret_keys/test_secret_twitter_keys.json";
        JSONObject configuration = null;
        try {
            configuration = DSJSONUtils.readFile(path);
        } catch (IOException | ParseException e) {
            fail("Unable to read file");
        }

        assertEquals("TestConsumerKey", configuration.get("ConsumerKey"));
        assertEquals("TestConsumerSecret", configuration.get("ConsumerSecret"));
        assertEquals("TestAccessToken", configuration.get("AccessToken"));
        assertEquals("TestAccessTokenSecret", configuration.get("AccessTokenSecret"));

    }

    @Test
    void testCannotGetMessageListOnNullQueryData(){
        assertThrows(
                NullPointerException.class,
                () -> TwitterGateway.getInstance().getMessageList(null)
        );
    }
}
