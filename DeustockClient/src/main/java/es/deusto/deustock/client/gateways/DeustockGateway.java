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

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
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
    
    public boolean register(String username, String password, String fullName, Date birthDate, String aboutMe, String country) throws UnsupportedEncodingException, NoSuchAlgorithmException {	
		Response response = (Response) getHostWebTarget().path("users").path("register")
				.path(username).path(getEncrypt(password)).path(fullName)
				.path(birthDate.toString()).path(aboutMe).path(country).request(MediaType.TEXT_PLAIN).get();
		
		return Boolean.parseBoolean(response.readEntity(String.class));
    }
    
//    public User login(String username, String password) {
//    	Response response = getHostWebTarget().path(username).path(getEncrypt(password)).request(MediaType.APPLICATION_JSON).get();
//        JSONObject obj = new JSONObject(response.readEntity(String.class));
//
//    	return(new User(obj.get("username"), obj.get("password"), obj.get("fullName"), obj.get("birthDate"), obj.get("aboutMe"), obj.get("country")));
//    }
    
    private String getEncrypt(String pass) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		byte[] data = pass.getBytes("UTF-8");
		byte[] encrypted = messageDigest.digest(data);
		return encrypted.toString();
    }


}
