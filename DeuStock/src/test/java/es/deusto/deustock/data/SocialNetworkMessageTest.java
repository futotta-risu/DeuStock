package es.deusto.deustock.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class SocialNetworkMessageTest {
	
	@Test
	public void testConstructor0() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		assertNull("Constructor1 doesn't work", socialNetworkMessage.getAuthor());
		assertNull("Constructor1 doesn't work", socialNetworkMessage.getMessage());
	}
	
	@Test
	public void testConstructor1() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertEquals("Constructor1 doesn't work", "Gartzi", socialNetworkMessage.getAuthor());
		assertEquals("Constructor1 doesn't work", "Hola no me gusta la CocaCola", socialNetworkMessage.getMessage());
	}
	
	@Test
	public void testConstructor2() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Hola no me gusta la CocaCola");
		assertEquals("Constructor2 doesn't work", "Hola no me gusta la CocaCola", socialNetworkMessage.getMessage());
	}

	@Test
	public void testGetMessage() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertEquals("getMessage doesn't work", "Hola no me gusta la CocaCola", socialNetworkMessage.getMessage());
	}
	
	@Test
	public void testSetMessage() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola me no gusta la CocaCola");
		socialNetworkMessage.setMessage("Hola me gusta la CocaCola");
		assertEquals("setMessage doesn't work", "Hola me gusta la CocaCola", socialNetworkMessage.getMessage());
	}
	
	@Test
	public void testGetAuthor() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertEquals("getAuthor doesn't work", "Gartzi", socialNetworkMessage.getAuthor());
	}
	
	@Test
	public void testSetAuthor() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		socialNetworkMessage.setAuthor("DarthGartzi");
		assertEquals("getAuthor doesn't work", "DarthGartzi", socialNetworkMessage.getAuthor());
	}
	
	@Test
	public void testGetSentiment() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		socialNetworkMessage.setSentiment(12.5);
		assertEquals("getSentiment doesn't work", 12.5, socialNetworkMessage.getSentiment(), 0.0);
	}
	
	@Test
	public void testSetSentiment() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		socialNetworkMessage.setSentiment(12.5);
		assertEquals("setSentiment doesn't work", 12.5, socialNetworkMessage.getSentiment(), 0.0);
	}
	
	@Test
	public void testToString() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		socialNetworkMessage.setSentiment(12.5);
		String actuals = socialNetworkMessage.toString();
		String expected = """
                Gartzi:
                Hola no me gusta la CocaCola
                Sentiment=12.5
                """;
		assertEquals("toString doesn't work", expected, actuals);
	}

}
