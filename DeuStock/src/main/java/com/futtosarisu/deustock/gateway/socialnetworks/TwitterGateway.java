package com.futtosarisu.deustock.gateway.socialnetworks;

import com.futtosarisu.deustock.data.SocialNetworkMessage;
import com.futtosarisu.deustock.dataminer.cleaner.SocialTextCleaner;
import com.futtosarisu.deustock.gateway.SocialNetworkAPIGateway;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Date;
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

    private TwitterFactory twitterF;
    private static TwitterGateway instance = null;

    /**
     * Default number of messages
     */
    private int nMessageDefault = 20;

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

    public Twitter getTwitter(){
        return this.twitterF.getInstance();
    }


    @Override
    public List<SocialNetworkMessage> getMessageList(String txt) {

        SocialNetworkMessage dummyMessage1 = new SocialNetworkMessage("dummy text 1");
        SocialNetworkMessage dummyMessage2 = new SocialNetworkMessage("dummy text 2");
        LinkedList<SocialNetworkMessage> messages = new  LinkedList<SocialNetworkMessage>();

        messages.add(dummyMessage1);
        messages.add(dummyMessage2);
        return getMessageList("\"BTC\"", 20, new Date());
    }

    @Override
    public List<SocialNetworkMessage> getMessageList(String txt, int nMessage) {
        return null;
    }

    @Override
    public List<SocialNetworkMessage> getMessageList(String txt, int nMessage, Date from) {
        Twitter twitter = this.twitterF.getInstance();

        Query query = new Query(txt + " lang:en");
        query.setCount(nMessage);
        //query.until("2018-08-10"); // TODO Date to String method
        //query.setResultType(Query.POPULAR);

        QueryResult result = null;
        List<SocialNetworkMessage> messages = new LinkedList<SocialNetworkMessage>();
        try {
            result = twitter.search(query);
            System.out.println("Hemos recibido " + result.getTweets().size());
            for (Status status : result.getTweets()) {
                System.out.println("@" + status.getUser().getScreenName() +  ":" + status.getCreatedAt()+"\n" + SocialTextCleaner.clean(status.getText()) +    "\n \n");

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
