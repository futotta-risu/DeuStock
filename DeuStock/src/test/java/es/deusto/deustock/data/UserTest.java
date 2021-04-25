package es.deusto.deustock.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testGetUsername() {
		User user = new User("Gartzi", "si");
		assertTrue("getUsername doesn't work", user.getUsername() == "Gartzi");
	}
	
	@Test
	public void testSetUsername() {
		User user = new User("Gartzi", "si");
		user.setUsername("DarthGartzi");
		assertTrue("setUsername doesn't work", user.getUsername() == "DarthGartzi");
	}
	
	@Test
	public void testGetPassword() {
		User user = new User("Gartzi", "si");
		assertTrue("getUsername doesn't work", user.getPassword() == "si");
	}
	
	@Test
	public void testSetPassword() {
		User user = new User("Gartzi", "si");
		user.setPassword("no");
		assertTrue("setUsername doesn't work", user.getPassword() == "no");
	}

}
