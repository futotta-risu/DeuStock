package com.deusto.deustock.gateway.socialnetworks;

import com.deusto.deustock.data.SocialNetworkMessage;
import com.deusto.deustock.gateway.socialnetworks.TwitterGateway;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TwitterGatewayTest {

    @Test
    @DisplayName("Twitter API get Tweets")
    public void testGetMessageList() {
        TwitterGateway tgw = TwitterGateway.getInstance();
        List<SocialNetworkMessage> msgs = tgw.getMessageList("\"BTC\"");
        assertEquals("Regular multiplication should work", msgs.size(),20);
    }

}
