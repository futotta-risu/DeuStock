package es.deusto.deustock.client.data.help;

import org.json.JSONObject;

import java.util.Date;

/**
 * @author Erik B. Terres
 */
public class FAQQuestion {

    String question, answer;
    Date date;

    @Override
    public String toString() {
        return "FAQQuestion{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public FAQQuestion(JSONObject data) {
        this.question = data.getString("question");
        this.answer = data.getString("answer");
    }

    public FAQQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
