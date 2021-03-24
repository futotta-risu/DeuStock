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
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Sentiment analyzer class that encapsulates all the NLP Sentiment analysis functions
 */
public class SentimentAnalyzer {


    /**
     *
     * Returns the category of a String based on the predicted sentiment.
     *
     * https://aboullaite.me/stanford-corenlp-java/
     *
     * @param message String message
     * @return Integer from 0 to 4. (Very Negative, Negative, Neutral, Positive, Very Positive)
     */
    public static int analyze(String message) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = pipeline.process(message);
        for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            return RNNCoreAnnotations.getPredictedClass(tree);
        }
        return 0;
    }

    public static List<Integer> analyze(List<String> messages){
        return messages.stream().map(SentimentAnalyzer::analyze).collect(Collectors.toList());
    }

    /**
     * Analyzer of SocialNetworkMessages.
     *
     * No need of return since is passed by reference.
     *
     */
    public static void analyze(Collection<SocialNetworkMessage> messages){
        for(SocialNetworkMessage snm : messages) {
            snm.setSentiment(analyze(snm.getMessage()));
        }
    }

    /**
     * Returns the average sentiment of a list of messages
     */
    public static double getSentimentTendency(Collection<SocialNetworkMessage> messages){
        return messages.stream().mapToInt(SocialNetworkMessage::getSentiment).average().getAsDouble();
    }

}