package es.deusto.deustock.dataminer.features;


import es.deusto.deustock.data.SocialNetworkMessage;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Collection;
import java.util.Properties;

/**
 * Sentiment analyzer class that encapsulates all the NLP Sentiment analysis functions
 *
 * @author Erik B. Terres
 */
public class SentimentAnalyzer {

    private SentimentAnalyzer(){}

    /**
     *
     * Returns the category of a String based on the predicted sentiment.
     *
     *
     * @param message String message
     * @return Double from 0 to 4. (0-Very Negative, 4-Very Positive)
     */
    public static double analyze(String message) {
        if( message==null || message.isBlank() ){
            return 2;
        }

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = pipeline.process(message);

        int i = 0;
        double sentiment = 0;
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            sentiment += RNNCoreAnnotations.getPredictedClass(tree);

            i++;
        }
        return sentiment/i;
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

    /**
     * Returns the average sentiment of a list of messages
     */
    public static double getSentimentTendency(Collection<SocialNetworkMessage> messages){
        return messages.stream().mapToDouble(SocialNetworkMessage::getSentiment).average().orElse(Double.NaN);
    }

}
