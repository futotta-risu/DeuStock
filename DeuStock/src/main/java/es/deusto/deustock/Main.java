package es.deusto.deustock;

import es.deusto.deustock.resources.HelloWorld;
import es.deusto.deustock.resources.help.FAQList;
import es.deusto.deustock.resources.socialnetwork.TwitterSentiment;
import es.deusto.deustock.resources.stocks.StockDetail;
import es.deusto.deustock.resources.stocks.StockList;
import es.deusto.deustock.resources.user.UserDetail;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;


/**
 * Main class.
 *
 */
public class Main {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8080/myapp/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.dekses.jersey.docker.demo package
        rc.register(TwitterSentiment.class);
        rc.register(HelloWorld.class);
        rc.register(StockList.class);
        rc.register(StockDetail.class);
        rc.register(FAQList.class);
        rc.register(UserDetail.class);
        rc.register(UserResource.class);
        rc.register(StockReportResource.class);
        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     */
    public static void main(String[] args) throws IOException {

        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();
    }
}

