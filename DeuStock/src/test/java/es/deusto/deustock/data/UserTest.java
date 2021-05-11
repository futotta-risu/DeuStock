package es.deusto.deustock.data;



import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

	@Test
	void testGetUsername() {
		User user = new User("Gartzi", "si");
		assertEquals("Gartzi", user.getUsername());
	}
	
	@Test
	void testSetUsername() {
		User user = new User("Gartzi", "si");
		user.setUsername("DarthGartzi");
		assertEquals("DarthGartzi", user.getUsername());
	}
	
	@Test
	void testGetPassword() {
		User user = new User("Gartzi", "si");
		assertEquals("si", user.getPassword());
	}
	
	@Test
	void testSetPassword() {
		User user = new User("Gartzi", "si");
		user.setPassword("no");
		assertEquals("no", user.getPassword());
	}
	
	@Test
	void testGetFullName() {
		User user = new User("Gartzi", "si");
		user.setFullName("Aritz Zugazaga");
		assertEquals("Aritz Zugazaga", user.getFullName());
	}
	
	@Test
	void testSetFullName() {
		User user = new User("Gartzi", "si");
		user.setFullName("Aritz Zugazaga");
		assertEquals("Aritz Zugazaga", user.getFullName());
	}
	
	@Test
	void testGetBirthDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setBirthDate(date);
		assertSame(user.getBirthDate(), date);
	}
	
	@Test
	void testSetBirthDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setBirthDate(date);
		assertSame(user.getBirthDate(), date);
	}
	
	@Test
	void testGetCountry() {
		User user = new User("Gartzi", "si");
		user.setCountry("Spain");
		assertEquals("Spain", user.getCountry());
	}
	
	@Test
	void testSetCountry() {
		User user = new User("Gartzi", "si");
		user.setCountry("Spain");
		assertEquals("Spain", user.getCountry());
	}
	
	@Test
	void testGetDescription() {
		User user = new User("Gartzi", "si");
		user.setDescription("Hola me gusta la CocaCola");
		assertEquals("Hola me gusta la CocaCola", user.getDescription());
	}
	
	@Test
	void testSetDescription() {
		User user = new User("Gartzi", "si");
		user.setDescription("Hola me gusta la CocaCola");
		assertEquals("Hola me gusta la CocaCola", user.getDescription());
	}
	
	@Test
	void testGetRegisterDate() {
		User user = new User("Gartzi", "si");
		assertSame(user.getRegisterDate(), user.getLastActivity());
	}
	
	@Test
	void testSetRegisterDate() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setRegisterDate(date);
		assertSame(user.getRegisterDate(), date);
	}
	
	@Test
	void testGetLastActivity() {
		User user = new User("Gartzi", "si");
		assertSame(user.getLastActivity(), user.getRegisterDate());
	}
	
	@Test
	void testSetLastActivity() {
		User user = new User("Gartzi", "si");
		Date date = new Date();
		user.setLastActivity(date);
		assertSame(user.getLastActivity(), date);
	}
	
	@Test
	void testConstructor() {
		User user = new User("Gartzi", "si");
		assertEquals( "Gartzi", user.getUsername());
		assertEquals("si", user.getPassword());
		assertSame(user.getRegisterDate(), user.getLastActivity());
		assertSame(user.getLastActivity(), user.getRegisterDate());
	}
	
	@Test
	void testUpdateInfo() {
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
		assertEquals("no", user.getPassword());
		assertEquals("Aritz Zugazaga", user.getFullName());
		assertSame(user.getBirthDate(), date);
		assertEquals("Spain", user.getCountry());
		assertEquals("Hola me gusta la CocaCola", user.getDescription());
		assertSame(user.getRegisterDate(), date);
		assertSame(user.getLastActivity(), date);

	}
	
	@Test
	void testToString() {
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
		assertEquals(expected, actuals);
	}
	
	@Test
	void testCheckPassword() {
		User user = new User("Gartzi", "si");
		assertTrue(user.checkPassword("si"));
	}

	@Test
	void testSetLastActivityEmpty(){
		User user = new User("Gartzi", "si");
		user.setLastActivity();
		assertDoesNotThrow((ThrowingSupplier<User>) user::setLastActivity);

	}
}
