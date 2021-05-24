package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;

/**
 *  Controller class that contains functions for the control of the HomeView.fxml view
 */
public class HomeViewController {

    @FXML
    private VBox stockList;

    /**
     * Default no-argument constructor
     */
    public HomeViewController(){}

    /**
     * Method that initializes the VBox instance corresponding in the FXML file charging it with a stockLists
     */
    @FXML
    private void initialize(){
        DeustockGateway gateway = new DeustockGateway();

        for(Stock stock : gateway.getStockList("small")){
            stockList.getChildren().add(new StockInfoLine(stock));
            stockList.getChildren().add(new Separator());
        }
    }
}
