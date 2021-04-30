package es.deusto.deustock.dataminer.cleaner;

import es.deusto.deustock.data.SocialNetworkMessage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static es.deusto.deustock.dataminer.cleaner.SocialTextCleaner.*;
import static org.junit.jupiter.api.Assertions.*;

public class SocialTextCleanerTest {

    @Test
    void testRemoveInvalidChars() {
        String testString = "yes, im communist ☭ yeah ☭";
        String expectedString = "yes, im communist  yeah ";

        assertEquals(expectedString, removeInvalidChars(testString));
    }

    @Test
    void testRemoveURLSHTTP() {
        String testString = "Test url http://www.google.com, and, https://www.facebook.com";
        String expectedString = "Test url , and, ";

        assertEquals(expectedString, removeURLS(testString));
    }

    @Test
    void testRemoveURLSHTTPS() {
        String testString = "Test url http://www.google.com, and, https://www.facebook.com";
        String expectedString = "Test url , and, ";

        assertEquals( expectedString, removeURLS(testString));
    }

    @Test
    void testRemoveURLSNoHTTP() {
        String testString = "Test url www.google.com, and, www.facebook.com";
        String expectedString = "Test url , and, ";

        assertEquals(expectedString, removeURLS(testString));
    }

    @Test
    void testRemoveHashtags(){
        String testString = "I really like #BTC #ETH";
        String expectedString = "I really like BTC ETH";

        assertEquals(expectedString, removeHashtags(testString));
    }

    @Test
    void testRemoveUsernames(){
        String testString = "Today I went with @Mike";
        String expectedString = "Today I went with Tom";

        assertEquals(expectedString, removeUsernames(testString));
    }

    @Test
    void testRemoveExtraSpaces(){
        String testString = " I  really   like spaces   ";
        String expectedString = "I really like spaces";

        assertEquals(expectedString, removeExtraSpaces(testString));
    }

    @Test
    void testClean() {
        String testString = " I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ";
        String expectedString = "I am currently holding BTC with Tom Buy more in !";

        assertEquals(expectedString, clean(testString));
    }

    @Test
    void testCleanList() {
        List<String> messages = new ArrayList<>();
        messages.add(" I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ");
        messages.add(" I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ");

        String expectedString = "I am currently holding BTC with Tom Buy more in !";

        clean(messages);

        assertEquals(expectedString, messages.get(0));
        assertEquals(expectedString, messages.get(1));
    }

    @Test
    void testCleanCollectionSocialNetworkMessages() {
        String messageText = " I  am currently holding  #BTC with @mike!! ☭☭  Buy more in http://www.binance.com! ";
        String expectedString = "I am currently holding BTC with Tom Buy more in !";

        List<SocialNetworkMessage> messages = new ArrayList<>();
        messages.add(new SocialNetworkMessage(messageText));
        messages.add(new SocialNetworkMessage(messageText));

        clean(messages);

        assertEquals(expectedString, messages.get(0).getMessage());
        assertEquals(expectedString, messages.get(1).getMessage());
    }
}
