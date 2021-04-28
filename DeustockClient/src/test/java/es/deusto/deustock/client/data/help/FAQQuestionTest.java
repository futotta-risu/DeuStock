package es.deusto.deustock.client.data.help;

import junit.framework.TestCase;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class FAQQuestionTest extends TestCase {

    FAQQuestion faqQuestion;

    @Before
    public void setUp(){
        faqQuestion = new FAQQuestion("question","answer");
        faqQuestion.setDate(new Date(2020-3-30));
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
        assertEquals(2020-3-30, faqQuestion.getDate());
    }

    @Test
    public void testSetDate(){

    }

    @Test
    public void  testToString(){

    }

    @Test
    public void testConstructorJSON(){
        FAQQuestion faqQuestion1 = new FAQQuestion(new JSONObject());
        assertNotNull(faqQuestion1);
    }

    @Test
    public void testConstructorParam(){
        assertEquals("question", faqQuestion.getQuestion());
        assertEquals("answer", faqQuestion.getAnswer());
        assertEquals(2020-3-30, faqQuestion.getDate());
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