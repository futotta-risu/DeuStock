package es.deusto.deustock.resources.socialnetwork;

import es.deusto.deustock.dataminer.features.SentimentExtractor;
import es.deusto.deustock.log.DeuLogger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum.Twitter;

/**
 * Twitter sentiment resource for sentiment analysis.
 *
 * @author Erik B. Terres
 */
@Path("twitter/sentiment/{query}")
public class TwitterSentimentResource {

    private SentimentExtractor extractor;

    public TwitterSentimentResource(){
        extractor = new SentimentExtractor(Twitter);
    }

    public TwitterSentimentResource(SentimentExtractor extractor){
        this.extractor = extractor;
    }
    
    public void setSentimentExtractor(SentimentExtractor extractor) { this.extractor = extractor; }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getSentiment(@PathParam("query") String query) {
        DeuLogger.logger.info("Sentiment Analyzer called for query: " + query);

        double sentiment;
        try {
            sentiment = extractor.getSentimentTendency(query);
        } catch (InterruptedException e) {
            DeuLogger.logger.error("Sentiment Analyzer Interrupted.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(sentiment)
                .build();
    }
}
