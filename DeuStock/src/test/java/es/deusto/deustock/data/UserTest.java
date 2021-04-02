package es.deusto.deustock.data;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Test;

public class UserTest {
	

	@Test
	public void createUser() {
		User test = new User("Username", "Pass", "FullName", new Date(1234567890),"Country", "Description");

		assertEquals("Username", test.getUsername());
		assertEquals("Pass", test.getPassword());
		assertEquals("FullName", test.getFullName());
		assertEquals(1234567890L, test.getBirthDate().getTime());
		assertEquals("Country", test.getCountry());
		assertEquals("Description", test.getDescription());

	}

	@Test
	public void testCheckPassword() {
		User user = new User();
		user.setPassword("test_password");
		assertTrue(user.checkPassword("test_password"));
	}

}
