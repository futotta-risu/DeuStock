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

    DeustockGateway gateway;

    @FXML
    VBox stockList;

    /**
     * Default no-argument constructor
     */
    public HomeViewController(){
        gateway = new DeustockGateway();
    }

    public void setDeustockGateway(DeustockGateway gateway){ this.gateway = gateway; }

    /**
     * Method that initializes the VBox instance corresponding in the FXML file charging it with a stockLists
     */
    @FXML
    private void initialize(){
        try{
            for(Stock stock : gateway.getStockList("small")){
                stockList.getChildren().add(new StockInfoLine(stock));
                stockList.getChildren().add(new Separator());
            }
        }catch (Exception e){
            return;
        }
    }
}
