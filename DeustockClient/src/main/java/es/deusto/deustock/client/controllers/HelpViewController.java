package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.help.FAQLine;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * @author Erik B. Terres
 */
public class HelpViewController {
    @FXML
    private VBox questionList;

    public HelpViewController(){}

    @FXML
    private void initialize(){
        (new DeustockGateway()).getFAQList()
                .forEach(q-> questionList.getChildren().add(new FAQLine(q)));
    }


}
