package es.deusto.deustock.resources.socialnetwork;

import es.deusto.deustock.dataminer.Extractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.dataminer.gateway.socialnetworks.exceptions.NoGatewayTypeException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
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
    public String getSentiment(@PathParam("query") String query) {

        Extractor ext = new Extractor();
        String result= "error";
        try{
            ext.setGateway(SocialNetworkGatewayEnum.Twitter);
            result = String.valueOf(ext.getSentimentTendency(new SocialNetworkQueryData(query)));

        } catch (NoGatewayTypeException e) {
            result= "error2";
            e.printStackTrace();
        }

        return result;
    }
}
