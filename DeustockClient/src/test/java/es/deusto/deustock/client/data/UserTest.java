package es.deusto.deustock.client.data;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserTest extends TestCase {

    User user;

    @Before
    public void setUp(){
        user = new User("username", "password", "Full Name", new Date(2012-02-1), "SPAIN", "descriptions");
        user.setLastActivity(new Date(2021-03-23));
        user.setRegisterDate(new Date(2016-01-24));
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
    public void testGetBirthDate() {
        assertEquals(2012-2-1, user.getBirthDate());
    }

    @Test
    public void testSetBirthDate() {
        user.setBirthDate(new Date(2000-02-02));
        assertEquals(2000-02-02, user.getBirthDate());
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
    public void testGetRegisterDate() { assertEquals(2016-01-24, user.getRegisterDate()); }

    @Test
    public void testSetRegisterDate() {
        user.setRegisterDate(new Date(2018-9-12));
        assertEquals(2018-9-12, user.getRegisterDate());
    }

    @Test
    public void testGetLastActivity() { assertEquals(2021-03-23, user.getLastActivity()); }

    @Test
    public void testSetLastActivity() {
        user.setLastActivity(new Date(2021-4-28));
        assertEquals(2021-4-28, user.getLastActivity());
    }

    @Test
    public void testConstructorWithParams(){
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("Full Name", user.getFullName());
        assertEquals(2012-2-1, user.getBirthDate());
        assertEquals("SPAIN", user.getCountry());
        assertEquals("descriptions", user.getDescription());
        assertEquals(2016-01-24, user.getRegisterDate());
        assertEquals(2021-03-23, user.getLastActivity());

        assertNotNull(user);
    }

    @Test
    public void testConstructorParamsEmpty(){
        User user2 = new User();
        assertEquals("NULL", user2.getUsername());
        assertEquals("NULL", user2.getPassword());
        assertEquals("NULL", user2.getFullName());
        assertEquals( Calendar.getInstance().getTime(), user2.getBirthDate());
        assertEquals("NULL", user2.getCountry());
        assertEquals("NULL", user2.getDescription());
        assertEquals(Calendar.getInstance().getTime(), user2.getRegisterDate());
        assertEquals(user2.getRegisterDate(), user2.getLastActivity());

    }

    @Test
    public void testUpdateInfo() {
        User user3 = new User("username3", "password3", "Full Name3", new Date(2012-02-23), "SPAIN", "descriptions3");
        user.setLastActivity(new Date(2021-03-23));
        user.setRegisterDate(new Date(2016-01-23));
        user.updateInfo(user3);
        assertEquals("username3", user.getUsername());
        assertEquals("password3", user.getPassword());
        assertEquals("Full Name3", user.getFullName());
        assertEquals( 2012-02-23, user.getBirthDate());
        assertEquals("SPAIN", user.getCountry());
        assertEquals("descriptions3", user.getDescription());
        assertEquals(2021-03-23, user.getRegisterDate());
        assertEquals(2016-01-23, user.getLastActivity());

    }

    @Test
    public void testToString() {
        String expected =  "User [username=username, password=password, fullName=Full Name, birthDate=2012-2-1," +
                "country=SPAIN, description=descriptions, registerDate=2016-01-24, lastActivity=2021-03-23]";
        assertTrue(expected.equals(user.toString()));
    }
}