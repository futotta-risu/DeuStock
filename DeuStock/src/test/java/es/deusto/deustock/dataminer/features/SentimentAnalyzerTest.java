package es.deusto.deustock.dataminer.features;

import es.deusto.deustock.data.SocialNetworkMessage;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SentimentAnalyzerTest {

    @Test
    @DisplayName("Test Analyze String")
    public void testAnalyze() {
        String testStringPositive = "You are pretty";
        String testStringNegative = "You are so ugly";

        double positiveStringSentiment = SentimentAnalyzer.analyze(testStringPositive);
        double negativeStringSentiment = SentimentAnalyzer.analyze(testStringNegative);

        assertTrue("Positive String should be possible", positiveStringSentiment > 2);
        assertTrue("Negative String should be negative", negativeStringSentiment < 2);
    }


    @Test
    @DisplayName("Test Analyze SocialNetworkMessage")
    public void testAnalyzeSocialNetworkMessage() {
        ArrayList<SocialNetworkMessage> msgs = new ArrayList<>();
        msgs.add(new SocialNetworkMessage("You are beautiful"));
        msgs.add(new SocialNetworkMessage("You are really ugly"));

        SentimentAnalyzer.analyze(msgs);

        assertTrue("Positive String should be possible", msgs.get(0).getSentiment() > 2);
        assertTrue("Negative String should be negative", msgs.get(1).getSentiment() < 2);
    }

}
