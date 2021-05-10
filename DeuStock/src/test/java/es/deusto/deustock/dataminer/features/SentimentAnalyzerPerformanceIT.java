package es.deusto.deustock.dataminer.features;

import es.deusto.deustock.data.SocialNetworkMessage;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Performance Test for the SentimentAnalyzer class.
 *
 * @author Erik B. Terres
 */
public class SentimentAnalyzerPerformanceIT {


    @Rule
    public ContiPerfRule i = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 5, threads = 5)
    @Required(average = 99999, max = 99999, percentile95=99999)
    public void testAnalyzeMessage() {
        ArrayList<SocialNetworkMessage> msgs = new ArrayList<>();

        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));
        

        SentimentAnalyzer.analyze(msgs);

        for(SocialNetworkMessage msg : msgs)
            assertTrue(msg.getSentiment()>=0);
    }

    @Test
    @PerfTest(invocations = 5, threads = 5)
    @Required(average = 99999, max = 99999, percentile95=99999)
    public void testAnalyzeMessageThreadedJoin() throws InterruptedException {

        ArrayList<SocialNetworkMessage> msgs = new ArrayList<>();
        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));

        SentimentAnalyzer.analyzeMulti(msgs);

        for(SocialNetworkMessage msg : msgs) {
            assertTrue(msg.getSentiment() >= 0);
        }
    }
}
