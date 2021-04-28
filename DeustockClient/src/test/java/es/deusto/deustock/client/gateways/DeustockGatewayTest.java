package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.net.RESTVars;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeustockGatewayTest{

    Response mockResponse;
    Client mockClient;
    WebTarget mockWebTarget;
    RESTVars mockRest;
    Stock stock;
    List<Stock> stockList;
    Double sentiment;

    @Before
    public void setUp(){
        mockResponse = mock(Response.class);
        mockClient = mock(Client.class);
        mockWebTarget = mock(WebTarget.class);
        mockRest = mock(RESTVars.class);
        stock = new Stock();
        stock.setPrice(BigDecimal.valueOf(600));
        stock.setFullName("TestStock");
        stock.setAcronym("TS");
        stock.setDescription("Test Stock description");

        stockList.add(stock);

        sentiment = 20.0;
    }

    @Test
    public void petitionReturnsWebTarget(){
        //any string o objeto mockito restvars?
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
    }

    @Test
    public void testGetStock(){
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        Response res = Response.status(200).entity(stock).build();
        when(mockWebTarget.path("stock").path("TS").path(anyString()).request(MediaType.APPLICATION_JSON).get()).thenReturn(res);
        assertEquals(stock, res.readEntity(Stock.class));
    }

    @Test
    public void tesGetStockList() {
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        Response res = Response.status(200).entity(stockList).build();
        when(mockWebTarget.path("stock").path("list").path(anyString()).request(MediaType.APPLICATION_JSON).get()).thenReturn(res);
        assertEquals(stockList, res.readEntity(new GenericType<List<Stock>>(){}));
    }

    @Test
    public void testGetTwitterSentiment(){
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        Response res = Response.status(200).entity(sentiment).build();
        when(mockWebTarget.path("twitter").path("sentiment").path(anyString()).request(MediaType.APPLICATION_JSON).get()).thenReturn(res);
        assertEquals(sentiment, Double.parseDouble(res.readEntity(String.class)) );
    }

    @Test
    public void getFAQList(){
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.path("help").path("faq").path("list").request(MediaType.APPLICATION_JSON).get()).thenReturn(mockResponse);


    }

    /*
    ublic List<FAQQuestion>  getFAQList(){
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
     */
}