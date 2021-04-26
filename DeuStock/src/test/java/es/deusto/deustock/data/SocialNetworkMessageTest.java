package es.deusto.deustock.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class SocialNetworkMessageTest {
	
	@Test
	public void testConstructor0() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		assertTrue("Constructor1 doesn't work", socialNetworkMessage.getAuthor() == null);
		assertTrue("Constructor1 doesn't work", socialNetworkMessage.getMessage() == null);
	}
	
	@Test
	public void testConstructor1() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertTrue("Constructor1 doesn't work", socialNetworkMessage.getAuthor() == "Gartzi");
		assertTrue("Constructor1 doesn't work", socialNetworkMessage.getMessage() == "Hola no me gusta la CocaCola");
	}
	
	@Test
	public void testConstructor2() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Hola no me gusta la CocaCola");
		assertTrue("Constructor2 doesn't work", socialNetworkMessage.getMessage() == "Hola no me gusta la CocaCola");
	}

	@Test
	public void testGetMessage() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertTrue("getMessage doesn't work", socialNetworkMessage.getMessage() == "Hola no me gusta la CocaCola");
	}
	
	@Test
	public void testSetMessage() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola me no gusta la CocaCola");
		socialNetworkMessage.setMessage("Hola me gusta la CocaCola");
		assertTrue("setMessage doesn't work", socialNetworkMessage.getMessage() == "Hola me gusta la CocaCola");
	}
	
	@Test
	public void testGetAuthor() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		assertTrue("getAuthor doesn't work", socialNetworkMessage.getAuthor() == "Gartzi");
	}
	
	@Test
	public void testSetAuthor() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		socialNetworkMessage.setAuthor("DarthGartzi");
		assertTrue("getAuthor doesn't work", socialNetworkMessage.getAuthor() == "DarthGartzi");
	}
	
	@Test
	public void testGetSentiment() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		socialNetworkMessage.setSentiment(12.5);
		assertTrue("getSentiment doesn't work", socialNetworkMessage.getSentiment() == 12.5);
	}
	
	@Test
	public void testSetSentiment() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		socialNetworkMessage.setSentiment(12.5);
		assertTrue("setSentiment doesn't work", socialNetworkMessage.getSentiment() == 12.5);
	}
	
	@Test
	public void testToString() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage("Gartzi", "Hola no me gusta la CocaCola");
		socialNetworkMessage.setSentiment(12.5);
		String actuals = socialNetworkMessage.toString();
		String expected = "Gartzi:\n" + "Hola no me gusta la CocaCola" + '\n' +"Sentiment=" + 12.5 + "\n";
		assertEquals("toString doesn't work", expected, actuals);
	}

}
