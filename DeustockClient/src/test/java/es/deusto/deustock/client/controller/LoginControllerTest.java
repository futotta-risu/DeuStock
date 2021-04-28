package es.deusto.deustock.client.controller;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;

import javafx.scene.control.TextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest{

    DeustockGateway gateway;
    TextField usernameText;
    TextField passwordText;
    User mockUser;
    User user;

    @BeforeEach
    public void setUp(){
        mockUser = mock(User.class);
        gateway = mock(DeustockGateway.class);
        user = new User()
                .setUsername("juangomez")
                .setPassword("juan123")
                .setFullName("Juan Gomez")
                .setCountry("Spain")
                .setDescription("Hola");
    }

    @Test
    public void loginValidDueToCorrectInput(){
        when(gateway.login("juangomez","juan123")).thenReturn(mockUser);
        assertEquals(user, mockUser);
    }



}