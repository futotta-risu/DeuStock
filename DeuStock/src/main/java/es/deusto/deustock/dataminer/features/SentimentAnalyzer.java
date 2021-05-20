package es.deusto.deustock.dataminer.features;

import es.deusto.deustock.data.SocialNetworkMessage;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;

/**
 * Sentiment analyzer class that encapsulates all the NLP Sentiment analysis functions
 *
 * @author Erik B. Terres
 */
public class SentimentAnalyzer {

    private SentimentAnalyzer(){}

    /**
     * Returns the category of a String based on the predicted sentiment.
     *
     * @param message String message
     * @return Double from 0 to 4. (0-Very Negative, 4-Very Positive)
     */
    public static double analyze(String message) {
        if(message==null || message.isBlank()) return 2;

        var props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
        props.put("threads", "4");

        var pipeline = new StanfordCoreNLP(props);
        var annotation = pipeline.process(message);

        var i = 0;
        double sentiment = 0;
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            var tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentiment += RNNCoreAnnotations.getPredictedClass(tree);

            i++;
        }

        return ( i > 0 ) ? sentiment/i : 0;
    }

    /**
     * Analyzes the sentiment of a collection of {@link SocialNetworkMessage} and saves them to the
     * sentiment variable inside the {@link SocialNetworkMessage}
     *
     * @param messages Collection of {@link SocialNetworkMessage}
     *
     * @see #analyze(String)
     */
    public static void analyze(Collection<SocialNetworkMessage> messages){
        messages.forEach(c -> c.setSentiment(analyze(c.getMessage())));
    }

    private static class AnalyzeSentiment extends Thread {
        private final SocialNetworkMessage msg;
        public AnalyzeSentiment(SocialNetworkMessage msg){this.msg = msg;}

        @Override
        public void run() {msg.setSentiment(analyze(msg.getMessage()));}
    }


    /**
     * Threaded analyze of the sentiment of a collection of {@link SocialNetworkMessage} and saves them to the
     * sentiment variable inside the {@link SocialNetworkMessage}
     *
     * Implementation based on Thread/Join due to time performance improvement vs Executions.
     *
     * @param messages Collection of {@link SocialNetworkMessage}
     *
     * @see #analyze(String)
     */
    public static void analyzeMulti(Collection<SocialNetworkMessage> messages) throws InterruptedException {
        List<AnalyzeSentiment> threads = new ArrayList<>();

        for(SocialNetworkMessage msg : messages){
            var thread = new AnalyzeSentiment(msg);
            threads.add(thread);
            thread.start();
        }

        for(AnalyzeSentiment a : threads){
            a.join();
        }
    }

    /**
     * Returns the average sentiment of a list of messages.
     *
     * @param messages List of already analyzed messages
     */
    public static double getSentimentTendency(Collection<SocialNetworkMessage> messages){
        return messages.stream().mapToDouble(SocialNetworkMessage::getSentiment).average().orElse(Double.NaN);
    }


}