package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.help.FAQLine;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

/**
 * @author Erik B. Terres
 * Controller class that contains functions for the control of the HekpViewController.fxml view
 **/
public class HelpViewController {
    @FXML
    private VBox questionList;

    /**
     * Default no-argument constructor
     */
    public HelpViewController(){}


    /**
     * Method that initializes the instance VBox in the FXML file charging it with the questionList
     * from the gateway
     */

    @FXML
    private void initialize(){
        (new DeustockGateway()).getFAQList()
                .forEach(q-> questionList.getChildren().add(new FAQLine(q)));
    }


}
