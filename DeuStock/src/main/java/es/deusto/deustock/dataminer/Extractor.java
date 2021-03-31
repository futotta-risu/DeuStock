package es.deusto.deustock.dataminer;

import es.deusto.deustock.data.SocialNetworkMessage;
import es.deusto.deustock.dataminer.cleaner.SocialTextCleaner;
import es.deusto.deustock.dataminer.features.SentimentAnalyzer;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayFactory;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;


import java.util.List;

/**
 * Extractor class, used to encapsulate the sentiment extraction process.
 */
public class Extractor {

    /**
     * Gateway for the Extractor
     */
    private final SocialNetworkAPIGateway gateway;


    public Extractor(SocialNetworkGatewayEnum type){
        gateway = SocialNetworkGatewayFactory.getInstance().create(type);
    }


    /**
     * Gets the message list from the gateway, cleans the data and then returns the sentiment tendency.
     *
     * @param queryData SocialNetworkQueryData
     * @return Double value between 0 to 4 representing the average sentiment.
     */
    public double getSentimentTendency(SocialNetworkQueryData queryData) {
        List<SocialNetworkMessage> messages = gateway.getMessageList(queryData);

        SocialTextCleaner.clean(messages);
        SentimentAnalyzer.analyze(messages);

        return SentimentAnalyzer.getSentimentTendency(messages);
    }

}
