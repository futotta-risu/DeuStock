package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.net.RESTVars;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class DeustockGateway {


    public List<Stock> getStockList(){
        List<Stock> stocks = new ArrayList<>();

        // Mock Data
        stocks.add(new Stock("AMZ",2,2));
        stocks.add(new Stock("Prob",20,24));
        stocks.add(new Stock("TST",200,25));

        return stocks;
    }

    public List<Stock> getStockList(String searchQuery){
        List<Stock> stocks = new ArrayList<>();

        //Response response = getHostWebTarget().path(RESTVars.appName).path("stocks")
        //        .path("list").path(searchQuery).request(MediaType.TEXT_PLAIN).get();


        return stocks;
    }

    public double getTwitterSentiment(String searchQuery){
        Response response = getHostWebTarget().path("twitter").path("sentiment")
                .path(searchQuery).request(MediaType.TEXT_PLAIN).get();

        return Double.parseDouble(response.readEntity(String.class));
    }

    private WebTarget getHostWebTarget(){
        return ClientBuilder.newClient().target(RESTVars.restUrl);
    }

}
