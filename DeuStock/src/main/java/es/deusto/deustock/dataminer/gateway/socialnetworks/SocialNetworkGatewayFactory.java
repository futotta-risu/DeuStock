package es.deusto.deustock.dataminer.gateway.socialnetworks;

import es.deusto.deustock.dataminer.gateway.socialnetworks.exceptions.NoGatewayTypeException;
import es.deusto.deustock.dataminer.gateway.socialnetworks.gateways.TwitterGateway;

/**
 * Factory for the SocialNetworkGateway.
 */
public class SocialNetworkGatewayFactory {

    private static SocialNetworkGatewayFactory instance = null;

    private SocialNetworkGatewayFactory(){}

    public static SocialNetworkGatewayFactory getInstance(){
        if(instance == null)
            instance = new SocialNetworkGatewayFactory();

        return instance;
    }

    public SocialNetworkAPIGateway create(SocialNetworkGatewayEnum type) throws NoGatewayTypeException {
        return switch (type) {
            case Twitter -> TwitterGateway.getInstance();
            default -> throw new NoGatewayTypeException("The type " + type + " was not found in the factory.");
        };
    }


}
