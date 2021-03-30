package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;


public class HomeViewController {

    @FXML
    private VBox stockList;
    
    public HomeViewController(){}
    
    @FXML
    private void initialize(){
        DeustockGateway gateway = new DeustockGateway();

        for(Stock stock : gateway.getStockList()){
            stockList.getChildren().add(new StockInfoLine(stock));
            stockList.getChildren().add(new Separator());
        }
    }
}
