package com.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import com.deusto.deustock.data.SocialNetworkMessage;
import com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Twitter gateway.
 *
 * Singleton
 *
 * Accessed by getInstance()
 */
public class TwitterGateway implements SocialNetworkAPIGateway {

    private final TwitterFactory twitterF;
    private static TwitterGateway instance = null;

    /**
     * Default number of messages
     */
    private final int nMessageDefault = 20;

    private final String dateDefault = "2018-08-10";

    private TwitterGateway(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("TJajigAGGn78BaTjuQqqE8FBy")
                .setOAuthConsumerSecret("4isIjlN7YbGNBjGQRSD3vy9jXnJTD922cOopG9Sza8MRiEsNoi")
                .setOAuthAccessToken("1014383023876427778-LU4VxNQRjBzz3wGosRgoEcqin3M7KS")
                .setOAuthAccessTokenSecret("cSYW6F5HdUoRR2ejuTKRIskq8hieQX1Bvm6K1aT2kPEnp");
        System.out.println("Configurado twitter");
        this.twitterF = new TwitterFactory(cb.build());
    }

    public static TwitterGateway getInstance() {
        if(instance == null) instance = new TwitterGateway();
        return instance;
    }

    @Override
    public List<SocialNetworkMessage> getMessageList(SocialNetworkQueryData queryData) {
        Twitter twitter = this.twitterF.getInstance();

        Query query = new Query(queryData.getSearchQuery() + " lang:en");
        query.setCount(queryData.getNMessages());
        //query.until(queryData.getFrom());
        query.setLang("en");

        List<SocialNetworkMessage> messages = new LinkedList<>();

        try {
            for (Status status : twitter.search(query).getTweets()) {
                messages.add(
                        new SocialNetworkMessage(status.getUser().getScreenName(), status.getText())
                );
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
