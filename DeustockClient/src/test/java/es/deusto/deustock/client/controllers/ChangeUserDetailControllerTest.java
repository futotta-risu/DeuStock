package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChangeUserDetailControllerTest{

    User user;
    DeustockGateway mockGateway;

    @BeforeEach
    public void setUp(){
        mockGateway = mock(DeustockGateway.class);
    }

    @Test
    public void testUpdate(){

    }

    @Test
    public void testGetUser(){
        when(mockGateway.getUser(anyString())).thenReturn(user);
    }
}