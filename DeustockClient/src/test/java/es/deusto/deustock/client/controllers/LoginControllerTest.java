package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.User;
import es.deusto.deustock.client.gateways.DeustockGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    DeustockGateway gateway;
    User mockUser;
    User user;
    LoginController loginController;

    @BeforeEach
    public void setUp() {
        mockUser = mock(User.class);
        gateway = mock(DeustockGateway.class);
        loginController = mock(LoginController.class);
        user = new User()
                .setUsername("juangomez")
                .setPassword("juan123")
                .setFullName("Juan Gomez")
                .setCountry("Spain")
                .setDescription("Hola");

    }

    @Test
    public void testLoginController() {
        //  when(loginController.)
    }

    @Test
    public void loginValidDueToCorrectInput() {
        when(gateway.login("juangomez", "juan123")).thenReturn(user);
    }

    @Test
    public void testInitialize() {

        assertDoesNotThrow(() -> initialize());
    }

    private void initialize() throws NoSuchMethodException {
        Method initialize = LoginController.class.getDeclaredMethod("initialize", null);
        initialize.setAccessible(true);

    }


}
