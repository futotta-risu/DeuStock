package es.deusto.deustock.dataminer.gateway.socialnetworks;

import es.deusto.deustock.dataminer.gateway.socialnetworks.gateways.TwitterGateway;

/**
 * Factory for the SocialNetworkGateway.
 *
 *  @author Erik B. Terres
 */
public class SocialNetworkGatewayFactory {

    private static SocialNetworkGatewayFactory instance = null;

    private SocialNetworkGatewayFactory(){}

    public static SocialNetworkGatewayFactory getInstance(){
        if(instance == null)
            instance = new SocialNetworkGatewayFactory();

        return instance;
    }

    public SocialNetworkAPIGateway create(SocialNetworkGatewayEnum type){
        return switch (type) {
            case Twitter -> TwitterGateway.getInstance();
        };
    }


}
