package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.help.FAQQuestion;
import es.deusto.deustock.client.net.RESTVars;
import es.deusto.deustock.client.visual.help.FAQLine;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Erik B. Terres
 */
public class DeustockGateway {

    private WebTarget getHostWebTarget(){
        return ClientBuilder.newClient().target(RESTVars.restUrl);
    }


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

    public List<FAQQuestion>  getFAQList(){
        Response data = getHostWebTarget()
                .path("help").path("faq").path("list")
                .request(MediaType.APPLICATION_JSON).get();

        JSONObject obj = new JSONObject(data.readEntity(String.class));

        List<FAQQuestion> questionList = new LinkedList<>();
        for(Object question : obj.getJSONArray("questions"))
            questionList.add( new FAQQuestion((JSONObject) question) );

        return questionList;
    }


}
