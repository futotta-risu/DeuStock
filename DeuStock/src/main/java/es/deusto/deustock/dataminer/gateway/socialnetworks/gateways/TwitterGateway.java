package es.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import es.deusto.deustock.data.SocialNetworkMessage;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.log.DeuLogger;
import es.deusto.deustock.util.file.DSJSONUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Twitter gateway.
 *
 * @author Erik B. Terres
 */
public class TwitterGateway implements SocialNetworkAPIGateway {

    private static final String PATH ="data/secret_keys/secret_twitter_keys.json";

    private final TwitterFactory twitterF;

    private static TwitterGateway instance = null;

    private TwitterGateway(){
        JSONObject config = null;
        try {
            config = getConfiguration();
        } catch (IOException | ParseException e) {
            DeuLogger.logger.error("Could not load twitter configuration.");
            this.twitterF = null;
            return;
        }
        this.twitterF = new TwitterFactory(getConfigurationBuilder(config).build());
    }

    private JSONObject getConfiguration() throws IOException, ParseException {
        if(System.getenv("twitter_access_token")!=null){
            JSONObject configuration = new JSONObject();
            configuration.put("AccessToken",System.getenv("twitter_access_token"));
            configuration.put("AccessTokenSecret",System.getenv("twitter_access_token_secret"));
            configuration.put("ConsumerKey",System.getenv("twitter_consumer_key"));
            configuration.put("ConsumerSecret",System.getenv("twitter_consumer_key_secret"));
            return configuration;
        }else return DSJSONUtils.readFile(PATH);

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
        if(instance == null){
            instance = new TwitterGateway();
        }

        return instance;
    }

    @Override
    public List<SocialNetworkMessage> getMessageList(SocialNetworkQueryData queryData) {
        Objects.requireNonNull(queryData);

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
            DeuLogger.logger.error("Error on getting tweets.");
        }

        return messages;
    }
}
