package com.deusto.deustock.dataminer.features;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.assertTrue;

public class SentimentAnalyzerTest {

    @Test
    @DisplayName("Test Analize String")
    public void testAnalyse() {
        String testStringPositive = "You are pretty";
        String testStringNegative = "You are so ugly";

        int positiveStringSentiment = SentimentAnalyzer.analyse(testStringPositive);
        int negativeStringSentiment = SentimentAnalyzer.analyse(testStringNegative);

        assertTrue("Positive String should be possible", positiveStringSentiment > 2);
        assertTrue("Negative String should be negative", negativeStringSentiment < 2);
    }

}
