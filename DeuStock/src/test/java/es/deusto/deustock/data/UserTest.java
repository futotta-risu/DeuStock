package es.deusto.deustock.data;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

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
		assertTrue("getPassword doesn't work", user.getPassword() == "si");
	}
	
	@Test
	public void testSetPassword() {
		User user = new User("Gartzi", "si");
		user.setPassword("no");
		assertTrue("setPassword doesn't work", user.getPassword() == "no");
	}
	
	@Test
	public void testGetFullName() {
		User user = new User("Gartzi", "si");
		user.setFullName("Aritz Zugazaga");
		assertTrue("getFullName doesn't work", user.getFullName() == "Aritz Zugazaga");
	}
	
	@Test
	public void testSetFullName() {
		User user = new User("Gartzi", "si");
		user.setFullName("Aritz Zugazaga");
		assertTrue("setFullName doesn't work", user.getFullName() == "Aritz Zugazaga");
	}
	
	@Test
	public void testGetBirthDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setBirthDate(date);
		assertTrue("getBirthDate doesn't work", user.getBirthDate() == date);
	}
	
	@Test
	public void testSetBirthDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setBirthDate(date);
		assertTrue("setBirthDate doesn't work", user.getBirthDate() == date);
	}
	
	@Test
	public void testGetCountry() {
		User user = new User("Gartzi", "si");
		user.setCountry("Spain");
		assertTrue("getCountry doesn't work", user.getCountry() == "Spain");
	}
	
	@Test
	public void testSetCountry() {
		User user = new User("Gartzi", "si");
		user.setCountry("Spain");
		assertTrue("setCountry doesn't work", user.getCountry() == "Spain");
	}
	
	@Test
	public void testGetDescription() {
		User user = new User("Gartzi", "si");
		user.setDescription("Hola me gusta la CocaCola");
		assertTrue("getDescription doesn't work", user.getDescription() == "Hola me gusta la CocaCola");
	}
	
	@Test
	public void testSetDescription() {
		User user = new User("Gartzi", "si");
		user.setDescription("Hola me gusta la CocaCola");
		assertTrue("setDescription doesn't work", user.getDescription() == "Hola me gusta la CocaCola");
	}
	
	@Test
	public void testGetRegisterDate() {
		User user = new User("Gartzi", "si");
		assertTrue("getRegisterDate doesn't work", user.getRegisterDate() == user.getLastActivity());
	}
	
	@Test
	public void testSetRegisterDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setRegisterDate(date);
		assertTrue("setRegisterDate doesn't work", user.getRegisterDate() == date);
	}
	
	@Test
	public void testGetLastActivity() {
		User user = new User("Gartzi", "si");
		assertTrue("getLastActivity doesn't work", user.getLastActivity() == user.getRegisterDate());
	}
	
	@Test
	public void testSetLastActivity() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setLastActivity(date);
		assertTrue("setLastActivity doesn't work", user.getLastActivity() == date);
	}
	
	@Test
	public void testConstructor() {
		User user = new User("Gartzi", "si");
		assertTrue("Constructor doesn't work", user.getUsername() == "Gartzi");
		assertTrue("Constructor doesn't work", user.getPassword() == "si");
		assertTrue("Constructor doesn't work", user.getRegisterDate() == user.getLastActivity());
		assertTrue("Constructor doesn't work", user.getLastActivity() == user.getRegisterDate());
	}
	
	@Test
	public void testUpdateInfo() {
		Date date = new Date();
		User user = new User("Gartzi", "si");
		User updater = new User("updater" ,"no");
		updater.setFullName("Aritz Zugazaga");
		updater.setBirthDate(date);
		updater.setCountry("Spain");
		updater.setDescription("Hola me gusta la CocaCola");
		updater.setRegisterDate(date);
		updater.setLastActivity(date);
		user.updateInfo(updater);
		assertTrue("updateInfo doesn't work", user.getPassword() == "no");
		assertTrue("updateInfo doesn't work", user.getFullName() == "Aritz Zugazaga");
		assertTrue("updateInfo doesn't work", user.getBirthDate() == date);
		assertTrue("updateInfo doesn't work", user.getCountry() == "Spain");
		assertTrue("updateInfo doesn't work", user.getDescription() == "Hola me gusta la CocaCola");
		assertTrue("updateInfo doesn't work", user.getRegisterDate() == date);
		assertTrue("updateInfo doesn't work", user.getLastActivity() == date);

	}
	
	@Test
	public void testToString() {
		Date date = new Date();
		User updater = new User("updater" ,"no");
		updater.setFullName("Aritz Zugazaga");
		updater.setBirthDate(date);
		updater.setCountry("Spain");
		updater.setDescription("Hola me gusta la CocaCola");
		updater.setRegisterDate(date);
		updater.setLastActivity(date);
		String actuals = updater.toString();
		String expected = "User [username=" + "updater" + ", password=" + "no" + ", fullName=" + "Aritz Zugazaga" + ", birthDate="
				+ date + ", country=" + "Spain" + ", description=" + "Hola me gusta la CocaCola" + ", registerDate=" + date
				+ ", lastActivity=" + date + "]";
		assertEquals("toString doesn't work", expected, actuals);
	}
	
	@Test
	public void testCheckPassword() {
		User user = new User("Gartzi", "si");
		assertTrue("checkPassword doesn't work", user.checkPassword("si"));
	}
}
