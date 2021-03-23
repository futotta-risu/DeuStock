package com.deusto.deustock;

import com.deusto.deustock.dataminer.Extractor;
import com.deusto.deustock.dataminer.gateway.socialnetworks.exceptions.NoGatewayTypeException;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import static com.deusto.deustock.dataminer.gateway.socialnetworks.SocialNetworkGatewayEnum.*;

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
        final ResourceConfig rc = new ResourceConfig().packages("com.dekses.jersey.docker.demo");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        /*final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.stop();*/
        System.out.println(System.getProperty("java.version"));
        Extractor extractor = new Extractor();
        try {
            extractor.setGateway(Twitter);
            extractor.setSearchQuery("\"islam\"");
            System.out.println("Average sentiment: " + extractor.getSentimentTendency());
        } catch (NoGatewayTypeException e) {
            e.printStackTrace();
        }


    }
}

