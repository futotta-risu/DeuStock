package es.deusto.deustock.client.controllers.stocks;

import es.deusto.deustock.client.controllers.MainController;
import es.deusto.deustock.client.data.Stock;
import es.deusto.deustock.client.gateways.DeustockGateway;
import es.deusto.deustock.client.visual.ViewPaths;
import es.deusto.deustock.client.visual.stocks.list.StockInfoLine;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
                
                Image imageNotFav = new Image("file:src/main/resources/views/img/notfav.png");
                Image imageFav = new Image("file:src/main/resources/views/img/fav.png");
                Button favButton = new Button();
                favButton.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;");
                
                ImageView imageViewNotFav = new ImageView(imageNotFav);
                imageViewNotFav.setFitWidth(50);
                imageViewNotFav.setFitHeight(50);
                imageViewNotFav.setPreserveRatio(false);
                ImageView imageViewFav = new ImageView(imageFav);
                imageViewFav.setFitWidth(50);
                imageViewFav.setFitHeight(50);
                imageViewFav.setPreserveRatio(false);
                
                favButton.setGraphic(imageViewNotFav);
                favButton.setOnAction(new EventHandler() {

					@Override
					public void handle(Event event) {
						if(favButton.getGraphic() == imageViewNotFav) {
							favButton.setGraphic(imageViewFav);
						} else {
							favButton.setGraphic(imageViewNotFav);
						}
						
					}
                });
                
                Button detailButton = new Button();
                detailButton.setText("More Info");
                detailButton.setOnAction(event -> MainController.getInstance().loadAndChangePaneWithParams(
                        ViewPaths.StockDetailViewPath,
                        new HashMap<>() {{
                            put("acronym", stock.getAcronym());
                        }}
                ));
                
                stockList.getChildren().add(stockLine);
                stockList.getChildren().add(favButton);
                stockList.getChildren().add(detailButton);
                stockList.getChildren().add(new Separator());
            }else
                stockLines.get(stock.getAcronym()).refreshStock(stock);
        }
    }


}
