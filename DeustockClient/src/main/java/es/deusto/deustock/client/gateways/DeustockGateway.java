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
     *
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
     *
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
     *
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
     * Method that gets a double by searching with the query parameter
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - plain text format
     * Invoking HTTP GET
     *
     * @param searchQuery a String
     * @return returns the value of the twitter sentiment as a Double
     */
    public double getTwitterSentiment(String searchQuery){
        Response response = getHostWebTarget().path("twitter").path("sentiment")
                .path(searchQuery).request(MediaType.TEXT_PLAIN).get();

        return Double.parseDouble(response.readEntity(String.class));
    }

    /**
     * Method that gets a double by searching with the query parameter
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - plain text format
     * Invoking HTTP GET
     *
     * @param searchQuery a String
     * @return returns the value of the reddit sentiment as a Double
     */
    public double getRedditSentiment(String searchQuery){
        Response response = getHostWebTarget().path("reddit").path("sentiment")
                .path(searchQuery).request(MediaType.TEXT_PLAIN).get();

        return Double.parseDouble(response.readEntity(String.class));
    }

    /**
     * Method that gets FAQQuestion list
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP GET
     *
     * @return returns the List of questions and answers
     */
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

    /**
     * Method that confirms if a registration has been successfully done
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP POST
     *
     * @param username String: the username of the user to register
     * @param password String: the password of the user to register
     * @param fullName String: the full name of the user to register
     * @param aboutMe String: the description of the user to register
     * @param country String: the country of the user to register
     * @return returns a boolean, true when the registration and connection has been successful and false when error
     */
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

    /**
     * Method that confirms if a login has been successfully done
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP POST
     *
     * @param username String: the username of the user for login
     * @param password String: the password of the user for login
     * @return returns a boolean, true when the login and connection has been successful and false when error
     */
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


    /**TODO
     *
     * @param pass
     * @return
     */
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

    /**
     * Method that gets the user by the username
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP GET
     *
     * @param username String parameter
     * @return returns the User object searching by the username
     */
    public User getUser(String username){
        Response data = getHostWebTarget()
                .path("tpuser").path(username)
                .request(MediaType.APPLICATION_JSON).get();

        return data.readEntity(User.class);
    }

    /**TODO
     *
     * @param token
     * @return
     */
    public boolean deleteUser(String token){
        Response response = getHostWebTarget()
                .path("tpuser")
                .request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .delete();

        return response.getStatus() == 200;
    }

    /**TODO
     *
     * @param user
     * @param token
     * @return
     */
    public boolean updateUser(User user, String token) {
        Response response = getHostWebTarget().path("tpuser")
                .request("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .put(Entity.entity(user, MediaType.APPLICATION_JSON));

        return response.getStatus() == 200;
    }

    /**
     * Method that gets the StockReport
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - octect stream format
     * Invoking HTTP GET
     *
     * @param acronym String: acronym of the Stock that will be searched
     * @param interval String: interval of the Stock that will be searched
     * @return returns byte[] of the Stock Report
     * @throws IOException throws a exception in case of error
     */
    public byte[] getStockReport(String acronym, String interval) throws IOException {
        Response data = getHostWebTarget()
                .path("reports").path("stock").path(acronym).path(interval)
                .request(MediaType.APPLICATION_OCTET_STREAM).get();

        return data.readEntity(byte[].class);
    }

    /**
     * TODO
     * @param acronym
     * @param interval
     * @param path
     * @return
     */
    public File getStockReport(String acronym, String interval, String path){
        Response response = getHostWebTarget()
                .path("reports").path("stock").path(acronym).path(interval)
                .request("application/pdf")
                .get();

        return generateFile(acronym, response, path);
    }

    /**
     * Method that gets the user by the username
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - octet stream format
     * Invoking HTTP GET
     *
     * @param username String: parameter by which it will be searched
     * @return byte[]: report of the user
     * @throws IOException throws a exception in case of error
     */
    public byte[] getUserReport(String username) throws IOException {
        Response data = getHostWebTarget()
                .path("reports").path("user").path(username)
                .request(MediaType.APPLICATION_OCTET_STREAM).get();

        return data.readEntity(byte[].class);
    }

    /**
     * TODO
     * @param username
     * @param path
     * @return
     */
    public File getUserReport(String username, String path){
        Response response = getHostWebTarget()
                .path("reports").path("user").path(username)
                .request("application/pdf")
                .get();

        return generateFile(username, response, path);
    }

    /**
     * TODO
     * @param name
     * @param response
     * @param path
     * @return
     */
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

    /**
     * Method that gets the Stock History list by the username
     * using .path for accessing a specific resource and building a HTTP request invocation with .request - JSON format
     * Invoking HTTP GET
     *
     * @param username String: the username by which it will be searched
     * @return returns a list of Stock History
     */
    public List<StockHistory> getHoldings(String username){
        Response response = getHostWebTarget()
                .path("user").path(username).path("holdings")
                .request(MediaType.APPLICATION_JSON)
                .get();

        return response.readEntity(new GenericType<>(){});
    }

    /**
     * TODO
     * @param username
     * @return
     */
    public boolean resetHoldings(String username) {
    	Response response = getHostWebTarget()
    			.path("user").path(username).path("holdings").path("reset")
    			.request().get();
    	return response.getStatus() == 200;
    }

    /**
     * TODO
     * @param username
     * @return
     */
    public double getBalance(String username){
        // TODO Delete username
        Response response = getHostWebTarget()
                .path("holdings").path("balance")
                .request(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + MainController.getInstance().getToken())
                .get();

        return response.readEntity(Double.class);
    }

    /**TODO
     *
     * @param operationType
     * @param stock
     * @param token
     * @param amount
     */
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

    /**TODO
     *
     * @param stockHistoryID
     * @param token
     */
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
