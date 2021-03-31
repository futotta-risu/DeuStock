package es.deusto.DeuStock.app.data;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class UserTest {
	
	User test = new User("Username", "Pass", "FullName", new Date(1234567890),"Country", "Description");

	@Test
	public void test() {
		assertTrue(test.getUsername().equals("Username"));
		assertTrue(test.getPassword().equals("Pass"));
		assertTrue(test.getFullName().equals("FullName"));
		assertTrue(test.getBirthDate().getTime() == 1234567890L);
		assertTrue(test.getCountry().equals("Country"));
		assertTrue(test.getDescription().equals("Description"));

	}

}
