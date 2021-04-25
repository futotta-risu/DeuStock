package es.deusto.deustock.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class SocialNetworkMessageTest {

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

}
