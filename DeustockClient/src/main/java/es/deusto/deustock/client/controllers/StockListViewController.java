package es.deusto.deustock.client.controllers;

import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.HashMap;

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

    private HashMap<String, StockInfoLine> stockLines;

    public StockListViewController(){}


    @FXML
    private void initialize(){
        stockLines = new HashMap<>();
        refreshStocks();
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnMouseClicked(mouseEvent -> refreshStocks());

        stockList.getChildren().add(refreshButton);
    }

    @FXML
    private void sentimentSearch(){
        String searchQuery = twitterSearchField.getText();
        DeustockGateway gateway = new DeustockGateway();

        sentimentLabel.setText("Sentiment: " + gateway.getTwitterSentiment(searchQuery));
    }

    public void refreshStocks(){
        DeustockGateway gateway = new DeustockGateway();

        for(Stock stock : gateway.getStockList("big")){
            if(!stockLines.containsKey(stock.getAcronym())){
                StockInfoLine stockLine = new StockInfoLine(stock);
                stockLines.put(stock.getAcronym(), stockLine);
                
                Image image1 = new Image("file:src/main/resources/views/img/notfav.png");
                Image image2 = new Image("file:src/main/resources/views/img/fav.png");
                Button favButton = new Button();
                
                favButton.setGraphic(new ImageView(image1));
                favButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        System.out.println("Añadido a favorito");
                        favButton.setGraphic(new ImageView(image2));
                    }
                });
                
                Button detailButton = new Button();
                detailButton.setText("More Info");
                detailButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							MainController.getInstance().loadAndChangeSceneWithParams(ViewPaths.StockDetailViewPath, new HashMap<String, Object>() {{ put("acronym", stock.getAcronym()); }});
						}
				});
                
                stockList.getChildren().add(stockLine);
                stockList.getChildren().add(new Separator());
            }else
                stockLines.get(stock.getAcronym()).refreshStock(stock);
        }
    }


}
