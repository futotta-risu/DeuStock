package es.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import es.deusto.deustock.data.SocialNetworkMessage;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.util.file.DSJSONUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
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

    private final String path ="data/secret_keys/secret_twitter_keys.json";

    private final TwitterFactory twitterF;
    private final TwitterStreamFactory twitterStreamF;

    private static TwitterGateway instance = null;

    private TwitterGateway(){
        JSONObject configuration = null;
        try {
            configuration = getConfiguration();
        } catch (IOException | ParseException e) {
            // TODO Log errors
            this.twitterF = null;
            this.twitterStreamF = null;
            return;
        }

        this.twitterF = new TwitterFactory(getConfigurationBuilder(configuration).build());
        this.twitterStreamF = new TwitterStreamFactory(getConfigurationBuilder(configuration).build());

    }

    private JSONObject getConfiguration() throws IOException, ParseException {
        return DSJSONUtils.readFile(path);
    }

    private ConfigurationBuilder getConfigurationBuilder(JSONObject configuration){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey((String)configuration.get("ConsumerKey"))
                .setOAuthConsumerSecret((String)configuration.get("ConsumerSecret"))
                .setOAuthAccessToken((String)configuration.get("AccessToken"))
                .setOAuthAccessTokenSecret((String)configuration.get("AccessTokenSecret"));
        return cb;
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
