package es.deusto.deustock.resources.socialnetwork;

import es.deusto.deustock.dataminer.features.SentimentExtractor;
import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum.REDDIT;

/**
 * @author landersanmi
 */
@Path("reddit/sentiment/{query}")
public class RedditSentimentResource {

    private SentimentExtractor extractor;
    private final Logger logger = Logger.getLogger(RedditSentimentResource.class);

    public RedditSentimentResource(){
        extractor = new SentimentExtractor(REDDIT);
    }

    public void setSentimentExtractor(SentimentExtractor extractor) {
        this.extractor = extractor;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getSentiment(@PathParam("query") String query) {
        logger.info("Sentiment Analyzer called [Reddit]");

        double sentiment;
        try {
            sentiment = extractor.getSentimentTendency(query);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Sentiment Analyzer Interrupted.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(sentiment)
                .build();
    }
}
