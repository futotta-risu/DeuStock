package es.deusto.deustock;

import org.apache.log4j.PropertyConfigurator;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;


/**
 * Main class.
 *
 */
public class Main {

    private static final Logger logger = Logger.getLogger(Main.class);

    private Main(){}

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
        PropertyConfigurator.configure("resources/log4j.properties");
        final HttpServer server = startServer();
        logger.info(String.format("Jersey app started with WADL available at "
                + "{} application.wadl\nHit enter to stop it...%n", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }
}


