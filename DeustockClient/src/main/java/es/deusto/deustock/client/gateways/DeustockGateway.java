package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.data.help.FAQQuestion;
import es.deusto.deustock.client.log.DeuLogger;
import es.deusto.deustock.client.net.RESTVars;
import es.deusto.deustock.client.visual.help.FAQLine;

import org.glassfish.jersey.client.ClientResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 *
 * @author Erik B. Terres
 */
public class DeustockGateway {

    private WebTarget getHostWebTarget(){
        return ClientBuilder.newClient().target(RESTVars.restUrl);
    }

    public Stock getStock(String acronym, String interval){
        Response  response = getHostWebTarget().path("stock")
                .path("detail").path(acronym).path(interval).request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(Stock.class);
    }
    
    public List<Stock> getStockList(String listType){
        Response  response = getHostWebTarget().path("stock")
                .path("list").path(listType).request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(new GenericType<List<Stock>>(){});
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
    	Response response = getHostWebTarget().path("users").path("register")
    			.request("application/json")
                .post(Entity.entity(
                        new User(username, getEncrypt(password), fullName, birthDate, aboutMe, country)
                        , MediaType.APPLICATION_JSON)
                );

        return response.getStatus() == 200;
    }
    
    public User login(String username, String password){
    	Response response = getHostWebTarget().path("users").path("login")
                .path(username).path(getEncrypt(password))
                .request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(User.class);
    }
    
    private String getEncrypt(String pass) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            DeuLogger.logger.error("Could not encrypt password, algorithm is not available");
        }
        byte[] data = new byte[0];
        data = pass.getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = messageDigest.digest(data);
		return Arrays.toString(encrypted);
    }

    public User getUser(String username){
        Response data = getHostWebTarget()
                .path("user").path(username)
                .request(MediaType.APPLICATION_JSON).get();

        return data.readEntity(User.class);
    }

    public boolean deleteUser(String username, String password){
        Response response = getHostWebTarget()
                .path("users").path("delete").path(username).path(password)
                .request().get();

        return response.getStatus() == 200;
    }
    
    public byte[] getStockReport(String acronym, String interval) throws IOException {
    	Response data = getHostWebTarget()
    			.path("reports").path(acronym).path(interval)
    			.request(MediaType.APPLICATION_OCTET_STREAM).get();
    	
    	return data.readEntity(byte[].class);     
    }


    public boolean updateUser(String username, String fullName, Date birthDate, String aboutMe, String country) {
        Response response = getHostWebTarget().path("users").path("update")
                .request("application/json")
                .post(Entity.entity(new User(username, "pass",fullName,birthDate,aboutMe, country), MediaType.APPLICATION_JSON));

        System.out.println(response);
        return response.getStatus() == 200;
    }
}
