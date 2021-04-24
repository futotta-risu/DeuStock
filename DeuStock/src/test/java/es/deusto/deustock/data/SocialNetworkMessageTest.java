package es.deusto.deustock.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class SocialNetworkMessageTest {

	@Test
	public void testMessage() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		String testString = "Hola me gusta la CocaCola";
		String expectedString = "Hola me gusta la CocaCola";
		
		socialNetworkMessage.setMessage(testString);
		
		assertEquals("getMessage and setMessage doesn't work", expectedString, socialNetworkMessage.getMessage());
	}
	
	@Test
	public void testAuthor() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		String testString = "Aritz Zugazaga";
		String expectedString = "Aritz Zugazaga";
		
		socialNetworkMessage.setAuthor(testString);
		
		assertEquals("getAuthor and setAuthor doesn't work", expectedString, socialNetworkMessage.getAuthor());
	}
	
	@Test
	public void testSentiment() {
		SocialNetworkMessage socialNetworkMessage = new SocialNetworkMessage();
		double testDouble = 12.52;
		double expectedDouble = 12.52;
		
		socialNetworkMessage.setSentiment(testDouble);
		
		assertEquals("getSentiment and setSentiment doesn't work", expectedDouble, socialNetworkMessage.getSentiment());
	}

}
