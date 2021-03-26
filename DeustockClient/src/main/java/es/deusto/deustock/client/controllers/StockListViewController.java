package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

public class StockListViewController {


    @FXML
    private Label sentimentLabel;

    @FXML
    private TextField twitterSearchField;

    @FXML
    private TableView<Stock> stockTable;

    @FXML
    private TableColumn<Stock, String> columnName;
    @FXML
    private TableColumn<Stock, Integer> columnPrice;
    @FXML
    private TableColumn<Stock, Integer> columnChange;


    // Add a public no-args constructor
    public StockListViewController(){

    }


    @FXML
    private void initialize(){
        List<Stock> stocks = new LinkedList<Stock>();
        stocks.add(new Stock("AMZ",2,2));
        stocks.add(new Stock("Prob",20,24));
        stocks.add(new Stock("TST",200,25));

        final ObservableList<Stock> data = FXCollections.observableArrayList();
        columnName.setCellValueFactory(
                new PropertyValueFactory<Stock,String>("name")
        );
        columnPrice.setCellValueFactory(
                new PropertyValueFactory<Stock,Integer>("price")
        );
        columnChange.setCellValueFactory(
                new PropertyValueFactory<Stock,Integer>("change")
        );
        for(Stock s: stocks)
            stockTable.getItems().add(s);
        stockTable.getItems().add(new Stock("PTT",89,99));


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
