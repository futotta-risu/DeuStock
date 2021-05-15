package es.deusto.deustock.dao;



import es.deusto.deustock.data.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DBManagerIT{

    private DBManager dbManager;

    @BeforeEach
    public void setUp(){
        dbManager = (DBManager) DBManager.getInstance();
    }

    @Test
    public void testGetObjectsReturnsNotNull() {
        User u = new User("TestUser", "TestPass");
        dbManager.store(u);

    	assertNotNull(DBManager.getInstance().getAll(User.class));
    }

}
