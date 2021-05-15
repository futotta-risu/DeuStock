package es.deusto.deustock.resources;


import org.apache.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * HelloWorld resource for testing purposes
 *
 * @author Erik B. Terres
 */
@Path("helloworld")
public class HelloWorld {

    private static final Logger logger = Logger.getLogger(HelloWorld.class);

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getIt() {
        logger.info("Hello World method called.");

        return Response
                .status(Response.Status.OK)
                .entity("Hello World")
                .build();
    }
}
