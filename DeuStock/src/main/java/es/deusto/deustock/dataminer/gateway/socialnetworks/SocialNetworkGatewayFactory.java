package es.deusto.deustock.dataminer.gateway.socialnetworks;

import es.deusto.deustock.dataminer.gateway.socialnetworks.gateways.TwitterGateway;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Factory for the SocialNetworkGateway.
 *
 *  @author Erik B. Terres
 */
public class SocialNetworkGatewayFactory {

    private static SocialNetworkGatewayFactory instance;

    private SocialNetworkGatewayFactory(){}

    public static SocialNetworkGatewayFactory getInstance(){
        if(instance == null)
            instance = new SocialNetworkGatewayFactory();

        return instance;
    }

    @NotNull
    public SocialNetworkAPIGateway create(SocialNetworkGatewayEnum type){
        Objects.requireNonNull(type);
        return switch (type) {
            case Twitter -> TwitterGateway.getInstance();
        };


    }


}
