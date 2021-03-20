package com.deusto.deustock.dataminer.features;


import com.deusto.deustock.data.SocialNetworkMessage;
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

public class SentimentAnalyzer {


    /**
     *
     * Returns the category of a String based on the predicted sentiment.
     *
     * https://aboullaite.me/stanford-corenlp-java/
     *
     * @param message
     * @return Integer from 0 to 4. (Very Negative, Negative, Neutral, Positive, Very Positive)
     */
    public static int analyse(String message) {
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

    public static List<Integer> analyse(Collection<String> messages){
        return messages.stream().map(SentimentAnalyzer::analyse).collect(Collectors.toList());
    }

    /**
     * Analyzer of SocialNetworkMessages.
     *
     * No need of return since is passed by reference.
     *
     */
    public static void analyseSocialMessage(Collection<SocialNetworkMessage> messages){
        for(SocialNetworkMessage snm : messages) {
            snm.setSentiment(analyse(snm.getMessage()));
        }
    }

}
