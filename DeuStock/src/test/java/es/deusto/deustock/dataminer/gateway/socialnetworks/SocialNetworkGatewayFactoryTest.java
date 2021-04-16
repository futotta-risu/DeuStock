package es.deusto.deustock.dataminer.gateway.socialnetworks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SocialNetworkGatewayFactoryTest {

    @Test
    void testGetInstanceNotNull() {
        SocialNetworkGatewayFactory factory = SocialNetworkGatewayFactory.getInstance();
        assertNotNull(factory);
    }

    @Test
    void testCreateTwitterGateway() {
        SocialNetworkGatewayFactory factory = SocialNetworkGatewayFactory.getInstance();
        SocialNetworkAPIGateway  twitterGateway =  factory.create(SocialNetworkGatewayEnum.Twitter);
        assertNotNull(twitterGateway);
    }
}