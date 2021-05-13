package es.deusto.deustock.dao;

import es.deusto.deustock.data.User;
import es.deusto.deustock.data.auth.Token;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TokenDAOIT {

    @Test
    void testGetTokenReturnsUser() throws SQLException {
        DBManager dbManager = (DBManager) DBManager.getInstance();

        User user = new User("TokenTestUser", "TestPass");
        UserDAO.getInstance().store(user);
        user = UserDAO.getInstance().get("TokenTestUser");
        Token token = new Token("1234567890", user);
        TokenDAO.getInstance().store(token);

        token = TokenDAO.getInstance().get("1234567890");
        assertNotNull(token.getUser());
    }
}
