package es.deusto.deustock.client.visual.help;

import es.deusto.deustock.client.data.help.FAQQuestion;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Include the content of a line in the FAQ list
 * 
 * @author Erik B. Terres
 */
public class FAQLine extends BorderPane {

    FAQQuestion question;
    Label questionLabel, answerLabel;


    /**
     * Constructor for class FAQLine
     * 
     * @param question The question object that it's inside the line
     */
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
