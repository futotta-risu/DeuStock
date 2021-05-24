package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.data.help.FAQQuestion;
import es.deusto.deustock.client.data.stocks.StockHistory;
import es.deusto.deustock.client.gateways.exceptions.ForbiddenException;
import es.deusto.deustock.client.net.RESTVars;

import es.deusto.deustock.client.simulation.investment.operations.OperationType;
import org.apache.log4j.Logger;
import org.apache.maven.surefire.shade.booter.org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javax.print.attribute.standard.Media;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Erik B. Terres
 *
 * Class that conects the client with the REST Services
 */
public class DeustockGateway {

    private final Logger logger = Logger.getLogger(DeustockGateway.class);

    /**
     * Using the ClientBuilder instance gets the connection to the server
     * @return it returns the WebTarget using the URI of the targeted web resource
     */
    private WebTarget getHostWebTarget(){
        return ClientBuilder.newClient().target(RESTVars.restUrl);
    }

    /**
     * Method that gets the stock by searching with the following two parameters
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP GET
     * @param acronym a variable in String
     * @param interval a variable in String
     * @return returns the Stock object
     */
    public Stock getStock(String acronym, String interval){
        Response  response = getHostWebTarget().path("stock")
                .path("detail").path(acronym).path(interval).request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(Stock.class);
    }

    /**
     * Method that gets the stock by searching with the following parameter
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP GET
     * @param acronym a variable in String
     * @return returns the Stock object
     */
    public Stock getSearchedStock(String acronym){
        Response response = getHostWebTarget().path("stock")
                .path("search").path(acronym).request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(Stock.class);
    }

    /**
     * Method that gets a list of Stocks by selecting the type of list
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP GET
     * @param listType a variable in String
     * @return returns a list of Stock
     */
    public List<Stock> getStockList(String listType){
        Response  response = getHostWebTarget().path("stock")
                .path("list").path(listType).request(MediaType.APPLICATION_JSON).get();

        return response.readEntity(new GenericType<>() {
        });
    }

    /**
     * Method that gets a double by searching
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP GET
     * @param searchQuery
     * @return
     */
    public double getTwitterSentiment(String searchQuery){
        Response response = getHostWebTarget().path("twitter").path("sentiment")
                .path(searchQuery).request(MediaType.TEXT_PLAIN).get();

        return Double.parseDouble(response.readEntity(String.class));
    }
    public double getRedditSentiment(String searchQuery){
        Response response = getHostWebTarget().path("reddit").path("sentiment")
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
    
    public boolean register(String username, String password, String fullName, String aboutMe, String country){

    	Response response = getHostWebTarget().path("auth").path("register")
			.request("application/json")
            .post(Entity.entity(new User()
                        .setUsername(username)
                        .setPassword(getEncrypt(password))
                        .setFullName(fullName)
                        .setDescription(aboutMe)
                        .setCountry(country)
                  , MediaType.APPLICATION_JSON)
            );

        return response.getStatus() == 200;
    }
    
    public String login(String username, String password) throws ForbiddenException {
    	Response response = getHostWebTarget().path("auth").path("login")
                .path(username).path(getEncrypt(password))
                .request(MediaType.APPLICATION_JSON)
                .get();
        if(response.getStatus() != 200){
            throw new ForbiddenException("Could not log in");
        }
        return response.readEntity(String.class);
    }
    
    private String getEncrypt(String pass) {
        // TODO Change location
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Could not encrypt password, algorithm is not available");
        }
        byte[] data = new byte[0];
        data = pass.getBytes(StandardCharsets.UTF_8);
        byte[] encrypted = messageDigest.digest(data);
		return Arrays.toString(encrypted);
    }

    public User getUser(String username){
        Response data = getHostWebTarget()
                .path("tpuser").path(username)
                .request(MediaType.APPLICATION_JSON).get();

        return data.readEntity(User.class);
    }

    public boolean deleteUser(String token){
        Response response = getHostWebTarget()
                .path("tpuser")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .delete();

        return response.getStatus() == 200;
    }

    public boolean updateUser(User user, String token) {
        Response response = getHostWebTarget().path("tpuser")
                .request("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .put(Entity.entity(user, MediaType.APPLICATION_JSON));

        return response.getStatus() == 200;
    }

    public byte[] getStockReport(String acronym, String interval) throws IOException {
        Response data = getHostWebTarget()
                .path("reports").path("stock").path(acronym).path(interval)
                .request(MediaType.APPLICATION_OCTET_STREAM).get();

        return data.readEntity(byte[].class);
    }

    public File getStockReport(String acronym, String interval, String path){
        Response response = getHostWebTarget()
                .path("reports").path("stock").path(acronym).path(interval)
                .request("application/pdf")
                .get();

        return generateFile(acronym, response, path);
    }

    public byte[] getUserReport(String username) throws IOException {
        Response data = getHostWebTarget()
                .path("reports").path("user").path(username)
                .request(MediaType.APPLICATION_OCTET_STREAM).get();

        return data.readEntity(byte[].class);
    }
    public File getUserReport(String username, String path){
        Response response = getHostWebTarget()
                .path("reports").path("user").path(username)
                .request("application/pdf")
                .get();

        return generateFile(username, response, path);
    }

    private File generateFile(String name, Response response, String path){
        InputStream is = response.readEntity(InputStream.class);
        File downloadfile = new File(path + "/" + name  + "_" + Calendar.getInstance().getTimeInMillis() + ".pdf");
        byte[] byteArray = new byte[0];
        try {
            byteArray = IOUtils.toByteArray(is);
            FileOutputStream fos = new FileOutputStream(downloadfile);
            fos.write(byteArray);
            fos.flush();
            fos.close();
            IOUtils.closeQuietly(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downloadfile;
    }

    public List<StockHistory> getHoldings(String username){
        Response response = getHostWebTarget()
                .path("user").path(username).path("holdings")
                .request(MediaType.APPLICATION_JSON)
                .get();

        return response.readEntity(new GenericType<>(){});
    }
    
    public boolean resetHoldings(String username) {
    	Response response = getHostWebTarget()
    			.path("user").path(username).path("holdings").path("reset")
    			.request().get();
    	return response.getStatus() == 200;
    }

    public double getBalance(String username){
        // TODO Delete username
        Response response = getHostWebTarget()
                .path("holdings").path("balance")
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + MainController.getInstance().getToken())
                .get();

        return response.readEntity(Double.class);
    }

    public void openOperation(OperationType operationType, Stock stock, String token, double amount){
        if(operationType == null){
            throw new IllegalArgumentException("Invalid operation type");
        }
        if(amount < 0){
            throw new IllegalArgumentException("Is not posible to set negative ammount");
        }
        if(amount == 0){
            return;
        }

        Response response = getHostWebTarget()
                .path("stock/operation/open")
                .path(operationType.name())
                .path(stock.getAcronym())
                .path(String.valueOf(amount))
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .get();
    }

    public void closeOperation(String stockHistoryID, String token){
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add("stockHistoryID", stockHistoryID);

        Response response = getHostWebTarget()
                .path("stock/operation/close")
                .path(stockHistoryID)
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .post(Entity.entity(formData, MediaType.APPLICATION_JSON));
    }


}
