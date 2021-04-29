package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.help.FAQQuestion;
import es.deusto.deustock.client.net.RESTVars;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedStatic;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DeustockGatewayTest {


    Client mockClient;
    ClientBuilder clientBuilderMock;
    WebTarget mockWebTarget;
    Invocation.Builder mockBuilder;

    Stock stock;

    @BeforeEach
    public void setUp() {
        //GIVEN
        mockClient = mock(Client.class);
        mockWebTarget = mock(WebTarget.class);
        mockBuilder = mock(Invocation.Builder.class);
        clientBuilderMock = mock(ClientBuilder.class);

        stock = new Stock();
        stock.setPrice(600);
        stock.setFullName("TestStock");
        stock.setAcronym("TS");
        stock.setDescription("Test Stock description");
/*
        stockList = new ArrayList<>();
        stockList.add(stock);

        sentiment = 20.0;
        */

    }

    @Test
    public void petitionReturnsWebTarget() {
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
    }

    @Test
    public void testGetStock() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
        Response response = Response.status(200).build();

        clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.request()).thenReturn(mockBuilder);
        when(mockBuilder.get()).thenReturn(response);

       Stock result = new DeustockGateway()
                .getStock("acronymTest", "intervalTest");

        assertEquals(response.readEntity(Stock.class), result);
        }
    }

    @Test
    public void tesGetStockList() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
        Response response = Response.status(200).build();

        clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.request()).thenReturn(mockBuilder);
        when(mockBuilder.get()).thenReturn(response);

        List<Stock> result = new DeustockGateway()
                .getStockList("listTypeTest");

        assertEquals(response.readEntity(new GenericType<>() {}), result);

    }
    }

    @Test
    public void testGetTwitterSentiment() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
            Response response = Response.status(200).build();

            clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
            when(mockClient.target(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.request()).thenReturn(mockBuilder);
            when(mockBuilder.get()).thenReturn(response);

            double result = new DeustockGateway()
                    .getTwitterSentiment("searchQueryTest");

            assertEquals(Double.parseDouble(response.readEntity(String.class)), result);

        }

        @Test
        public void testGetFAQList() {
            try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
                Response response = Response.status(200).build();

                clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
                when(mockClient.target(anyString())).thenReturn(mockWebTarget);
                when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
                when(mockWebTarget.request()).thenReturn(mockBuilder);
                when(mockBuilder.get()).thenReturn(response);

                List<FAQQuestion> result = new DeustockGateway()
                        .getFAQList();

                assertEquals(response.readEntity(String.class), result);
            }
        }

        @Test
        public void testRegister() {
            try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
                Response response = Response.status(200).build();

                clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
                when(mockClient.target(anyString())).thenReturn(mockWebTarget);
                when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
                when(mockWebTarget.request()).thenReturn(mockBuilder);
                when(mockBuilder.post(any())).thenReturn(response);

                //WHEN
                boolean result = new DeustockGateway()
                        .register("usernameTest", "passTest", "fullNameTest", "aboutMeTeTest", "countryTest");

                //THEN
                assertTrue(result);
            }
        }
    }
}
/*
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
