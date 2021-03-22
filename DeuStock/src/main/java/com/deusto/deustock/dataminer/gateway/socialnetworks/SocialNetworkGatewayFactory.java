package com.deusto.deustock.dataminer.gateway.socialnetworks;

import com.deusto.deustock.dataminer.gateway.socialnetworks.exceptions.NoGatewayTypeException;
import com.deusto.deustock.dataminer.gateway.socialnetworks.gateways.TwitterGateway;

public class SocialNetworkGatewayFactory {

    private static SocialNetworkGatewayFactory instance = null;

    private SocialNetworkGatewayFactory(){

    }

    public static SocialNetworkGatewayFactory getInstance(){
        if(instance == null)
            instance = new SocialNetworkGatewayFactory();

        return instance;
    }

    public SocialNetworkAPIGateway create(SocialNetworkGatewayEnum type) throws NoGatewayTypeException {
        switch(type){
            case Twitter:
                return  TwitterGateway.getInstance();

            default:
                throw new NoGatewayTypeException("The type " + type + " was not found in the factory.");
        }
    }


}
