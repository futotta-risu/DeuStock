package es.deusto.deustock.dataminer.features;

import es.deusto.deustock.data.SocialNetworkMessage;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Performance Test for the SentimentAnalyzer class.
 *
 * @author Erik B. Terres
 */
@Tag("sentiment")
@Tag("performance")
public class SentimentAnalyzerPerformanceTest {


    @Test
    @DisplayName("Test Performance with no threads")
    public void testAnalyzeMessage() {
        ArrayList<SocialNetworkMessage> msgs = new ArrayList<>();

        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));
        msgs.add(new SocialNetworkMessage("You learn more from failure than from success. Don’t let it stop you. Failure builds character."));
        msgs.add(new SocialNetworkMessage("It’s not whether you get knocked down, it’s whether you get up. Knowing is not enough; we must apply. Wishing is not enough; we must do."));
        msgs.add(new SocialNetworkMessage("If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you"));
        msgs.add(new SocialNetworkMessage("Failure will never overtake me if my determination to succeed is strong enough. The future belongs to the competent. Get good, get better, be the best!"));
        msgs.add(new SocialNetworkMessage("Entrepreneurs are great at dealing with uncertainty and also very good at minimizing risk. That’s the classic entrepreneur."));
        msgs.add(new SocialNetworkMessage("Imagine your life is perfect in every respect; what would it look like? You are never too old to set another goal or to dream a new dream."));
        msgs.add(new SocialNetworkMessage("Whether you think you can or think you can’t, you’re right.  Say thank you to everyone you meet for everything they do for you."));
        msgs.add(new SocialNetworkMessage("Security is mostly a superstition. Life is either a daring adventure or nothing. Fake it until you make it! Act as if you had all the confidence you require until it becomes your reality."));
        msgs.add(new SocialNetworkMessage("The man who has confidence in himself gains the confidence of others. To see what is right and not do it is a lack of courage."));
        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action. Reading is to the mind, as exercise is to the body."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing. Develop an Attitude of Gratitude."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));
        msgs.add(new SocialNetworkMessage("You learn more from failure than from success. Don’t let it stop you. Failure builds character."));
        msgs.add(new SocialNetworkMessage("It’s not whether you get knocked down, it’s whether you get up. Knowing is not enough; we must apply. Wishing is not enough; we must do."));
        msgs.add(new SocialNetworkMessage("If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you"));
        msgs.add(new SocialNetworkMessage("Failure will never overtake me if my determination to succeed is strong enough. Do what you can with all you have, wherever you are."));
        msgs.add(new SocialNetworkMessage("Entrepreneurs are great at dealing with uncertainty and also very good at minimizing risk. That’s the classic entrepreneur."));
        msgs.add(new SocialNetworkMessage("Imagine your life is perfect in every respect; what would it look like?"));
        msgs.add(new SocialNetworkMessage("Whether you think you can or think you can’t, you’re right. Creativity is intelligence having fun."));
        msgs.add(new SocialNetworkMessage("Security is mostly a superstition. Life is either a daring adventure or nothing."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today."));
        msgs.add(new SocialNetworkMessage("Things work out best for those who make the best of how things work out."));
        msgs.add(new SocialNetworkMessage("A room without books is like a body without a soul."));
        msgs.add(new SocialNetworkMessage("I think goals should never be easy, they should force you to work, even if they are uncomfortable at the time."));
        msgs.add(new SocialNetworkMessage("One of the lessons that I grew up with was to always stay true to yourself and never let what somebody else says distract you from your goals."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today. Today’s accomplishments were yesterday’s impossibilities."));
        msgs.add(new SocialNetworkMessage("The only way to do great work is to love what you do. If you haven’t found it yet, keep looking. Don’t settle."));
        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action. Reading is to the mind, as exercise is to the body."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing. Develop an Attitude of Gratitude."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));
        msgs.add(new SocialNetworkMessage("You learn more from failure than from success. Don’t let it stop you. Failure builds character."));
        msgs.add(new SocialNetworkMessage("It’s not whether you get knocked down, it’s whether you get up. Knowing is not enough; we must apply. Wishing is not enough; we must do."));
        msgs.add(new SocialNetworkMessage("If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you"));
        msgs.add(new SocialNetworkMessage("Failure will never overtake me if my determination to succeed is strong enough. Do what you can with all you have, wherever you are."));
        msgs.add(new SocialNetworkMessage("Entrepreneurs are great at dealing with uncertainty and also very good at minimizing risk. That’s the classic entrepreneur."));
        msgs.add(new SocialNetworkMessage("Imagine your life is perfect in every respect; what would it look like?"));
        msgs.add(new SocialNetworkMessage("Whether you think you can or think you can’t, you’re right. Creativity is intelligence having fun."));
        msgs.add(new SocialNetworkMessage("Security is mostly a superstition. Life is either a daring adventure or nothing."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today."));
        msgs.add(new SocialNetworkMessage("Things work out best for those who make the best of how things work out."));
        msgs.add(new SocialNetworkMessage("A room without books is like a body without a soul."));
        msgs.add(new SocialNetworkMessage("I think goals should never be easy, they should force you to work, even if they are uncomfortable at the time."));
        msgs.add(new SocialNetworkMessage("One of the lessons that I grew up with was to always stay true to yourself and never let what somebody else says distract you from your goals."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today. Today’s accomplishments were yesterday’s impossibilities."));
        msgs.add(new SocialNetworkMessage("The only way to do great work is to love what you do. If you haven’t found it yet, keep looking. Don’t settle."));


        SentimentAnalyzer.analyze(msgs);

        for(SocialNetworkMessage msg : msgs)
            assertTrue(msg.getSentiment()>=0);
    }


    @Test
    @DisplayName("Test Performance with Thread/Join")
    public void testAnalyzeMessageThreadedJoin() throws InterruptedException {

        ArrayList<SocialNetworkMessage> msgs = new ArrayList<>();
        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));
        msgs.add(new SocialNetworkMessage("You learn more from failure than from success. Don’t let it stop you. Failure builds character."));
        msgs.add(new SocialNetworkMessage("It’s not whether you get knocked down, it’s whether you get up. Knowing is not enough; we must apply. Wishing is not enough; we must do."));
        msgs.add(new SocialNetworkMessage("If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you"));
        msgs.add(new SocialNetworkMessage("Failure will never overtake me if my determination to succeed is strong enough. The future belongs to the competent. Get good, get better, be the best!"));
        msgs.add(new SocialNetworkMessage("Entrepreneurs are great at dealing with uncertainty and also very good at minimizing risk. That’s the classic entrepreneur."));
        msgs.add(new SocialNetworkMessage("Imagine your life is perfect in every respect; what would it look like? You are never too old to set another goal or to dream a new dream."));
        msgs.add(new SocialNetworkMessage("Whether you think you can or think you can’t, you’re right.  Say thank you to everyone you meet for everything they do for you."));
        msgs.add(new SocialNetworkMessage("Security is mostly a superstition. Life is either a daring adventure or nothing. Fake it until you make it! Act as if you had all the confidence you require until it becomes your reality."));
        msgs.add(new SocialNetworkMessage("The man who has confidence in himself gains the confidence of others. To see what is right and not do it is a lack of courage."));
        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action. Reading is to the mind, as exercise is to the body."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing. Develop an Attitude of Gratitude."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));
        msgs.add(new SocialNetworkMessage("You learn more from failure than from success. Don’t let it stop you. Failure builds character."));
        msgs.add(new SocialNetworkMessage("It’s not whether you get knocked down, it’s whether you get up. Knowing is not enough; we must apply. Wishing is not enough; we must do."));
        msgs.add(new SocialNetworkMessage("If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you"));
        msgs.add(new SocialNetworkMessage("Failure will never overtake me if my determination to succeed is strong enough. Do what you can with all you have, wherever you are."));
        msgs.add(new SocialNetworkMessage("Entrepreneurs are great at dealing with uncertainty and also very good at minimizing risk. That’s the classic entrepreneur."));
        msgs.add(new SocialNetworkMessage("Imagine your life is perfect in every respect; what would it look like?"));
        msgs.add(new SocialNetworkMessage("Whether you think you can or think you can’t, you’re right. Creativity is intelligence having fun."));
        msgs.add(new SocialNetworkMessage("Security is mostly a superstition. Life is either a daring adventure or nothing."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today."));
        msgs.add(new SocialNetworkMessage("Things work out best for those who make the best of how things work out."));
        msgs.add(new SocialNetworkMessage("A room without books is like a body without a soul."));
        msgs.add(new SocialNetworkMessage("I think goals should never be easy, they should force you to work, even if they are uncomfortable at the time."));
        msgs.add(new SocialNetworkMessage("One of the lessons that I grew up with was to always stay true to yourself and never let what somebody else says distract you from your goals."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today. Today’s accomplishments were yesterday’s impossibilities."));
        msgs.add(new SocialNetworkMessage("The only way to do great work is to love what you do. If you haven’t found it yet, keep looking. Don’t settle."));
        msgs.add(new SocialNetworkMessage("You are beautiful. We generate fears while we sit. We overcome them by action. Reading is to the mind, as exercise is to the body."));
        msgs.add(new SocialNetworkMessage("The best way to get started is to quit talking and begin doing. Develop an Attitude of Gratitude."));
        msgs.add(new SocialNetworkMessage("The pessimist sees difficulty in every opportunity. The optimist sees opportunity in every difficulty"));
        msgs.add(new SocialNetworkMessage("Don’t let yesterday take up too much of today. We may encounter many defeats but we must not be defeated."));
        msgs.add(new SocialNetworkMessage("You learn more from failure than from success. Don’t let it stop you. Failure builds character."));
        msgs.add(new SocialNetworkMessage("It’s not whether you get knocked down, it’s whether you get up. Knowing is not enough; we must apply. Wishing is not enough; we must do."));
        msgs.add(new SocialNetworkMessage("If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you"));
        msgs.add(new SocialNetworkMessage("Failure will never overtake me if my determination to succeed is strong enough. Do what you can with all you have, wherever you are."));
        msgs.add(new SocialNetworkMessage("Entrepreneurs are great at dealing with uncertainty and also very good at minimizing risk. That’s the classic entrepreneur."));
        msgs.add(new SocialNetworkMessage("Imagine your life is perfect in every respect; what would it look like?"));
        msgs.add(new SocialNetworkMessage("Whether you think you can or think you can’t, you’re right. Creativity is intelligence having fun."));
        msgs.add(new SocialNetworkMessage("Security is mostly a superstition. Life is either a daring adventure or nothing."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today."));
        msgs.add(new SocialNetworkMessage("Things work out best for those who make the best of how things work out."));
        msgs.add(new SocialNetworkMessage("A room without books is like a body without a soul."));
        msgs.add(new SocialNetworkMessage("I think goals should never be easy, they should force you to work, even if they are uncomfortable at the time."));
        msgs.add(new SocialNetworkMessage("One of the lessons that I grew up with was to always stay true to yourself and never let what somebody else says distract you from your goals."));
        msgs.add(new SocialNetworkMessage("The only limit to our realization of tomorrow will be our doubts of today. Today’s accomplishments were yesterday’s impossibilities."));
        msgs.add(new SocialNetworkMessage("The only way to do great work is to love what you do. If you haven’t found it yet, keep looking. Don’t settle."));


        SentimentAnalyzer.analyzeMulti(msgs);

        for(SocialNetworkMessage msg : msgs)
            assertTrue(msg.getSentiment()>=0);
    }
}
