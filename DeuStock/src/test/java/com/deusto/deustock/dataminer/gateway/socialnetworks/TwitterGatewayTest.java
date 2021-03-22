package com.deusto.deustock.dataminer.gateway.socialnetworks;

import com.deusto.deustock.dataminer.gateway.socialnetworks.gateways.TwitterGateway;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TwitterGatewayTest {

    @Test
    @DisplayName("Twitter API get Tweets")
    public void testGetMessageList() {
        SocialNetworkQueryData queryData = new SocialNetworkQueryData();
        queryData.setSearchQuery("\"BTC\"");
        assertTrue("Cannot get messages from Twitter API", TwitterGateway.getInstance().getMessageList(queryData).size()>0);
    }

}
