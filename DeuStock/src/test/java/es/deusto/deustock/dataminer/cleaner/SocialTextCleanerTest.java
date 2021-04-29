package es.deusto.deustock.dataminer.cleaner;

import es.deusto.deustock.data.SocialNetworkMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static es.deusto.deustock.dataminer.cleaner.SocialTextCleaner.*;
import static org.junit.Assert.assertEquals;

public class SocialTextCleanerTest {

    @Test
    public void testRemoveInvalidChars() {
        String testString = "yes, im communist ☭ yeah ☭";
        String expectedString = "yes, im communist  yeah ";

        assertEquals("The invalid chars remover doesn't work", expectedString, removeInvalidChars(testString));
    }

    @Test
    public void testRemoveURLSHTTP() {
        String testString = "Test url http://www.google.com, and, https://www.facebook.com";
        String expectedString = "Test url , and, ";

        assertEquals("HTTP urls can be removed", expectedString, removeURLS(testString));
    }

    @Test
    public void testRemoveURLSHTTPS() {
        String testString = "Test url http://www.google.com, and, https://www.facebook.com";
        String expectedString = "Test url , and, ";

        assertEquals( "HTTPS urls can be removed", expectedString, removeURLS(testString));
    }

    @Test
    public void testRemoveURLSNoHTTP() {
        String testString = "Test url www.google.com, and, www.facebook.com";
        String expectedString = "Test url , and, ";

        assertEquals("No HTTP format urls can be removed", expectedString, removeURLS(testString));
    }

    @Test
    public void testRemoveHashtags(){
        String testString = "I really like #BTC #ETH";
        String expectedString = "I really like BTC ETH";

        assertEquals("Cannot remove hashtags from string", expectedString, removeHashtags(testString));
    }

    @Test
    public void testRemoveUsernames(){
        String testString = "Today I went with @Mike";
        String expectedString = "Today I went with Tom";

        assertEquals("Cannot remove hashtags from string", expectedString, removeUsernames(testString));
    }

    @Test
    public void testRemoveExtraSpaces(){
        String testString = " I  really   like spaces   ";
        String expectedString = "I really like spaces";

        assertEquals("Cannot remove extra spaces", expectedString, removeExtraSpaces(testString));
    }

    @Test
    public void testClean() {
        String testString = " I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ";
        String expectedString = "I am currently holding BTC with Tom Buy more in !";

        assertEquals("Cannot totally clean string",
                expectedString, clean(testString));
    }

    @Test
    public void testCleanList() {
        List<String> messages = new ArrayList<>();
        messages.add(" I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ");
        messages.add(" I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ");

        String expectedString = "I am currently holding BTC with Tom Buy more in !";

        clean(messages);

        assertEquals("Cannot totally clean string", expectedString, messages.get(0));
        assertEquals("Cannot totally clean string", expectedString, messages.get(1));
    }

    @Test
    public void testCleanCollectionSocialNetworkMessages() {
        String messageText = " I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ";
        String expectedString = "I am currently holding BTC with Tom Buy more in !";

        List<SocialNetworkMessage> messages = new ArrayList<>();
        messages.add(new SocialNetworkMessage(messageText));
        messages.add(new SocialNetworkMessage(messageText));

        clean(messages);

        assertEquals("Cannot totally clean string", expectedString, messages.get(0).getMessage());
        assertEquals("Cannot totally clean string", expectedString, messages.get(1).getMessage());
    }
}
