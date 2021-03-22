package com.deusto.deustock.dataminer;

import com.deusto.deustock.data.SocialNetworkMessage;
import com.deusto.deustock.dataminer.cleaner.SocialTextCleaner;
import com.deusto.deustock.dataminer.features.SentimentAnalyzer;
import com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayFactory;
import com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import com.deusto.deustock.dataminer.gateway.socialnetworks.exceptions.NoGatewayTypeException;


import java.util.List;

public class Extractor {

    private SocialNetworkAPIGateway gateway;

    private SocialNetworkQueryData queryData = new SocialNetworkQueryData();

    public Extractor(){

    }

    public void setGateway(SocialNetworkGatewayEnum type) throws NoGatewayTypeException {
        gateway = SocialNetworkGatewayFactory.getInstance().create(type);
    }

    public void setQueryData(SocialNetworkQueryData queryData){
        this.queryData = queryData;
    }

    public void setSearchQuery(String searchQuery){
        this.queryData.setSearchQuery(searchQuery);
    }

    public void setNMessages(int nMessages){
        this.queryData.setNMessages(nMessages);
    }

    public double getSentimentTendency() throws NoGatewayTypeException {
        // TODO Log not valid
        if(gateway == null) {
            throw new NoGatewayTypeException("There is no Gateway set");
        }

        List<SocialNetworkMessage> messages = gateway.getMessageList(this.queryData);
        System.out.println("Hemos obtenido " + messages.size() + " tweets");

        SocialTextCleaner.clean(messages);
        SentimentAnalyzer.analyze(messages);

        return SentimentAnalyzer.getSentimentTendency(messages);
    }

}
