package es.deusto.deustock;

import es.deusto.deustock.resources.HelloWorld;
import es.deusto.deustock.resources.UserResource;
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
        final ResourceConfig rc = new ResourceConfig()
                .packages("es.deusto.deustock.resources");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     */
    public static void main(String[] args) throws IOException {

        final HttpServer server = startServer();
        System.out.printf("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...%n", BASE_URI);
        System.in.read();
        server.stop();
    }
}

