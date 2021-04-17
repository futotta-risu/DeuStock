package es.deusto.deustock.resources.socialnetwork;

import es.deusto.deustock.dataminer.Extractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum.Twitter;

/**
 * Twitter sentiment resource for sentiment analysis.
 *
 * @author Erik B. Terres
 */
@Path("twitter/sentiment/{query}")
public class TwitterSentiment {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getSentiment(@PathParam("query") String query) throws InterruptedException {
        Extractor ext = new Extractor(Twitter);

        return String.valueOf(ext.getSentimentTendency(new SocialNetworkQueryData(query)));

    }
}
