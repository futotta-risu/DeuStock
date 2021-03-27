package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.net.RESTVars;
import es.deusto.deustock.client.visual.ViewPaths;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


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
        // Temporal data. Will eventually use a REST petition to get the list
        List<Stock> stocks = new LinkedList<Stock>();
        stocks.add(new Stock("AMZ",2,2));
        stocks.add(new Stock("Prob",20,24));
        stocks.add(new Stock("TST",200,25));

        for(Stock stock : stocks){
            BorderPane temp = new BorderPane();
            // TODO encapsulate row in own class
            Label nameLabel = new Label(stock.getName());
            nameLabel.getStyleClass().add("stock-line-name");
            Label price = new Label(String.valueOf(stock.getPrice()));
            price.getStyleClass().add("stock-line-price");
            temp.setLeft(nameLabel);
            temp.setRight(price);
            nameLabel.setOnMouseClicked(mouseEvent -> MainController.getInstance().loadAndChangeScene(ViewPaths.StockDetailViewPath));

            stockList.getChildren().add(temp);
            stockList.getChildren().add(new Separator());
        }
    }

    @FXML
    private void sentimentSearch(){
        String query = twitterSearchField.getText();

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(RESTVars.host);
        Response response = target.path(RESTVars.appName).path("twitter")
                .path("sentiment").path(query).request(MediaType.TEXT_PLAIN).get();

        String result = response.readEntity(String.class);

        sentimentLabel.setText("Sentiment: " + result);
        System.out.println(result);
    }


}
