package es.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import es.deusto.deustock.data.SocialNetworkMessage;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
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
    private final TwitterStreamFactory twitterStreamF;

    private static TwitterGateway instance = null;

    private TwitterGateway(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("TJajigAGGn78BaTjuQqqE8FBy")
                .setOAuthConsumerSecret("4isIjlN7YbGNBjGQRSD3vy9jXnJTD922cOopG9Sza8MRiEsNoi")
                .setOAuthAccessToken("1014383023876427778-LU4VxNQRjBzz3wGosRgoEcqin3M7KS")
                .setOAuthAccessTokenSecret("cSYW6F5HdUoRR2ejuTKRIskq8hieQX1Bvm6K1aT2kPEnp");

        ConfigurationBuilder cb1 = new ConfigurationBuilder();
        cb1.setDebugEnabled(true)
                .setOAuthConsumerKey("TJajigAGGn78BaTjuQqqE8FBy")
                .setOAuthConsumerSecret("4isIjlN7YbGNBjGQRSD3vy9jXnJTD922cOopG9Sza8MRiEsNoi")
                .setOAuthAccessToken("1014383023876427778-LU4VxNQRjBzz3wGosRgoEcqin3M7KS")
                .setOAuthAccessTokenSecret("cSYW6F5HdUoRR2ejuTKRIskq8hieQX1Bvm6K1aT2kPEnp");
        this.twitterF = new TwitterFactory(cb.build());
        this.twitterStreamF = new TwitterStreamFactory(cb1.build());
    }

    public static TwitterGateway getInstance() {
        if(instance == null) instance = new TwitterGateway();
        return instance;
    }

    @Override
    public List<SocialNetworkMessage> getMessageList(SocialNetworkQueryData queryData) {
        Twitter twitter = this.twitterF.getInstance();

        Query query = new Query(queryData.getSearchQuery()+" -filter:retweets");
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

    // Only for future purpose
    @Deprecated
    public void getStreamMessageList(SocialNetworkQueryData queryData){

        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
            @Override
            public void onScrubGeo(long userId, long upToStatusId) {}
            @Override
            public void onStallWarning(StallWarning warning) {}
            @Override
            public void onException(Exception ex) {}
        };
        TwitterStream twitterStream = this.twitterStreamF.getInstance();
        twitterStream.addListener(listener);
        twitterStream.sample();
        twitterStream.filter(new FilterQuery(queryData.getSearchQuery()+" -filter:retweets").language("en"));
    }
}
