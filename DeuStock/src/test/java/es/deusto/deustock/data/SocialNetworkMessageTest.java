package es.deusto.deustock.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SocialNetworkMessageTest {
	
	@Test
	void testConstructor0() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		assertNull(socialNetworkMessage.getAuthor());
		assertNull(socialNetworkMessage.getMessage());
	}
	
	@Test
	void testConstructor1() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertEquals("Gartzi", socialNetworkMessage.getAuthor());
		assertEquals("Hola no me gusta la CocaCola", socialNetworkMessage.getMessage());
	}
	
	@Test
	void testConstructor2() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Hola no me gusta la CocaCola");
		assertEquals( "Hola no me gusta la CocaCola", socialNetworkMessage.getMessage());
	}

	@Test
	void testGetMessage() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertEquals("Hola no me gusta la CocaCola", socialNetworkMessage.getMessage());
	}
	
	@Test
	void testSetMessage() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola me no gusta la CocaCola");
		socialNetworkMessage.setMessage("Hola me gusta la CocaCola");
		assertEquals("Hola me gusta la CocaCola", socialNetworkMessage.getMessage());
	}
	
	@Test
	void testGetAuthor() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertEquals( "Gartzi", socialNetworkMessage.getAuthor());
	}
	
	@Test
	void testSetAuthor() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		socialNetworkMessage.setAuthor("DarthGartzi");
		assertEquals("DarthGartzi", socialNetworkMessage.getAuthor());
	}
	
	@Test
	void testGetSentiment() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		socialNetworkMessage.setSentiment(12.5);
		assertEquals(12.5, socialNetworkMessage.getSentiment(), 0.01);
	}
	
	@Test
	void testSetSentiment() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		socialNetworkMessage.setSentiment(12.5);
		assertEquals( 12.5, socialNetworkMessage.getSentiment(), 0.01);
	}
	
	@Test
	void testToString() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		socialNetworkMessage.setSentiment(12.5);
		String actual = socialNetworkMessage.toString();
		String expected = "Gartzi:\nHola no me gusta la CocaCola\nSentiment=12.5\n";
		assertEquals(expected, actual);
	}

}
