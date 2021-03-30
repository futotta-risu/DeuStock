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

    public StockListViewController(){}


    @FXML
    private void initialize(){
        DeustockGateway gateway = new DeustockGateway();

        for(Stock stock : gateway.getStockList()){
            stockList.getChildren().add(new StockInfoLine(stock));
            stockList.getChildren().add(new Separator());
        }
        Button helpView = new Button("Goto Help");
        helpView.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangePane(
                        ViewPaths.HelpViewPath
                )
        );
        stockList.getChildren().add(helpView);

        HashMap<String, Object> params = new HashMap<>();
        params.put("username","test");
        Button userView = new Button("User");
        userView.setOnMouseClicked(
                mouseEvent -> MainController.getInstance().loadAndChangePaneWithParams(
                        ViewPaths.UserDetailViewPath, params
                )
        );
        stockList.getChildren().add(userView);
    }

    @FXML
    private void sentimentSearch(){
        String searchQuery = twitterSearchField.getText();
        DeustockGateway gateway = new DeustockGateway();

        sentimentLabel.setText("Sentiment: " + gateway.getTwitterSentiment(searchQuery));
    }


}
