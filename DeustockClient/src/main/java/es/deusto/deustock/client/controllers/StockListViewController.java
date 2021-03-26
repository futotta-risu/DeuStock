package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class StockListViewController {
    
    @FXML
    private Label sentimentLabel;

    @FXML
    private TextField twitterSearchField;

    @FXML
    private VBox stockList;

    // Add a public no-args constructor
    public StockListViewController(){}


    @FXML
    private void initialize(){
        stockList.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        List<Stock> stocks = new LinkedList<Stock>();
        stocks.add(new Stock("AMZ",2,2));
        stocks.add(new Stock("Prob",20,24));
        stocks.add(new Stock("TST",200,25));
        for(Stock stock : stocks){
            BorderPane temp = new BorderPane();
            Label nameLabel = new Label(stock.getName());
            Label price = new Label(String.valueOf(stock.getPrice()));
            temp.setLeft(nameLabel);
            temp.setRight(price);
            price.setStyle("-fx-font-weight: bold;-fx-font-size:18px; ");
            nameLabel.setStyle("-fx-font-weight: bold;-fx-font-size:24px;");
            nameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Stage parentStage = (Stage) nameLabel.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader();

                    loader.setLocation(getClass().getResource("/views/StockDetailView.fxml"));
                    VBox layout = null;
                    try {
                        layout = (VBox) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }

                    // Show the scene containing the root layout.
                    Scene scene = new Scene(layout);
                    parentStage.setScene(scene);
                    parentStage.show();
                }
            });

            stockList.getChildren().add(temp);
            stockList.getChildren().add(new Separator());
        }
    }

    @FXML
    private void sentimentSearch(){
        String query = twitterSearchField.getText();

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        Response response = target.path("myapp").path("twitter")
                .path("sentiment").path(query).request(MediaType.TEXT_PLAIN).get();

        String result = response.readEntity(String.class);

        sentimentLabel.setText("Sentiment: " + result);
        System.out.println(result);
    }


}
