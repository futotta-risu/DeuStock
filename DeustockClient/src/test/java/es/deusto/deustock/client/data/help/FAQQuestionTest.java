package es.deusto.deustock.client.data.help;

import junit.framework.TestCase;
import org.json.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class FAQQuestionTest{

    FAQQuestion faqQuestion;
    JSONObject data;

    @BeforeEach
    public void setUp(){
        faqQuestion = new FAQQuestion("question","answer");
    }

    @Test
    public void testGetQuestion(){
        assertEquals("question", faqQuestion.getQuestion());
    }

    @Test
    public void testSetQuestion(){
        faqQuestion.setQuestion("question1");
        assertEquals("question1", faqQuestion.getQuestion());
    }

    @Test
    public void testGetAnswer(){
        assertEquals("answer", faqQuestion.getAnswer());

    }

    @Test
    public void testSetAnswer(){
        faqQuestion.setAnswer("answer1");
        assertEquals("answer1", faqQuestion.getAnswer());
    }

    @Test
    public void testGetDate(){

    }

    @Test
    public void testSetDate(){

    }

    @Test
    public void  testToString(){

    }

    @Test
    public void testConstructorJSON(){
        data = new JSONObject();
        data.put("question","TestQuestion");
        data.put("answer","TestAnswer");
        FAQQuestion faqQuestion1 = new FAQQuestion(data);
        assertNotNull(faqQuestion1);
    }

    @Test
    public void constructorJSONNotNull(){

    }

    @Test
    public void testConstructorParam(){
        assertEquals("question", faqQuestion.getQuestion());
        assertEquals("answer", faqQuestion.getAnswer());
        assertNotNull(faqQuestion);
    }

    /**_TODO
     @Override
    public String toString() {
        return "FAQQuestion{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
    */

}