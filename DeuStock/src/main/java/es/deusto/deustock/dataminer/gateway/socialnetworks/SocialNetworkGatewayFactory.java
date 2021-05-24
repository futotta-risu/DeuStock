package es.deusto.deustock.dataminer.gateway.socialnetworks;

import es.deusto.deustock.dataminer.gateway.socialnetworks.gateways.RedditGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.gateways.TwitterGateway;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Singleton Factory for the {@link SocialNetworkAPIGateway}.
 *
 * {@link SocialNetworkGatewayFactory#getInstance()}
 *
 * @author Erik B. Terres
 */
public class SocialNetworkGatewayFactory {

    private static SocialNetworkGatewayFactory instance;

    private SocialNetworkGatewayFactory(){}

    public static SocialNetworkGatewayFactory getInstance(){
        if(instance == null)
            instance = new SocialNetworkGatewayFactory();

        return instance;
    }


    /**
     * Returns a {@link SocialNetworkAPIGateway}
     *
     * @param type {@link SocialNetworkGatewayEnum} type for the gateway
     *
     * @return Newly created {@link SocialNetworkAPIGateway}
     */
    public SocialNetworkAPIGateway create(@NotNull SocialNetworkGatewayEnum type){
        return switch (type) {
            case TWITTER -> TwitterGateway.getInstance();
            case REDDIT -> RedditGateway.getInstance();
        };

    }


}
