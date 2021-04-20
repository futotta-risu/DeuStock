package es.deusto.deustock.dataminer.features;

import es.deusto.deustock.data.SocialNetworkMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("sentiment")
public class SentimentAnalyzerTest {

    @Test
    @DisplayName("Test Analyze String")
    public void testAnalyzeString() {
        String testStringPositive = "You are pretty";
        String testStringNegative = "You are so ugly";

        double positiveStringSentiment = SentimentAnalyzer.analyze(testStringPositive);
        double negativeStringSentiment = SentimentAnalyzer.analyze(testStringNegative);

        assertTrue(positiveStringSentiment > 2);
        assertTrue(negativeStringSentiment < 2);
    }

    @Test
    @DisplayName("Test Analyze String returns average on null")
    public void testAnalyzeNullString(){
        assertEquals(SentimentAnalyzer.analyze((String) null),2);
    }

    @Test
    @DisplayName("Test Analyze String returns average on empty")
    public void testAnalyzeEmptyString(){
        assertEquals(SentimentAnalyzer.analyze(""),2);
    }

    @Test
    @DisplayName("Test Analyze String returns average on empty")
    public void testAnalyzeWhitespaceString(){
        assertEquals(SentimentAnalyzer.analyze("   "),2);
    }

    @Test
    @DisplayName("Test Analyze String returns average on null")
    public void testAnalyzeNullString(){
        assertEquals(SentimentAnalyzer.analyze((String) null),2);
    }

    @Test
    @DisplayName("Test Analyze String returns average on empty")
    public void testAnalyzeEmptyString(){
        assertEquals(SentimentAnalyzer.analyze(""),2);
    }

    @Test
    @DisplayName("Test Analyze String returns average on empty")
    public void testAnalyzeWhitespaceString(){
        assertEquals(SentimentAnalyzer.analyze("   "),2);
    }


    @Test
    @DisplayName("Test Analyze SocialNetworkMessage")
    public void testAnalyzeSocialNetworkMessage() {
        ArrayList<SocialNetworkMessage> msgs = new ArrayList<>();
        msgs.add(new SocialNetworkMessage("You are beautiful"));
        msgs.add(new SocialNetworkMessage("You are really ugly"));

        SentimentAnalyzer.analyze(msgs);

        assertTrue( msgs.get(0).getSentiment() > 2);
        assertTrue(msgs.get(1).getSentiment() < 2);
    }

    @Test
    @DisplayName("Test Threaded Analyze saves Sentiment")
    public void testAnalyzeMessageThreaded() throws InterruptedException {
        ArrayList<SocialNetworkMessage> msgs = new ArrayList<>();
        msgs.add(new SocialNetworkMessage("You are beautiful"));
        msgs.add(new SocialNetworkMessage("You are really ugly"));

        SentimentAnalyzer.analyzeMulti(msgs);

        assertTrue( msgs.get(0).getSentiment() > 2);
        assertTrue(msgs.get(1).getSentiment() < 2);
    }

}