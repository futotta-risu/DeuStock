package es.deusto.deustock.dataminer.gateway.socialnetworks.gateways;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.PoliteHttpRestClient;
import es.deusto.deustock.data.SocialNetworkMessage;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkAPIGateway;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.util.file.DSJSONUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Reddit {@link SocialNetworkAPIGateway} to get {@link SocialNetworkMessage} from Reddit.
 *
 * {@link RedditGateway#getInstance()}
 *
 * @author landersanmi
 */
public class RedditGateway  implements SocialNetworkAPIGateway {

    private static final String PATH ="data/secret_keys/secrets_reddit.json";
    private static final Logger logger = Logger.getLogger(RedditGateway.class);

    private PoliteHttpRestClient restClient;
    private User user = null;

    private static RedditGateway instance = null;

    public static RedditGateway getInstance() {
        if(instance == null){
            instance = new RedditGateway();
        }
        return instance;
    }

    private RedditGateway(){
        JSONObject config;
        try {
            config = getConfiguration();
            // Initialize REST Client
            restClient = new PoliteHttpRestClient();
            restClient.setUserAgent("bot/1.0 by name");
            user = new User(restClient, config.get("UsernameSecret").toString(), config.get("PasswordSecret").toString());

        } catch (IOException | ParseException e) {
            logger.error("Could not load configuration [Reddit].");
            return;
        }
        // Connect the user
        try {
            user.connect();
        } catch (Exception e) {
            logger.error("Could not login with the given credentials [Reddit]");
        }
    }

    private JSONObject getConfiguration() throws IOException, ParseException {
        if(System.getenv("reddit_username_secret")!=null){
            var configuration = new JSONObject();
            configuration.put("UsernameSecret",System.getenv("reddit_username_secret"));
            configuration.put("PasswordSecret",System.getenv("reddit_password_secret"));
            return configuration;
        }else return DSJSONUtils.readFile(PATH);

    }


    @Override
    public List<SocialNetworkMessage> getMessageList(SocialNetworkQueryData queryData) {
        // Handle to Submissions, which offers the basic API submission functionality
        Submissions subms = new Submissions(restClient, user);

        // Retrieve submissions of a submission
        List<Submission> submissionsSubreddit = subms.ofSubreddit(queryData.getSearchQuery(), SubmissionSort.TOP, -1, queryData.getNMessages(), null, null, true);

        List<SocialNetworkMessage> messages = new ArrayList<SocialNetworkMessage>();
        for(Submission submission : submissionsSubreddit){
            if(!submission.getSelftext().equals("")){
                messages.add(new SocialNetworkMessage(submission.getAuthor(), submission.getSelftext()));
            }
        }

        return messages;
    }

}
