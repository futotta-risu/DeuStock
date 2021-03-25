package es.deusto.deustock.dataminer;

import es.deusto.deustock.data.SocialNetworkMessage;
import es.deusto.deustock.dataminer.cleaner.SocialTextCleaner;
import es.deusto.deustock.dataminer.features.SentimentAnalyzer;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayFactory;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.dataminer.gateway.socialnetworks.exceptions.NoGatewayTypeException;


import java.util.List;

/**
 * Extractor class, used to encapsulate the sentiment extraction proccess.
 */
public class Extractor {

    /**
     * Gateway for the Extractor
     */
    private SocialNetworkAPIGateway gateway;


    public Extractor(){}

    /**
     * Setter for the Gateway function. Based on {@link SocialNetworkGatewayEnum}
     * @param  type SocialNetworkGatewayEnum
     * @return this
     * @throws NoGatewayTypeException
     */
    public Extractor setGateway(SocialNetworkGatewayEnum type) throws NoGatewayTypeException {
        gateway = SocialNetworkGatewayFactory.getInstance().create(type);
        return this;
    }

    /**
     * Gets the message list from the gateway, cleans the data and then returns the sentiment tendency.
     *
     * @param queryData SocialNetworkQueryData
     * @return Dobule value between 0 to 4 representing the average sentiment.
     * @throws NoGatewayTypeException
     */
    public double getSentimentTendency(SocialNetworkQueryData queryData) throws NoGatewayTypeException {
        // TODO Log not valid
        if(gateway == null) {
            throw new NoGatewayTypeException("There is no Gateway set");
        }
        List<SocialNetworkMessage> messages = gateway.getMessageList(queryData);
        System.out.println("Hemos obtenido " + messages.size() + " tweets");

        SocialTextCleaner.clean(messages);
        SentimentAnalyzer.analyze(messages);

        return SentimentAnalyzer.getSentimentTendency(messages);
    }

}
