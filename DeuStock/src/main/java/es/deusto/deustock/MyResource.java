package es.deusto.deustock;

import es.deusto.deustock.dataminer.Extractor;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum;
import es.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkQueryData;
import es.deusto.deustock.dataminer.gateway.socialnetworks.exceptions.NoGatewayTypeException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {

        Extractor ext = new Extractor();
        String result= "error";
        try{
            ext.setGateway(SocialNetworkGatewayEnum.Twitter);
            SocialNetworkQueryData snq = new SocialNetworkQueryData();
            snq.setSearchQuery("happy");
            result = String.valueOf(ext.getSentimentTendency(snq));

        } catch (NoGatewayTypeException e) {
            result= "error2";
            e.printStackTrace();
        }

        return result;
    }
}
