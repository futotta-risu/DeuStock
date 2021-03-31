package es.deusto.deustock.client.visual.help;

import es.deusto.deustock.client.data.help.FAQQuestion;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * @author Erik B. Terres
 */
public class FAQLine extends BorderPane {

    FAQQuestion question;
    Label questionLabel, answerLabel;


    public FAQLine(FAQQuestion question){
        this.question = question;
        initRoot();
    }

    private void initRoot(){
        this.questionLabel = new Label(this.question.getQuestion());
        this.answerLabel = new Label(this.question.getAnswer());
        setBottom(this.answerLabel);
        setTop(this.questionLabel);
    }

}
