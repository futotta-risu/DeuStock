package es.deusto.deustock.client.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest{

    User user;

    @BeforeEach
    public void setUp(){
        user = new User()
            .setUsername("username")
            .setPassword("password")
            .setFullName("Full Name")
            .setDescription("descriptions")
            .setCountry("SPAIN");
    }

    @Test
    public void testGetUsername() {
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("mariaereno");
        assertEquals("mariaereno", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("123");
        assertEquals("123", user.getPassword());
    }

    @Test
    public void testGetFullName() {
        assertEquals("Full Name", user.getFullName());
    }

    @Test
    public void testSetFullName() {
        user.setFullName("Maria Ereno");
        assertEquals("Maria Ereno", user.getFullName());
    }


    @Test
    public void testGetCountry() {
        assertEquals("SPAIN", user.getCountry());
    }

    @Test
    public void testSetCountry() {
        user.setCountry("GREECE");
        assertEquals("GREECE", user.getCountry());
    }

    @Test
    public void testGetDescription() { assertEquals("descriptions", user.getDescription());
    }

    @Test
    public void testSetDescription() {
        user.setDescription("description2");
        assertEquals("description2", user.getDescription());
    }

    @Test
    public void testConstructorWithParams(){
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("Full Name", user.getFullName());
        assertEquals("SPAIN", user.getCountry());
        assertEquals("descriptions", user.getDescription());

        assertNotNull(user);
    }

    @Test
    public void testConstructorParamsEmpty(){
        User user2 = new User();
        assertNotNull(user2);
    }

    @Test
    public void testUpdateInfo() {
        User user3 = new User()
                .setUsername("username3")
                .setPassword("password3")
                .setDescription("descriptions3")
                .setCountry("SPAIN")
                .setFullName("Full Name3");

        user.updateInfo(user3);
        assertEquals("Full Name3", user.getFullName());
        assertEquals("SPAIN", user.getCountry());
        assertEquals("descriptions3", user.getDescription());

    }

    @Test
    public void testToString() {
        String expected =  "User{username=username, fullName=Full Name, country=SPAIN, description=descriptions}";
        assertEquals(expected, user.toString());
    }
}