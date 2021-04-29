package es.deusto.deustock.client.gateways;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.data.help.FAQQuestion;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Date;
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
    Response mockResponse;

    Stock stock;
    Double sentiment;

    @BeforeEach
    public void setUp() {
        //GIVEN
        mockClient = mock(Client.class);
        mockWebTarget = mock(WebTarget.class);
        mockBuilder = mock(Invocation.Builder.class);
        clientBuilderMock = mock(ClientBuilder.class);
        mockResponse = mock(Response.class);

        stock = new Stock();
        stock.setAcronym("acronymTest");
    }

    @Test
    public void testPetitionReturnsWebTarget() {
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
    }

    @Test
    public void testGetStock() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
        Response response = mock(Response.class);

        clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
        when(mockWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
        when(mockBuilder.get()).thenReturn(response);
        when(response.readEntity(Stock.class)).thenReturn(stock);


       Stock result = new DeustockGateway()
                .getStock("acronymTest", "intervalTest");

        assertEquals(response.readEntity(Stock.class), result);

        }
    }

    @Test
    public void tesGetStockList() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
        	List<Stock> list = new ArrayList<Stock>();
        	
        	Response response = mock(Response.class);
	
	        clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
	        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
	        when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
	        when(mockWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
	        when(mockBuilder.post(any())).thenReturn(response);
	        when(mockBuilder.get()).thenReturn(response);
	        when(response.readEntity(any(GenericType.class))).thenReturn(list);

            List<Stock> result = new DeustockGateway()
                    .getStockList("listTypeTest");

            assertEquals(list, result);

        }
    }

    @Test
    public void testGetTwitterSentiment() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {

            clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
            when(mockClient.target(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.request(MediaType.TEXT_PLAIN)).thenReturn(mockBuilder);
            when(mockBuilder.get()).thenReturn(mockResponse);
            when(mockResponse.readEntity(String.class)).thenReturn("0.5");

            double result = new DeustockGateway().getTwitterSentiment("searchQueryTest");

            assertEquals(mockResponse.readEntity(String.class), "0.5");

        }
    }

    /*

    @Test
    public void testGetFAQList() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            JSONObject question = new JSONObject();
            question.put("question","TestQuestion");
            question.put("answer","TestAnswer");
            array.put(question);

            jsonObject.put("questions", array);

            clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
            when(mockClient.target(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
            when(mockBuilder.get()).thenReturn(mockResponse);


            List<FAQQuestion> result = new DeustockGateway().getFAQList();

            assertTrue(result.size() > 0);
            // TODO Assert Equals
        }
    }
    */

    @Test
    public void testRegister() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {

            clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
            when(mockClient.target(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.request(anyString())).thenReturn(mockBuilder);
            when(mockBuilder.post(any())).thenReturn(mockResponse);
            when(mockResponse.getStatus()).thenReturn(200);

            //WHEN
            boolean result = new DeustockGateway()
                    .register("usernameTest", "passTest", "fullNameTest", "aboutMeTeTest", "countryTest");

            //THEN
            assertTrue(result);
            assertEquals(200, mockResponse.getStatus());
        }
    }
    @Test
    public void testLogin() {
        try(MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)){
            User user = new User();
            user.setUsername("usernameTest");
            user.setPassword("passTest");

            Response response = mock(Response.class);

            clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
            when(mockClient.target(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
            when(mockBuilder.post(any())).thenReturn(response);
            when(mockBuilder.get()).thenReturn(response);
            when(response.readEntity(User.class)).thenReturn(user);

            User result = new DeustockGateway().login("usernameTest", "passTest");

            assertEquals(user, result);
        }
    }
    @Test
    public void tesGetUser() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
        	User user = new User();
            user.setUsername("usernameTest");
        	
        	Response response = mock(Response.class);
	
	        clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
	        when(mockClient.target(anyString())).thenReturn(mockWebTarget);
	        when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
	        when(mockWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
	        when(mockBuilder.post(any())).thenReturn(response);
	        when(mockBuilder.get()).thenReturn(response);
	        when(response.readEntity(User.class)).thenReturn(user);
	
	        User result = new DeustockGateway()
	                .getUser("usernameTest");
	
	        assertEquals(user, result);

        }
    }
    
    @Test
    public void testDeleteUser() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
        	User user = new User();
            user.setUsername("usernameTest");
            user.setPassword("passTest");
        	
        	Response response = mock(Response.class);

            clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
            when(mockClient.target(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.request()).thenReturn(mockBuilder);
            when(mockBuilder.post(any())).thenReturn(response);
            when(mockBuilder.get()).thenReturn(response);
	        when(response.getStatus()).thenReturn(200);

            //WHEN
            boolean result = new DeustockGateway()
                    .deleteUser("usernameTest", "passTest");

            //THEN
            assertTrue(result);
        }
    }
    
    @Test
    public void testUpdateUser() {
        try (MockedStatic<ClientBuilder> clientBuilder = mockStatic(ClientBuilder.class)) {
        	User user = new User();
            user.setUsername("usernameTest");
            user.setPassword("passTest");
        	
        	Response response = mock(Response.class);

            clientBuilder.when(ClientBuilder::newClient).thenReturn(mockClient);
            when(mockClient.target(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.path(anyString())).thenReturn(mockWebTarget);
            when(mockWebTarget.request(MediaType.APPLICATION_JSON)).thenReturn(mockBuilder);
            when(mockBuilder.post(any())).thenReturn(response);
            when(mockBuilder.get()).thenReturn(response);
            when(response.getStatus()).thenReturn(200);

            //WHEN
            Date dateTest = new Date();
            boolean result = new DeustockGateway()
                    .updateUser("usernameTest", "fullNameTest", dateTest, "aboutMeTeTest", "countryTest");

            //THEN
            assertTrue(result);
        }
    }
}