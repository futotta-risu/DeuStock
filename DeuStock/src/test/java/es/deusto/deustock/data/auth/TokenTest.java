package es.deusto.deustock.data.auth;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.dto.stocks.StockHistoryDTO;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testGetToken() throws IllegalAccessException, NoSuchFieldException {
        User user = new User("TestUser", "TestPass");
        final Token token = new Token("TestToken", user);

        //when
        final String result = token.getToken();

        //then
        assertEquals("TestToken", result);
    }

    @Test
    void testGetUser() throws NoSuchFieldException, IllegalAccessException {
        User user = new User("TestUser", "TestPass");
        final Token token = new Token("TestToken", user);

        //when
        final User result = token.getUser();

        //then
        assertEquals(user, result);
    }
}