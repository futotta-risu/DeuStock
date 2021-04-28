package es.deusto.deustock.client.controller;

import com.sun.research.ws.wadl.Response;
import es.deusto.deustock.client.controllers.LoginController;
import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest{

    DeustockGateway gateway;
    TextField usernameText;
    TextField passwordText;
    User mockUser;
    User user;

    @Before
    public void setUp(){
        mockUser = mock(User.class);
        gateway = mock(DeustockGateway.class);
        user = new User("juangomez", "juan123", "Juan Gomez", new Date(2012-2-12), "Spain", "Hola");
    }

    @Test
    public void loginValidDueToCorrectInput(){
        when(gateway.login("juangomez","juan123")).thenReturn(mockUser);
        assertEquals(user, mockUser);
    }



}