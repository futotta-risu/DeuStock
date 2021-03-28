package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * StockListView controller
 *
 * @author Erik B. Terres
 */
public class StockListViewController {
    
    @FXML
    private Label sentimentLabel;

    @FXML
    private TextField twitterSearchField;

    @FXML
    private VBox stockList;

    public StockListViewController(){}


    @FXML
    private void initialize(){
        DeustockGateway gateway = new DeustockGateway();

        for(Stock stock : gateway.getStockList()){
            stockList.getChildren().add(new StockInfoLine(stock));
            stockList.getChildren().add(new Separator());
        }
    }

    @FXML
    private void sentimentSearch(){
        String searchQuery = twitterSearchField.getText();
        DeustockGateway gateway = new DeustockGateway();

        sentimentLabel.setText("Sentiment: " + gateway.getTwitterSentiment(searchQuery));
    }


}
