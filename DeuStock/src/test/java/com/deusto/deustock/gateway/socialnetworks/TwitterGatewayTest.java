package com.deusto.deustock.gateway.socialnetworks;

import com.deusto.deustock.data.SocialNetworkMessage;
import com.deusto.deustock.gateway.socialnetworks.TwitterGateway;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TwitterGatewayTest {

    @Test
    @DisplayName("Twitter API get Tweets")
    public void testGetMessageList() {
        assertTrue("Cannot get messages from Twitter API", TwitterGateway.getInstance().getMessageList("\"BTC\"").size()>0);
    }

}
