package es.deusto.deustock.data;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class UserTest {

	@Test
	public void testGetUsername() {
		User user = new User("Gartzi", "si");
		assertEquals("getUsername doesn't work", "Gartzi", user.getUsername());
	}
	
	@Test
	public void testSetUsername() {
		User user = new User("Gartzi", "si");
		user.setUsername("DarthGartzi");
		assertEquals("setUsername doesn't work", "DarthGartzi", user.getUsername());
	}
	
	@Test
	public void testGetPassword() {
		User user = new User("Gartzi", "si");
		assertEquals("getPassword doesn't work", "si", user.getPassword());
	}
	
	@Test
	public void testSetPassword() {
		User user = new User("Gartzi", "si");
		user.setPassword("no");
		assertEquals("setPassword doesn't work", "no", user.getPassword());
	}
	
	@Test
	public void testGetFullName() {
		User user = new User("Gartzi", "si");
		user.setFullName("Aritz Zugazaga");
		assertEquals("getFullName doesn't work", "Aritz Zugazaga", user.getFullName());
	}
	
	@Test
	public void testSetFullName() {
		User user = new User("Gartzi", "si");
		user.setFullName("Aritz Zugazaga");
		assertEquals("setFullName doesn't work", "Aritz Zugazaga", user.getFullName());
	}
	
	@Test
	public void testGetBirthDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setBirthDate(date);
		assertSame("getBirthDate doesn't work", user.getBirthDate(), date);
	}
	
	@Test
	public void testSetBirthDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setBirthDate(date);
		assertSame("setBirthDate doesn't work", user.getBirthDate(), date);
	}
	
	@Test
	public void testGetCountry() {
		User user = new User("Gartzi", "si");
		user.setCountry("Spain");
		assertEquals("getCountry doesn't work", "Spain", user.getCountry());
	}
	
	@Test
	public void testSetCountry() {
		User user = new User("Gartzi", "si");
		user.setCountry("Spain");
		assertEquals("setCountry doesn't work", "Spain", user.getCountry());
	}
	
	@Test
	public void testGetDescription() {
		User user = new User("Gartzi", "si");
		user.setDescription("Hola me gusta la CocaCola");
		assertEquals("getDescription doesn't work", "Hola me gusta la CocaCola", user.getDescription());
	}
	
	@Test
	public void testSetDescription() {
		User user = new User("Gartzi", "si");
		user.setDescription("Hola me gusta la CocaCola");
		assertEquals("setDescription doesn't work", "Hola me gusta la CocaCola", user.getDescription());
	}
	
	@Test
	public void testGetRegisterDate() {
		User user = new User("Gartzi", "si");
		assertSame("getRegisterDate doesn't work", user.getRegisterDate(), user.getLastActivity());
	}
	
	@Test
	public void testSetRegisterDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setRegisterDate(date);
		assertSame("setRegisterDate doesn't work", user.getRegisterDate(), date);
	}
	
	@Test
	public void testGetLastActivity() {
		User user = new User("Gartzi", "si");
		assertSame("getLastActivity doesn't work", user.getLastActivity(), user.getRegisterDate());
	}
	
	@Test
	public void testSetLastActivity() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setLastActivity(date);
		assertSame("setLastActivity doesn't work", user.getLastActivity(), date);
	}
	
	@Test
	public void testConstructor() {
		User user = new User("Gartzi", "si");
		assertEquals("Constructor doesn't work", "Gartzi", user.getUsername());
		assertEquals("Constructor doesn't work", "si", user.getPassword());
		assertSame("Constructor doesn't work", user.getRegisterDate(), user.getLastActivity());
		assertSame("Constructor doesn't work", user.getLastActivity(), user.getRegisterDate());
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
		assertEquals("updateInfo doesn't work", "no", user.getPassword());
		assertEquals("updateInfo doesn't work", "Aritz Zugazaga", user.getFullName());
		assertSame("updateInfo doesn't work", user.getBirthDate(), date);
		assertEquals("updateInfo doesn't work", "Spain", user.getCountry());
		assertEquals("updateInfo doesn't work", "Hola me gusta la CocaCola", user.getDescription());
		assertSame("updateInfo doesn't work", user.getRegisterDate(), date);
		assertSame("updateInfo doesn't work", user.getLastActivity(), date);

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
				+ ", lastActivity=" + date + ", wallet=null]";
		assertEquals("toString doesn't work", expected, actuals);
	}
	
	@Test
	public void testCheckPassword() {
		User user = new User("Gartzi", "si");
		assertTrue("checkPassword doesn't work", user.checkPassword("si"));
	}
}
